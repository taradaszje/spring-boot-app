package com.jsularz.practice_app.services.impl;

import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.dto.UserUpdateFormDto;
import com.jsularz.practice_app.exceptions.EmailExistsException;
import com.jsularz.practice_app.exceptions.TokenNotFoundException;
import com.jsularz.practice_app.exceptions.UserNotExistsException;
import com.jsularz.practice_app.models.Role;
import com.jsularz.practice_app.models.RoleType;
import com.jsularz.practice_app.models.User;
import com.jsularz.practice_app.models.VerificationToken;
import com.jsularz.practice_app.services.RoleService;
import com.jsularz.practice_app.services.UserService;
import com.jsularz.practice_app.services.repositories.UserRepository;
import com.jsularz.practice_app.services.repositories.VerificationTokenRepository;
import com.jsularz.practice_app.userDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public User createNewUserAccount(final UserCreateFormDto accountDto) throws EmailExistsException {
        if (checkEmailExist(accountDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: " + accountDto.getEmail());
        } else {
            final Role role = roleService.findRole(RoleType.SITE_USER);
            final User user = new User();
            user.setEmail(accountDto.getEmail());
            user.setPassword(encoder.encode(String.valueOf(accountDto.getPassword())).toCharArray());
            user.setCreatedOn(LocalDateTime.now());
            user.setLastLogin(null);
            user.setUsername(accountDto.getUsername());
            user.setStatus(false);
            user.addRole(role);
            return userRepository.save(user);
        }
    }
    //todo wyjebać buildera, stworzyć metode budującą z dto, stworzyć i serwis tokenu
    @Override
    public VerificationToken getVerificationToken(final String verificationToken) {
        return tokenRepository.findByToken(verificationToken).orElseThrow(() -> new TokenNotFoundException("Token not found" + verificationToken));
    }

    @Override
    public void createVerificationToken(final User user, final String token) {
       final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public Iterable<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void updateUser(final Long id, final UserUpdateFormDto userUpdateFormDto) {
        userRepository.findById(id).ifPresent(user -> {
            user.setEmail(userUpdateFormDto.getEmail());
            user.setRoles(userUpdateFormDto.getRoles());
            user.setUsername(userUpdateFormDto.getUsername());
            if(userUpdateFormDto.getPassword().length == 0){
                user.setPassword(user.getPassword());
            }else{
                user.setPassword(userUpdateFormDto.getPassword());
            }
            userRepository.save(user);
        });
    }

    @Override
    public UserDetails loadUserByUsername(final String email) {
        return new UserDetailsImpl(userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"))
        );
    }

    @Override
    public void deleteById(final Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public boolean checkEmailExist(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void saveUser(final User user){
        this.userRepository.save(user);
    }

    @Override
    public User findById(final Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
    }

    @Override
    public User findByEmail(final String email) {
        return this.userRepository.findByEmail(email).orElseThrow(()-> new UserNotExistsException("User with email "+ email +" not found."));
    }

    public void setLoginTime(final String email){
        final User found = findByEmail(email);
        found.setLastLogin(LocalDateTime.now());
            userRepository.save(found);
    }
}
