package com.alibou.security.services;

import com.alibou.security.exceptions.UserAlreadyExistsException;
import com.alibou.security.requests.AuthenticationRequest;
import com.alibou.security.responses.AuthenticationResponse;
import com.alibou.security.requests.RegisterRequest;
import com.alibou.security.config.JwtService;
import com.alibou.security.enums.Role;
import com.alibou.security.models.User;
import com.alibou.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {

    // Check if a user with the same email already exists
    repository.findByEmail(request.getEmail()).ifPresent(user -> {
      // Validate if the passwords also match
      if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new UserAlreadyExistsException("User with the same email and password already exists.");
      }
    });
    Role role = request.getRole() != null ? request.getRole() : Role.USER; // Default to USER if no role specified
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(role)
        .build();
    repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
//    var user = repository.findByEmail(request.getEmail())
//        .orElseThrow();
    var user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
}