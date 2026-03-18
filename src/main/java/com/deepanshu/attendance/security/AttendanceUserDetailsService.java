package com.deepanshu.attendance.security;

import com.deepanshu.attendance.domain.entities.User;
import com.deepanshu.attendance.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found with email "+email));
        return new AttendanceUserDetails(user);
    }
}
