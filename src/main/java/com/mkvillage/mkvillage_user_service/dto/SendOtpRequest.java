package com.mkvillage.mkvillage_user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendOtpRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String mobile;

    private String email;

    @NotBlank
    private String password;
}
