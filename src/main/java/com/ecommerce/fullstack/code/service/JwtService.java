package com.ecommerce.fullstack.code.service;


import com.ecommerce.fullstack.code.config.WebSecurityConfig;
import com.ecommerce.fullstack.code.dao.UserDao;
import com.ecommerce.fullstack.code.entity.JwtRequest;
import com.ecommerce.fullstack.code.entity.JwtResponse;
import com.ecommerce.fullstack.code.entity.User;
import com.ecommerce.fullstack.code.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userDao.findById(username);
        if (userOptional.isPresent()){
           return new org.springframework.security.core.userdetails.User(
                   userOptional.get().getUserName(),
                   userOptional.get().getPassword(),
                 webSecurityConfig.getAuthorities(userOptional.get())
           );
       }else{
          throw new UsernameNotFoundException("Username is not valid");
       }
    }

    public UserDao getUserDao(){return userDao;}

}
