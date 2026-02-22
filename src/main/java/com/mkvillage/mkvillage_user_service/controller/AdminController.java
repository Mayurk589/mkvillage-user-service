package com.mkvillage.mkvillage_user_service.controller;

import com.mkvillage.mkvillage_user_service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // 🔐 entire controller protected
public class AdminController {

    private final AdminService adminService;

    // ===============================
    // GET ALL USERS
    // ===============================
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // ===============================
    // GET PENDING DAIRY REQUESTS
    // ===============================
    @GetMapping("/dairy-owner-requests")
    public ResponseEntity<?> getPendingRequests() {
        return ResponseEntity.ok(adminService.getPendingRequests());
    }

    // ===============================
    // APPROVE DAIRY OWNER
    // ===============================
    @PutMapping("/approve-dairy-owner/{userId}")
    public ResponseEntity<?> approveDairyOwner(@PathVariable Long userId) {
        adminService.approveDairyOwner(userId);
        return ResponseEntity.ok(Map.of("message", "Dairy Owner approved"));
    }

    // ===============================
    // REJECT DAIRY OWNER
    // ===============================
    @PutMapping("/reject-dairy-owner/{userId}")
    public ResponseEntity<?> rejectDairyOwner(@PathVariable Long userId) {
        adminService.rejectDairyOwner(userId);
        return ResponseEntity.ok(Map.of("message", "Request rejected"));
    }

    // ===============================
    // CHANGE ROLE (SAFE VERSION)
    // ===============================
    @PutMapping("/change-role/{userId}")
    public ResponseEntity<?> changeRole(
            @PathVariable Long userId,
            @RequestBody Map<String, String> body) {

        String action = body.get("action");

        adminService.changeUserRole(userId, action);

        return ResponseEntity.ok(Map.of("message", "Role updated successfully"));
    }

}
