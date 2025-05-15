package com.mokal.journalApp.controller;

import com.mokal.journalApp.entity.User;
import com.mokal.journalApp.service.UserDetailsServiceImpl;
import com.mokal.journalApp.service.UserService;
import com.mokal.journalApp.utilis.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mokal.journalApp.dto.UserDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public API's")
public class PublicController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private JwtUtil jwtUtil;


    public PublicController(AuthenticationManager authenticationManager, UserService userService, UserDetailsServiceImpl userDetailsServiceImpl, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtUtil = jwtUtil;
    }


    @Operation(summary = "Register a new user", description = "Create a new user account")
    @PostMapping("/signup")
    public void signup(@RequestBody UserDTO user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        userService.saveNewUser(newUser);
    }

    @Operation(summary = "Login", description = "Authenticate user and get JWT token")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
