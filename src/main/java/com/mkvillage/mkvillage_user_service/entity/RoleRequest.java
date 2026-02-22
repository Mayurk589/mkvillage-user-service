package com.mkvillage.mkvillage_user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "role_requests")
@Getter
@Setter
public class RoleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===============================
    // USER WHO RAISED REQUEST
    // ===============================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ===============================
    // REQUESTED ROLE
    // ===============================
    @Column(nullable = false)
    private String requestedRole;   // Example: ROLE_DAIRY_OWNER

    // ===============================
    // STATUS
    // ===============================
    @Column(nullable = false)
    private String status;          // PENDING / APPROVED / REJECTED

    // ===============================
    // TIMESTAMPS
    // ===============================
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    // ===============================
    // AUTO SET CREATED TIME
    // ===============================
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }
}
