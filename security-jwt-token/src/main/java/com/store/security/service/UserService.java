package com.store.security.service;

import com.store.security.dto.UserRegistrationRequestDto;
import com.store.security.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request);

    UserResponseDto updateRole(Long userId, String role);
}
