package com.dashboard.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dashboard.backend.dto.ResponseStructure;
import com.dashboard.backend.entity.User;
import com.dashboard.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
        return userService.getAllUser();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<User>> updateUser(
            @PathVariable Integer id,
            @RequestBody User user) {

        return userService.updateUse(id, user);
    }
    
    @GetMapping("/me")
    public String getLoggedInUser() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
    
    @GetMapping("/debug-role")
    public String debugRole(Authentication auth) {
        return auth.getAuthorities().toString();
    }
}