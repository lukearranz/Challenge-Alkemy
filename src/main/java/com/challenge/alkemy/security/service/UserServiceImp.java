package com.challenge.alkemy.security.service;

import com.challenge.alkemy.entity.Usuario;
import com.challenge.alkemy.repository.UsuarioRepository;
import com.challenge.alkemy.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImp implements UserDetailsService, UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Logic to get the user from the database.
        Usuario userDB = usuarioRepository.findUsuarioByUsername(username);

        if (userDB == null) {
            throw new UsernameNotFoundException("USUARIO NO ENCONTRADO");
        }
        return new User(userDB.getUsername(), userDB.getPassword(), new ArrayList<>());
    }

    @Override
    public String createUser(Usuario usuario) {

        // Buscamos el User en la DB para ver si ya existe.
        Usuario usuarioDB = usuarioRepository.findUsuarioByUsername(usuario.getUsername());
        if (usuarioDB == null) {
            // Encriptamos la contraseña antes de guardarla
            usuario.setPassword(passwordService.encryptPassword(usuario.getPassword()));
            usuarioRepository.save(usuario);
            // Aqui enviamos el mail de registro.
            emailSenderService.sendSimpleEmail(
                    usuario.getEmail(),
                    "Gracias " + usuario.getUsername() + " por registrarse en esta API!",
                    "Mail de prueba Registro"
            );
            return "USUARIO CREADO CON EXITO";
        }
        return "EL USUARIO ELEGIDO YA ESTA EN USO";
    }


}
