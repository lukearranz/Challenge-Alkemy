package com.challenge.alkemy.service;

public interface EmailSenderService {
    void sendSimpleEmail(String toEmail, String body, String subject);

}
