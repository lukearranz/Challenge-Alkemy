package com.challenge.alkemy.security.service;

import com.challenge.alkemy.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {

    UserDetails loadUserByUsername(String username);

    String createUser(Usuario usuario);
}
