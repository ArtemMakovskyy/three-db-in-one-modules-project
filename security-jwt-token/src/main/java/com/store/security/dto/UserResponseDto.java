package com.store.security.dto;

import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits. Like 0509876543")
    private String phoneNumber;
    private Set<String> roles;
}
