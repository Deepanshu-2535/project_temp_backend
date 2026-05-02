package com.deepanshu.attendance.services.impl;

import com.deepanshu.attendance.domain.entities.User;
import com.deepanshu.attendance.repositories.UserRepository;
import com.deepanshu.attendance.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UUID getUserIdFromRequest(HttpServletRequest request) {
        return (UUID) request.getAttribute("userId");
    }
}
