package com.challenge.alkemy;

import com.challenge.alkemy.entity.dto.authDto.request.RegisterRequestDto;
import com.challenge.alkemy.security.service.UserServiceImp;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Alkemy ApiRest Challenge", version = "1.5", description = "API desarrollada en SpringBoot" +
		" utilizando JWT para la seguridad, MySql para la persistencia de datos y OpenApi+Swagger para la documentación." +
		" Los tests unitarios se realizaron con JUnit5 y Mockito."
))
public class ChallengeAlkemyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeAlkemyApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	CommandLineRunner runner(UserServiceImp userServiceImp) {
		// Creamos un Usuario al iniciar la aplicacion.
		return args -> {
			RegisterRequestDto user = RegisterRequestDto.builder()
					.username("username")
					.password("password")
					.email("usuario@gmail.com")
					.build();
			userServiceImp.createUser(user);
		};
	}

}
