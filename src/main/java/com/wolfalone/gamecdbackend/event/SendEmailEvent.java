package com.wolfalone.gamecdbackend.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class SendEmailEvent extends ApplicationEvent {
    private String email;
    private String subject;
    private String text;

    public SendEmailEvent(String email, String subject, String text) {
        super(email);
        this.email = email;
        this.subject =subject;
        this.text = text;
    }
}
