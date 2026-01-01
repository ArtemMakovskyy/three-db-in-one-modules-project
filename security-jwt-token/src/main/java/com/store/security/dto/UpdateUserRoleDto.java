package com.store.security.dto;

import com.store.security.validation.ValidUserRole;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRoleDto(
        @ValidUserRole
        String role) {
}
