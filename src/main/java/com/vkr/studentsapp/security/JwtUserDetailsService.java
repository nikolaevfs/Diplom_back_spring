package com.vkr.studentsapp.security;

import com.vkr.studentsapp.model.User;
import com.vkr.studentsapp.security.jwt.JwtUser;
import com.vkr.studentsapp.security.jwt.JwtUserFactory;
import com.vkr.studentsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException("User with email not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        System.out.println("loadByUsername loaded");

        return jwtUser;
    }
}
