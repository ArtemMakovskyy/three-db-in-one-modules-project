package com.store.security.controller;

import com.store.security.dto.UpdateUserRoleDto;
import com.store.security.dto.UserResponseDto;
import com.store.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User management",
        description = "Endpoints for managing users")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PATCH, RequestMethod.DELETE})
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/role")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Update user role by user id",
            description = """
                    Update user role by user identification number. Only for ADMIN.
                    Available roles: ROLE_CUSTOMER, ROLE_MANAGER, ROLE_ADMIN.""")
    public UserResponseDto updateUserRole(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserRoleDto roleDto) {
        return userService.updateRole(id, roleDto.role());
    }
}
