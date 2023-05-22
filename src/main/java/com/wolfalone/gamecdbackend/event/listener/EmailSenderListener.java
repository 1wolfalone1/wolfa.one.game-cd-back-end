package com.wolfalone.gamecdbackend.event.listener;

import com.wolfalone.gamecdbackend.event.SendEmailEvent;
import com.wolfalone.gamecdbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

//@Component
//
//public class EmailSenderListener  implements ApplicationListener<SendEmailEvent> {
//    @Autowired
//    private EmailService emailService;
//    @Override
//    @Async
//    public void onApplicationEvent(SendEmailEvent event) {
//        emailService.sendSimpleEmail(event.getEmail(), event.getSubject(), event.getText());
//    }
//}
