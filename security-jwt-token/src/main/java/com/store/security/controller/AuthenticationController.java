package com.store.security.controller;

import com.store.security.dto.UserLoginRequestDto;
import com.store.security.dto.UserLoginResponseDto;
import com.store.security.dto.UserRegistrationRequestDto;
import com.store.security.dto.UserResponseDto;
import com.store.security.exception.RegistrationException;
import com.store.security.security.AuthenticationService;
import com.store.security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PATCH, RequestMethod.DELETE})
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public String getTest() {
        return "test users";
    }

    @PostMapping("/register")
    public UserResponseDto registerUser(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto loginUser(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpRequest) {
        authenticationService.logout(httpRequest);
        return ResponseEntity.ok("Logout successful");
    }
}
