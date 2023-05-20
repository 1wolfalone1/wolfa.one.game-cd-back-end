package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceIml implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nhatthienfpt@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            System.out.println("111111111111111111111111");
            mailSender.send(message);
            System.out.println(message);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}
