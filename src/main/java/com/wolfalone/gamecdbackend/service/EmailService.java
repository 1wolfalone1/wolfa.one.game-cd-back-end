package com.wolfalone.gamecdbackend.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    boolean sendSimpleEmail(String to, String subject, String text);
}
