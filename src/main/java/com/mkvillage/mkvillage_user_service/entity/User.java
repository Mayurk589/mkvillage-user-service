package com.mkvillage.mkvillage_user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String mobile;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean mobileVerified = false;

    private boolean active = true;

    private boolean notificationsEnabled = true;

    private boolean privateAccount = false;

    // ===============================
    // MULTI ROLE SUPPORT
    // ===============================
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

}
