package com.alibou.security.utility;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class LoggedInUserUtil {

    public static UserDetails getLoggedInUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getLoggedInUserEmail() {
        UserDetails userDetails = getLoggedInUserDetails();
        return userDetails.getUsername();
    }

    public static String getLoggedInUserRole() {
        UserDetails userDetails = getLoggedInUserDetails();
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");
    }
}
