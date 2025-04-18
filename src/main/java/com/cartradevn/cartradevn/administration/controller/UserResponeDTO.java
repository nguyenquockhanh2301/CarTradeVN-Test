package com.cartradevn.cartradevn.administration.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponeDTO {
    private Long id;
    private String username;
    private String role;
}
