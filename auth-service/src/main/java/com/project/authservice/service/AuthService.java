package com.project.authservice.service;

import com.project.authservice.dto.LoginRequestDTO;
import com.project.authservice.dto.LoginResponseDTO;
import com.project.authservice.security.usermanagement.SecurityUser;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.project.authservice.util.JwtUtil;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public Optional<String> login(LoginRequestDTO loginRequestDTO) {
        try {
            //authenticating user by their credential first
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.userName(), loginRequestDTO.password()));
            log.info("Authenticating user {}", authenticate.getPrincipal());
            // If authenticated then generating token for them on login request.
            SecurityUser user = (SecurityUser) authenticate.getPrincipal();
            log.info("User {} logged in", user.getUsername());
            return Optional.of(jwtUtil.generateAccessToken(user));
        }catch (Exception e){
            log.error("Error occurred"+e.getMessage(),e);
            return Optional.empty();
        }
    }

    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e){
            return false;
        }
    }

}
