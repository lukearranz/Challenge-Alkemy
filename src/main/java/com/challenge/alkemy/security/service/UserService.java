package com.challenge.alkemy.security.service;

import com.challenge.alkemy.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface UserService {

    UserDetails loadUserByUsername(String username);

    String createUser(Usuario usuario);
}
