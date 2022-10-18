package com.challenge.alkemy.security.service;

import com.challenge.alkemy.error.user.UserAndPassDontMatchException;

public interface PasswordService {

    // Encriptar la contraseña
    String encryptPassword(String password);

    // Verificar si la contraseña original y la hashPassword son las mismas
    boolean verifyPassword(String originalPassword, String hashPassword) throws UserAndPassDontMatchException;
}
