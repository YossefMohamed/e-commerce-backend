package com.ecommerce.fullstack.code.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailListener {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    @EventListener
    void sendEmailEvent(EmailEvent emailEvent) {
        System.out.println(emailEvent.getUser().getUserName());
        String from = "sender@gmail.com";
        String to = "ms01010103727@gmail.com"; // emailEvent.getUser().getUserEmail()
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("luv2code welcome message");
        message.setText("Hello " + emailEvent.getUser().getUserName() + "! This is a Welcome email.");
        mailSender.send(message);
    }

}
