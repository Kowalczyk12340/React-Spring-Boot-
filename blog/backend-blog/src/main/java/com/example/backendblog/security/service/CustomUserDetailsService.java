package com.example.backendblog.security.service;

import com.example.backendblog.common.Exception.ApiException;
import com.example.backendblog.model.domain.User;
import com.example.backendblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email));
        return CustomUserDetails.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ApiException("User not found ", HttpStatus.NOT_FOUND)
        );

        return CustomUserDetails.create(user);
    }
}
