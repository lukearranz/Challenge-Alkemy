package com.challenge.alkemy.security.service;

import com.challenge.alkemy.error.user.UserAndPassDontMatchException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImp implements PasswordService{

    BCryptPasswordEncoder b = new BCryptPasswordEncoder();

    @Override
    public String encryptPassword(String password) {
        // Aqui encriptamos la contraseña y la devolvemos
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean verifyPassword(String originalPassword, String hashPassword) throws UserAndPassDontMatchException {
        // Aqui chequeamos que las contraseñas coincidan.
        if (!b.matches(originalPassword,hashPassword)) {
            throw new UserAndPassDontMatchException("USUARIO O CONTRASEÑA INCORRECTOS");
        }
        return b.matches(originalPassword, hashPassword);
    }
}
