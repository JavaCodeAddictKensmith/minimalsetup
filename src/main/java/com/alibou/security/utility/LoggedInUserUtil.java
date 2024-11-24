package com.alibou.security.utility;

import com.alibou.security.config.CustomUserDetails;
import com.alibou.security.config.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggedInUserUtil {

    private final CustomUserDetailsService userDetailsService;

    public String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return authentication.getName();
    }

    public CustomUserDetails getLoggedInUser() {
        String email = getLoggedInUserEmail();
        if (email == null) {
            throw new IllegalStateException("No logged-in user found");
        }
        return (CustomUserDetails) userDetailsService.loadUserByUsername(email);
    }
}