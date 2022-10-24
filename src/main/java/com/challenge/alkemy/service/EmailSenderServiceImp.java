package com.challenge.alkemy.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderServiceImp implements EmailSenderService {

    private JavaMailSender mailSender;

    @Override
    public void sendSimpleEmail(String toEmail, String body, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("polloarranz@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

    }
}
