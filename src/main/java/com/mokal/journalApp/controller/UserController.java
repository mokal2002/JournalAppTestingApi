package com.mokal.journalApp.controller;


import com.mokal.journalApp.Repo.UserRepo;
import com.mokal.journalApp.api.response.WeatherResponse;
import com.mokal.journalApp.entity.User;
import com.mokal.journalApp.service.UserService;
import com.mokal.journalApp.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Tag(name = "User API's", description = "User API's")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WeatherService weatherService;


    @Operation(summary = "Update user profile", description = "Update the authenticated user's profile information")
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDB = userService.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete user account", description = "Delete the authenticated user's account")
    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("mumbai");
        String greeting = "";
        if (weatherResponse != null){
            greeting = ", Weather feels like "+ weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+ authentication.getName()+ greeting,HttpStatus.OK);
    }
}
