package com.deepanshu.attendance.services;

import com.deepanshu.attendance.domain.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface UserService {
    User createUser(User user);
    UUID getUserIdFromRequest(HttpServletRequest request);
}
