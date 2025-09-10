package com.tvseries.TvSeriesManagementSystemBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tvseries.TvSeriesManagementSystemBackend.dto.RegisterRequest;
import com.tvseries.TvSeriesManagementSystemBackend.entity.User;
import com.tvseries.TvSeriesManagementSystemBackend.repository.UserRepository;
import com.tvseries.TvSeriesManagementSystemBackend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
    }
}
