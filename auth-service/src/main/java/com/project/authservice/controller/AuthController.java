package com.project.authservice.controller;

import com.project.authservice.dto.LoginRequestDTO;
import com.project.authservice.dto.LoginResponseDTO;
import com.project.authservice.service.AuthService;
import com.project.authservice.service.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "Generate token on user login")
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(
      @RequestBody LoginRequestDTO loginRequestDTO) {
    log.info("Login request received {} {} {}",loginRequestDTO.userName(),loginRequestDTO.password(),loginRequestDTO.email());
    LoginResponseDTO responseDTO = authService.login(loginRequestDTO);
    return ResponseEntity.ok(responseDTO);
  }

  @Operation(summary = "Validate Token")
  @GetMapping("/validate")
  public ResponseEntity<Void> validateToken(
          @RequestHeader("Authorization") String authHeader) {

    if(authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    authHeader = authHeader.replace("Bearer ", "");
    return authService.validateToken(authHeader)
            ? ResponseEntity.ok().build()
            : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

}