package com.wolfalone.gamecdbackend.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventHandle {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    /**
     * Hành động bấm chuông cửa
     */
    public void sendEmail(String email, String subject, String text) {
        // Phát ra một sự kiện DoorBellEvent
        // source (Nguồn phát ra) chính là class này
        applicationEventPublisher.publishEvent(new SendEmailEvent(email, subject, text));
    }
}
