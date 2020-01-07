package com.jsularz.practice_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.models.User;
import com.jsularz.practice_app.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BeanPropertyBindingResult;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

import static com.jsularz.practice_app.TestObjectFactory.createUser;
import static com.jsularz.practice_app.TestObjectFactory.createUserCreateForm;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(MainController.class)
@RunWith(SpringRunner.class)
public class MainControllerTest {

    @Resource
    private MockMvc mockMvc;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private JacksonTester<UserCreateFormDto> createFormDtoJacksonTester;
    private JacksonTester<User> userJacksonTester;

    @Before
    public void init(){
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void shouldReturnHttp201WhenUserIsCreated() throws Exception {
        //given
        final UserCreateFormDto userCreateFormDto = createUserCreateForm();
        final User user = createUser();

        given(userService.checkEmailExist(userCreateFormDto.getEmail())).willReturn(false);
        given(userService.createNewUserAccount(any(UserCreateFormDto.class))).willReturn(user);

        //when
        final MockHttpServletResponse response = mockMvc
                .perform(post("/register").with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("username",userCreateFormDto.getUsername())
                .param("email",userCreateFormDto.getEmail())
                .param("password", Arrays.toString(userCreateFormDto.getPassword()))
                .param("matchingPassword", Arrays.toString(userCreateFormDto.getMatchingPassword())))
                .andReturn()
                .getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getRedirectedUrl()).isEqualTo("/login?success");
        verify(userService).createNewUserAccount(any(UserCreateFormDto.class));
    }

    @Test
    public void shouldReturn302AndReturnToRegistrationPageWhenUsernameIsEmpty() throws Exception {
        //given
        final UserCreateFormDto userCreateFormDto = createUserCreateForm();
        final User user = createUser();

        given(userService.checkEmailExist(userCreateFormDto.getEmail())).willReturn(false);
        given(userService.createNewUserAccount(any(UserCreateFormDto.class))).willReturn(user);

        //when
        final MvcResult response = mockMvc
                .perform(post("/register").with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("username","")
                        .param("email",userCreateFormDto.getEmail())
                        .param("password", Arrays.toString(userCreateFormDto.getPassword()))
                        .param("matchingPassword", Arrays.toString(userCreateFormDto.getMatchingPassword())))
                .andReturn();
        final BeanPropertyBindingResult result = (BeanPropertyBindingResult) response.getFlashMap().get("org.springframework.validation.BindingResult.user");


        //then
        assertThat(response.getFlashMap().isEmpty()).isFalse();
        //todo merge into one string and compare ;)
        assertThat(result.getAllErrors().get(0).getDefaultMessage()).isEqualTo("Can't be blank");
        assertThat(result.getAllErrors().get(1).getDefaultMessage()).isEqualTo("Username must be between 4 and 20 characters");
        assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(response.getResponse().getRedirectedUrl()).isEqualTo("/register");
        verify(userService, times(0)).checkEmailExist(userCreateFormDto.getEmail());
        verify(userService, times(0)).createNewUserAccount(any(UserCreateFormDto.class));
    }

}
