package com.springboot.pjt1.member.service;

public interface EmailService {

    void sendMail(String to, String sub, String text);
}
