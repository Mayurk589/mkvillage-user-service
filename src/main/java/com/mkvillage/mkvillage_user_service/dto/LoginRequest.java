package com.mkvillage.mkvillage_user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    private String mobile;

    @NotBlank
    private String password;
}
