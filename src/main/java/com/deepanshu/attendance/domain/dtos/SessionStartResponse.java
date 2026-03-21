package com.deepanshu.attendance.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionStartResponse {
    Long sessionId;
    String currentToken;
    LocalDateTime expiresAt;
}
