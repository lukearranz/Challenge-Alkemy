package com.challenge.alkemy.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordServiceImp implements PasswordService{

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean verifyPassword(String originalPassword, String hashPassword) {
        return passwordEncoder.matches(originalPassword,hashPassword);
    }
}
