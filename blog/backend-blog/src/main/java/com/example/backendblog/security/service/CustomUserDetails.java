package com.example.backendblog.security.service;

import com.example.backendblog.common.util.AuthoritiesConstants;
import com.example.backendblog.model.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CustomUserDetails implements OAuth2User, UserDetails
{
    private Long id;
    private String name;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String,Object> attributes;

    public CustomUserDetails(Long id, String name, String email, String password, Collection<? extends GrantedAuthority> authorities)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(User user)
    {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER));

        return new CustomUserDetails(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static CustomUserDetails create(User user,Map<String,Object> attributes) {
        CustomUserDetails customUserDetails = CustomUserDetails.create(user);
        customUserDetails.setAttributes(attributes);
        return customUserDetails;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
