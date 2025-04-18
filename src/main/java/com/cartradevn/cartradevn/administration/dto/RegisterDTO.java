package com.cartradevn.cartradevn.administration.dto;

import com.cartradevn.cartradevn.administration.Enum.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "Username không được để trống")
    private String username;
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;
    @NotBlank(message = "Password không được để trống")
    private String password;
    private String confirmPassword; // Added for confirmation
    private UserRole role = UserRole.BUYER ; // Default role, can be ADMIN or SELLER as well
}
