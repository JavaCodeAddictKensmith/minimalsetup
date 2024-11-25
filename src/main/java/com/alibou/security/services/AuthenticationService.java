package com.alibou.security.services;

import com.alibou.security.config.CustomUserDetails;
import com.alibou.security.exceptions.UserAlreadyExistsException;
import com.alibou.security.dto.requests.AuthenticationRequest;
import com.alibou.security.dto.responses.AuthenticationResponse;
import com.alibou.security.dto.requests.RegisterRequest;
import com.alibou.security.config.JwtService;
import com.alibou.security.enums.Role;
import com.alibou.security.models.User;
import com.alibou.security.repositories.UserRepository;
import com.alibou.security.utility.LoggedInUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final LoggedInUserUtil loggedInUserUtil;

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

//  public AuthenticationResponse authenticate(AuthenticationRequest request) {
//    authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                    request.getEmail(),
//                    request.getPassword()
//            )
//    );
//
//    var user = repository.findByEmail(request.getEmail())
//            .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//    var jwtToken = jwtService.generateToken(user);
//
//    // Log the logged-in user's details
//    CustomUserDetails loggedInUser = loggedInUserUtil.getLoggedInUser();
//    System.out.println("Logged-In User Email: " + loggedInUser.getEmail());
//    System.out.println("Logged-In User Role: " + loggedInUser.getRole());
//
//    return AuthenticationResponse.builder()
//            .token(jwtToken)
//            .build();
//  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    // Authenticate the user using AuthenticationManager
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );

    // Explicitly set the authenticated user into the SecurityContext
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Fetch the user details from the repository
    var user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    // Generate JWT token
    var jwtToken = jwtService.generateToken(user);

    // Log the logged-in user's details
    CustomUserDetails loggedInUser = loggedInUserUtil.getLoggedInUser();
    System.out.println("Logged-In User Email: " + loggedInUser.getEmail());
   System.out.println("Logged-In User Role: " + loggedInUser.getAuthorities());

    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
  }
}
