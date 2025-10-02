package com.project.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
                              @NotBlank(message = "UserName is required")
                              String userName,
                              @NotBlank(message = "Email is required")
                              @Email(message = "Email should be a valid email address")
                              String email,
                              @NotBlank(message = "Password is required")
                              @Size(min = 8, message = "Password must be at least 8 characters long")
                              String password) {
}