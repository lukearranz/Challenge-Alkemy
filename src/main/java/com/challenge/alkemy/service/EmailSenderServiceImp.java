package com.challenge.alkemy.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderServiceImp implements EmailSenderService {

    @Override
    public void sendSimpleEmail(String toEmail, String body, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("polloarranz@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
    }
}
