package com.mkvillage.mkvillage_user_service.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String name;
    private String mobile;
    private String email;
    private boolean active;
    private Set<String> roles;
}
