package com.challenge.alkemy.security.service;

public interface PasswordService {

    String encryptPassword(String password);

    boolean verifyPassword(String originalPassword, String hashPassword);
}
