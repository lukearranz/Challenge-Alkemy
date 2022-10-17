package com.challenge.alkemy.controller;

import com.challenge.alkemy.entity.Personaje;
import com.challenge.alkemy.service.PersonajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonajeController.class)
class PersonajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonajeService personajeService;

    private Personaje personaje;

    @BeforeEach
    void setUp() {
        personaje = Personaje.builder()
                .nombre("Hulk")
                .edad(60)
                .peso(100.0)
                .historia("Terrible bicho verde")
                .imagen("https://upload.wikimedia.org/wikipedia/commons/a/a0/Hulk_%282540708438%29.jpg")
                .personajeId(1L)
                .build();
    }

    @Test
    void savePersonaje() throws Exception {
        Personaje inputPersonaje = Personaje.builder()
                .nombre("Hulk")
                .edad(60)
                .peso(100.0)
                .historia("Terrible bicho verde")
                .imagen("https://upload.wikimedia.org/wikipedia/commons/a/a0/Hulk_%282540708438%29.jpg")
                .build();

        //Mockito.when(personajeService.savePersonaje(inputPersonaje)).thenReturn(personaje);

        mockMvc.perform(post("/personaje")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                "        \"nombre\": \"Lucas\",\n" +
                                "        \"edad\": \"29\",\n" +
                                "        \"peso\": \"67.5\"\n" +
                                "        \"historia\": \"es tremendo perverso\"\n" +
                                "        \"imagen\": \"https://upload.wikimedia.org/wikipedia/commons/a/a0/Hulk_%282540708438%29.jpg\"\n" +
                                "    }"))
                .andExpect(status().isOk());
    }

}