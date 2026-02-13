package com.example.service;

import com.example.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserImpl implements CurrentUser {

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal instanceof UserDto userDto) {
                return userDto;
            }
        }
        throw new RuntimeException("failed to get current user");
    }
}
