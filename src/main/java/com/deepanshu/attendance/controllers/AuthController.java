package com.deepanshu.attendance.controllers;

import com.deepanshu.attendance.AttendanceApplication;
import com.deepanshu.attendance.domain.dtos.AuthResponse;
import com.deepanshu.attendance.domain.dtos.LoginRequest;
import com.deepanshu.attendance.security.AttendanceUserDetails;
import com.deepanshu.attendance.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        UserDetails userDetails = authenticationService.authenticate(loginRequest.getEmail(),loginRequest.getPassword());
        String token = authenticationService.generateToken(userDetails);
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .role(userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_",""))
                .expiresIn(2592000)
                .build();
        return ResponseEntity.ok(response);

    }
}
