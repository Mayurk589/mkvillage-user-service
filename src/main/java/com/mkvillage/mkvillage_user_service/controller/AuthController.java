package com.mkvillage.mkvillage_user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mkvillage.mkvillage_user_service.dto.LoginRequest;
import com.mkvillage.mkvillage_user_service.dto.RegisterRequest;
import com.mkvillage.mkvillage_user_service.dto.RegisterVerifyRequest;
import com.mkvillage.mkvillage_user_service.service.AuthService;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService userService;

    // ===============================
    // SEND OTP
    // ===============================
    @PostMapping("/register/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody @Valid RegisterRequest request) {
        userService.sendOtp(request);
        return ResponseEntity.ok(Map.of("message", "OTP sent successfully"));
    }

    // ===============================
    // VERIFY OTP
    // ===============================
    @PostMapping("/register/verify-otp")
    public ResponseEntity<?> verify(@RequestBody @Valid RegisterVerifyRequest request) {
        userService.verifyOtp(request);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    // ===============================
    // LOGIN
    // ===============================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        String token = userService.login(request);

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "type", "Bearer"
                )
        );
    }

    // ===============================
    // LOGOUT
    // ===============================
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        userService.logout(token);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
