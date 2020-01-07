package com.jsularz.practice_app.services.impl;

import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.exceptions.EmailExistsException;
import com.jsularz.practice_app.models.Role;
import com.jsularz.practice_app.models.RoleType;
import com.jsularz.practice_app.models.User;
import com.jsularz.practice_app.services.RoleService;
import com.jsularz.practice_app.services.repositories.UserRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static com.jsularz.practice_app.TestObjectFactory.createUserCreateForm;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(value = SpringRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldCreateUser(){
        //given
        final UserCreateFormDto userCreateFormDto = createUserCreateForm();
        final Role role = new Role(RoleType.SITE_USER);
        role.setId(3L);

        final User user = new User();
        user.setEmail(userCreateFormDto.getEmail());
        user.setPassword(userCreateFormDto.getPassword());
        given(encoder.encode(Arrays.toString(userCreateFormDto.getPassword())))
                .willReturn(Arrays.toString(user.getPassword()));
        given(roleService.findRole(RoleType.SITE_USER)).willReturn(role);
        given(userRepository.findByEmail(userCreateFormDto.getEmail())).willReturn(Optional.empty());
        given(userRepository.save(user)).will(invocation -> invocation.getArgument(0));

        //when
        userService.createNewUserAccount(userCreateFormDto);

        //then
        verify(encoder).encode(Arrays.toString(userCreateFormDto.getPassword()));
        verify(userRepository).save(user);
        verify(userRepository).findByEmail(userCreateFormDto.getEmail());
        verify(roleService).findRole(RoleType.SITE_USER);
    }

    @Test
    public void shouldNotCreateUserIfUserWithThisEmailExists(){
        //given
        final UserCreateFormDto userCreateFormDto = createUserCreateForm();
        final Role role = new Role(RoleType.SITE_USER);
        role.setId(3L);

        final User user = new User();
        user.setEmail(userCreateFormDto.getEmail());
        given(encoder.encode(Arrays.toString(userCreateFormDto.getPassword())))
                .willReturn(Arrays.toString(user.getPassword()));
        given(roleService.findRole(RoleType.SITE_USER)).willReturn(role);
        given(userRepository.findByEmail(userCreateFormDto.getEmail())).willReturn(Optional.of(user));
        given(userRepository.save(user)).will(invocation -> invocation.getArgument(0));

        //when
        final ThrowableAssert.ThrowingCallable invocation = () -> userService.createNewUserAccount(userCreateFormDto);

        //then
        assertThatThrownBy(invocation).isInstanceOf(EmailExistsException.class).hasMessage("There is an account with that email address: "+ userCreateFormDto.getEmail());
        verify(encoder, times(0)).encode(Arrays.toString(userCreateFormDto.getPassword()));
        verify(userRepository,times(0)).save(user);
        verify(userRepository, times(0)).save(user);
        verify(userRepository).findByEmail(userCreateFormDto.getEmail());
        verify(roleService, times(0)).findRole(RoleType.SITE_USER);
    }


}
