package com.deepanshu.attendance.services.impl;

import com.deepanshu.attendance.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UUID getUserIdFromRequest(HttpServletRequest request) {
        return (UUID) request.getAttribute("userId");
    }
}
