package com.challenge.alkemy.security.service;

import com.challenge.alkemy.entity.Usuario;
import com.challenge.alkemy.error.user.UsernameAlreadyTakenException;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {

    UserDetails loadUserByUsername(String username);

    void createUser(Usuario usuario) throws UsernameAlreadyTakenException;
}
