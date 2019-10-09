package com.jsularz.practice_app.services.impl;

import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.dto.UserUpdateFormDto;
import com.jsularz.practice_app.exceptions.EmailExistsException;
import com.jsularz.practice_app.exceptions.TokenNotFoundException;
import com.jsularz.practice_app.models.User;
import com.jsularz.practice_app.models.VerificationToken;
import com.jsularz.practice_app.repositories.UserRepository;
import com.jsularz.practice_app.repositories.VerificationTokenRepository;
import com.jsularz.practice_app.services.UserService;
import com.jsularz.practice_app.userDetails.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    // @Transactional
    @Override
    public User createNewUserAccount(final UserCreateFormDto accountDto) throws EmailExistsException {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: " + accountDto.getEmail());
        } else {
           return this.userRepository.save(User.builder()
                    .email(accountDto.getEmail())
                    .password(encoder.encode(String.valueOf(accountDto.getPassword())).toCharArray())
                    .username(accountDto.getUsername())
                    .createdOn(LocalDateTime.now())
                    .lastLogin(null)
                    .roles(accountDto.getRoles())
                    .status(false)
                    .build());
        }
    }
    //todo wyjebać buildera, stworzyć metode budującą z dto
    @Override
    public VerificationToken getVerificationToken(final String verificationToken) {
        return tokenRepository.findByToken(verificationToken).orElseThrow(() -> new TokenNotFoundException("Token not found" + verificationToken));
    }

    @Override
    public void createVerificationToken(final User user, final String token) {
       final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    private Optional<User> findByEmail(final String email) {
        return this.userRepository.findByEmail(email);
    }

    public void setLoginTime(final String email){findByEmail(email).ifPresent(user->{
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            });
    }

    public void saveRegisteredUser(final User user){
        this.userRepository.save(user);
    }

    public boolean emailExist(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Iterable<User> findAll() {
        return this.userRepository.findAll();
    }

    public void deleteById(final Long id) {
        this.userRepository.deleteById(id);
    }

    //to jest ok?
    public User findById(final Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
    }

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
        return new MyUserDetails(userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"))
        );

    }
}
