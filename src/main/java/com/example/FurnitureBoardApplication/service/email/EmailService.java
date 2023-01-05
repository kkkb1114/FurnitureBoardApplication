package com.example.FurnitureBoardApplication.service.email;

import com.example.FurnitureBoardApplication.dto.MailDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender javaMailSender;

    public void sendFindPasswordCode(MailDto mailDto){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailDto.getFromAddress());
        mailMessage.setTo(mailDto.getToAddress());
        mailMessage.setSubject(mailDto.getTitle());
        mailMessage.setText(mailDto.getMessage());
        javaMailSender.send(mailMessage);
    }
}
