package com.blubank.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.blubank.entity.User.User user = userService.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException(email + " not found");

        List<GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString()));

        return new User(user.getEmail(), user.getPassword(), roles);
    }
}