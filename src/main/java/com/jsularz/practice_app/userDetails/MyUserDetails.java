package com.jsularz.practice_app.userDetails;

import com.jsularz.practice_app.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class MyUserDetails implements UserDetails {

    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return convertSet();
    }

    @Override
    public String getPassword() {
        return user.getPassword().toString();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    private Collection<GrantedAuthority> convertSet() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        this.user.getRoles().forEach(role -> collection.add(new SimpleGrantedAuthority(role.getName())));
        return collection;
    }
}
