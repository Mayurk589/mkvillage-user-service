package com.mkvillage.mkvillage_user_service.controller;


import com.mkvillage.mkvillage_user_service.entity.User;
import com.mkvillage.mkvillage_user_service.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/farmer")
public class UserController {

    @Autowired
    private UserService farmerService;

    // ===============================
    // DASHBOARD
    // ===============================
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        return "Welcome Farmer: " + authentication.getName();
    }

    // ===============================
    // GET PROFILE
    // ===============================
    @GetMapping("/profile")
    public User getProfile(Authentication authentication) {
        return farmerService.getProfile(authentication.getName());
    }

    // ===============================
    // REQUEST DAIRY OWNER ROLE
    // ===============================
    @PostMapping("/request-dairy-owner")
    public String requestDairyOwner(Authentication authentication) {
        farmerService.raiseDairyOwnerRequest(authentication.getName());
        return "Request submitted successfully";
    }
}
