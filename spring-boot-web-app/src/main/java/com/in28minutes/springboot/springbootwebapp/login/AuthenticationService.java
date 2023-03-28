package com.in28minutes.springboot.springbootwebapp.login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public boolean authenticate(String username, String password) {
        return username.equals("mark") && password.equals("pass");
    }
}
