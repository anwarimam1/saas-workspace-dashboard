package com.dashboard.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dashboard.backend.dao.UserDao;
import com.dashboard.backend.dto.AuthRequest;
import com.dashboard.backend.entity.User;
import com.dashboard.backend.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;

    public String login(AuthRequest request) {

        String email = request.getEmail().trim();
        String password = request.getPassword().trim();

        System.out.println("INPUT EMAIL: [" + email + "]");
        System.out.println("INPUT PASSWORD: [" + password + "]");

        User user = userDao.findByEmail(email);

        if (user == null) {
            System.out.println("❌ USER NOT FOUND");
            throw new RuntimeException("User not found");
        }

        System.out.println("DB EMAIL: [" + user.getEmail() + "]");
        System.out.println("DB PASSWORD: [" + user.getPassword() + "]");

        boolean match = passwordEncoder.matches(password, user.getPassword());

        System.out.println("MATCH RESULT: " + match);

        if (!match) {
            throw new RuntimeException("Invalid password");
        }
        
        String token = jwtUtil.generateToken(user);


        return token;
    }
}