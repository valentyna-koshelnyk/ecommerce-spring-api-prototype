package com.startsteps.ecommerceapi.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailService")
@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class EmailServiceImpl implements EmailService{

    private JavaMailSender mailSender;


    @Async
    public void sendEmail(SimpleMailMessage email){
        mailSender.send(email);
    }
}
