package com.alibou.security.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoggedInUserUtils {
    public String  getLoggedInUser (){
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
        System.out.println("===LOGGEDUSER====="+ userDetails.getUsername());
            return userDetails.getUsername();
    }
}
