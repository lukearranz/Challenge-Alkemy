package com.challenge.alkemy;

import com.challenge.alkemy.service.EmailSenderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import net.bytebuddy.build.Plugin;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Alkemy ApiRest Challenge", version = "1.5", description = "API desarrollada en SpringBoot" +
		" utilizando JWT para la seguridad, MySql para la persistencia de datos y OpenApi+Swagger para la documentación."))
public class ChallengeAlkemyApplication {

	@Autowired
	private EmailSenderService mailService;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeAlkemyApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}


}
