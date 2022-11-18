package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


    @DisplayName("Verificar si podemos buscar un Usuario por nombre")
    void findUsuarioByUsername() {

        usuarioRepository.save(usuarioFicticio);
        Assertions.assertEquals(usuarioFicticio, usuarioRepository.findByUsername("TestingUser").get());
    }
}