package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Usuario;
import com.challenge.alkemy.security.service.PasswordService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Creamos un usuario ficticio.
    Usuario usuarioFicticio = Usuario.builder()
            .username("TestingUser")
            .email("lucas.arranz@hotmail.com")
            .password("TestingUser")
            .build();

    @AfterEach
    void tearDown() {
        usuarioRepository.deleteAll();
    }

    @Test
    @DisplayName("Verificar si podemos buscar un Usuario por nombre")
    void findUsuarioByUsername() {
        // Guardamos el User.
        usuarioRepository.save(usuarioFicticio);

        Assertions.assertEquals(usuarioFicticio, usuarioRepository.findUsuarioByUsername("TestingUser"));

    }
}