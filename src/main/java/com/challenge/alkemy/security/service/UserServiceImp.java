package com.challenge.alkemy.security.service;

import com.challenge.alkemy.entity.Usuario;
import com.challenge.alkemy.entity.dto.authDto.AuthMapper;
import com.challenge.alkemy.entity.dto.authDto.request.RegisterRequestDto;
import com.challenge.alkemy.entity.dto.authDto.response.RegisterResponseDto;
import com.challenge.alkemy.error.user.UsernameAlreadyTakenException;
import com.challenge.alkemy.repository.UsuarioRepository;
import com.challenge.alkemy.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserDetailsService, UserService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordService passwordService;
    private final EmailSenderService emailSenderService;
    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario userDB = usuarioRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("USUARIO NO ENCONTRADO"));
        return new User(userDB.getUsername(), userDB.getPassword(), Collections.emptyList());
    }

    @Override
    public RegisterResponseDto createUser(RegisterRequestDto userRequest) throws UsernameAlreadyTakenException {

        Optional<Usuario> usuarioDB = usuarioRepository.findByUsername(userRequest.getUsername());
        if (usuarioDB.isPresent()) {
            throw new UsernameAlreadyTakenException("EL USUARIO YA SE ENCUENTRA EN USO");
        }

        userRequest.setPassword(passwordService.encryptPassword(userRequest.getPassword()));
        Usuario usuarioGuardado = usuarioRepository.save(authMapper.registerRequestDtoToUsuario(userRequest));

        emailSenderService.sendSimpleEmail(
                userRequest.getEmail(),
                "Gracias " + userRequest.getUsername() + " por registrarse en esta API!",
                "Mail de prueba Registro"
        );

        return authMapper.usuarioToRegisterResponseDto(usuarioGuardado);
    }
}
