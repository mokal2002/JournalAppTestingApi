package com.mokal.journalApp.controller;


import com.mokal.journalApp.cache.AppCache;
import com.mokal.journalApp.entity.User;
import com.mokal.journalApp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API's")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users")
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create admin user", description = "Create a new user with administrative privileges")
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);
    }

    @Operation(summary = "Clear application cache", description = "Clear and reinitialize the application cache")
    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
