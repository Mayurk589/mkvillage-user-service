package com.mkvillage.mkvillage_user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mkvillage.mkvillage_user_service.dto.UserResponseDto;
import com.mkvillage.mkvillage_user_service.service.InternalUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final InternalUserService userService;

    // Used by Dairy Service
    @GetMapping("/by-mobile/{mobile}")
    public ResponseEntity<UserResponseDto> getUserByMobile(
            @PathVariable String mobile) {

        return ResponseEntity.ok(userService.getUserByMobile(mobile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/{id}/has-role")
    public ResponseEntity<Boolean> hasRole(
            @PathVariable Long id,
            @RequestParam String role) {

        return ResponseEntity.ok(userService.hasRole(id, role));
    }
}
