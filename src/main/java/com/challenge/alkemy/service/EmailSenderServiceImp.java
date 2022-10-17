package com.challenge.alkemy.service;

import com.challenge.alkemy.controller.GeneroController;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderServiceImp implements EmailSenderService {

    private JavaMailSender mailSender;

    // Logger for debugging the application
    private final Logger LOGGER = LoggerFactory.getLogger(GeneroController.class);

    @Override
    public void sendSimpleEmail(String toEmail, String body, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("polloarranz@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        LOGGER.info("Email enviado con exito!");

    }
}
