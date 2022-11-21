package com.challenge.alkemy.repository;

import com.challenge.alkemy.entity.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UsuarioRepositoryTest {

    private final String USUARIO = "TestingUser";
    private final String EMAIL = "testinguser@hotmail.com";
    private final String PASSWORD = "TestingUserPass";

    @Autowired
    private UsuarioRepository usuarioRepository;

    Usuario usuarioFicticio = Usuario.builder()
            .username(USUARIO)
            .email(EMAIL)
            .password(PASSWORD)
            .build();

    @BeforeEach
    void setUp() {
        usuarioRepository.save(usuarioFicticio);
    }

    @AfterEach
    void tearDown() {
        usuarioRepository.deleteAll();
    }

    @Test
    void findUsuarioByUsername() {

        Optional<Usuario> expected = usuarioRepository.findByUsername(USUARIO);
        assertThat(expected).isPresent();
        assertThat(expected).isNotEmpty();
        assertThat(expected.get().getUsername()).isEqualTo(USUARIO);
        assertThat(expected.get().getPassword()).isEqualTo(PASSWORD);
        assertThat(expected.get().getEmail()).isEqualTo(EMAIL);
    }
}