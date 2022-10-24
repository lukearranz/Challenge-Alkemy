package com.challenge.alkemy.security.service;

import com.challenge.alkemy.entity.dto.authDto.request.RegisterRequestDto;
import com.challenge.alkemy.entity.dto.authDto.response.RegisterResponseDto;
import com.challenge.alkemy.error.user.UsernameAlreadyTakenException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    RegisterResponseDto createUser(RegisterRequestDto usuario) throws UsernameAlreadyTakenException;
}
