package com.jsularz.practice_app.configs;

import com.jsularz.practice_app.models.RoleType;
import com.jsularz.practice_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        handle(request, response, authentication);
        setLoginTime(authentication.getName());
        clearAuthenticationAttributes(request);
    }
//todo setlogintime
    private void setLoginTime(final String email){
       // final User found = userService.findByEmail(email);
      //  found.setLastLogin(LocalDateTime.now());
      //  userService.updateUser(found);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(final Authentication authentication){
        boolean isUser = false;
        boolean isAdmin = false;
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for( GrantedAuthority authority : authorities){
            if(authority.getAuthority().equals(RoleType.ADMIN_USER.name())){
                isAdmin = true;
                break;
            }else if(authority.getAuthority().equals(RoleType.SITE_USER.name())){
                isUser = true;
                break;
            }
        }
        if(isUser){
            return "/user/home";
        }else if(isAdmin){
            return "/admin/console";
        }else{
            throw new IllegalStateException();
        }
    }
    private void clearAuthenticationAttributes(final HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
