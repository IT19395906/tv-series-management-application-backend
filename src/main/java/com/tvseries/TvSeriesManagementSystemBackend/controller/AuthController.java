package com.tvseries.TvSeriesManagementSystemBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvseries.TvSeriesManagementSystemBackend.auth.JwtUtil;
import com.tvseries.TvSeriesManagementSystemBackend.dto.AuthRequest;
import com.tvseries.TvSeriesManagementSystemBackend.dto.AuthResponse;
import com.tvseries.TvSeriesManagementSystemBackend.dto.RegisterRequest;
import com.tvseries.TvSeriesManagementSystemBackend.service.UserService;
import com.tvseries.TvSeriesManagementSystemBackend.service.Impl.AuthServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthServiceImpl authServiceImpl;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails userDetails = authServiceImpl.loadUserByUsername(request.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt, "User login successfully"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok(new AuthResponse(null, "User registered successfully"));
    }

}
