package com.startsteps.ecommerceapi.user.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
