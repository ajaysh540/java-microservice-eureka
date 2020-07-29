package com.employeeservice.employeeapplication.controller;

import com.employeeservice.employeeapplication.Model.AuthenticationRequest;
import com.employeeservice.employeeapplication.Model.AuthenticationResponse;
import com.employeeservice.employeeapplication.services.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
    @GetMapping("/hello")
    public String publicValue(){
        return "Hello From Employee";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }
        catch(Exception e ){
            throw new Exception("Incorrect Username or password",e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        String jwtToken = jwtUtil.generateToken(userDetails);
        return (jwtToken);
    }
}
