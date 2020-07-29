package com.task.managerapplication.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @Value("${message}")
    String message;

    @GetMapping("/")
    public String welcomePAge(){
        return "<h1>Welcome </h1><br/> User mappings- /user and /user/testconfig <br/> Manager Mappings- /manager/employees" +
                ", /manager/addemployee(post), /manager/update(post) and /manager/delete(post)";
    }
    @GetMapping("/user/testconfig")
    public String testConfigServer(){
        return "<h1>Message From Config Server Says</h1> <h2>"+ message +"</h2>";
    }

    @GetMapping("/user")
    public String publicPage(){
        return "<h1>Public page For User and Manager</h1>";
    }}
