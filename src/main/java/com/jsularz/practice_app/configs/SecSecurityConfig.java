package com.jsularz.practice_app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Autowired
    private DataSource dataSource;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN_USER")
                .antMatchers("/login","/register","/registrationConfirm", "/game", "/resources/static/**").permitAll()
                .antMatchers("/home/**").hasAuthority("SITE_USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(new AuthenticationSuccessHandlerImpl())
                .usernameParameter("email")
                .passwordParameter("password")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("JSESSIONID","csrftoken")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login")
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl()).logoutUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }


}
