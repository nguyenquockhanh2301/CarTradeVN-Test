package com.cartradevn.cartradevn.administration.dto;

import com.cartradevn.cartradevn.administration.Enum.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    @NotBlank(message = "Username không được để trống")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;
    
    private UserRole role; 
}
