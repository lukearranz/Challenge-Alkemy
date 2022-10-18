package com.challenge.alkemy.security.service;

import com.challenge.alkemy.entity.Usuario;
import com.challenge.alkemy.error.user.UsernameAlreadyTakenException;
import com.challenge.alkemy.repository.UsuarioRepository;
import com.challenge.alkemy.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserDetailsService, UserService {

    private UsuarioRepository usuarioRepository;
    private PasswordService passwordService;
    private EmailSenderService emailSenderService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Logic to get the user from the database.
        Usuario userDB = usuarioRepository.findUsuarioByUsername(username).orElseThrow(()-> new UsernameNotFoundException("USUARIO NO ENCONTRADO"));
        return new User(userDB.getUsername(), userDB.getPassword(), new ArrayList<>());
    }

    @Override
    public void createUser(Usuario usuario) throws UsernameAlreadyTakenException {

        // Buscamos el User en la DB para ver si ya existe.
        if (usuarioRepository.findUsuarioByUsername(usuario.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException("EL USUARIO YA SE ENCUENTRA EN USO");
        }
        // Encriptamos la contraseña antes de guardarla
        usuario.setPassword(passwordService.encryptPassword(usuario.getPassword()));
        usuarioRepository.save(usuario);
        // Aqui enviamos el mail de registro.
        emailSenderService.sendSimpleEmail(
                usuario.getEmail(),
                "Gracias " + usuario.getUsername() + " por registrarse en esta API!",
                "Mail de prueba Registro"
        );
    }
}
