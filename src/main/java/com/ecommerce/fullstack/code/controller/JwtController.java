package com.ecommerce.fullstack.code.controller;


import com.ecommerce.fullstack.code.config.WebSecurityConfig;
import com.ecommerce.fullstack.code.entity.JwtRequest;
import com.ecommerce.fullstack.code.entity.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @PostMapping("/authenticate")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
            return webSecurityConfig.createJwtToken(jwtRequest);
    }

}
