package com.deepanshu.attendance.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface UserService {
    UUID getUserIdFromRequest(HttpServletRequest request);
}
