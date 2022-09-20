package com.challenge.alkemy.security.service;

public interface PasswordService {

    // Encriptar la contraseña
    String encryptPassword(String password);

    // Verificar si la contraseña original y la hashPassword son las mismas
    boolean verifyPassword(String originalPassword, String hashPassword);
}
