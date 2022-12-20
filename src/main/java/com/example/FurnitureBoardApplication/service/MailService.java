package com.example.FurnitureBoardApplication.service;

import com.example.FurnitureBoardApplication.entity.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSenderImpl javaMailSender;

    private final String title = "안녕하세요 FurnitureBoard 임시 비밀번호 안내 이메일 입니다.";
    private final String message = "안녕하세요 FurnitureBoard 임시 비밀번호 안내 이메일 입니다."
            +"\n"+"회원님의 임시 비밀번호는 아래와 같이 변경 되셨으며 로그인 후 반드시 비밀번호를 변경해주시기 바랍니다.";
    private final String fromAddress = "kikkb1114@gmail.com";

    /**이메일 생성**/
    public Mail createMail(String tmpPassword, String userEmail){
        Mail mail = Mail.builder()
                .toAddress(userEmail)
                .title(title)
                .message(message + tmpPassword)
                .fromAddress(fromAddress)
                .build();

        System.out.println("메일 생성 완료");
        return mail;
    }

    /**이메일 전송**/
    public void sendMail(Mail mail){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getToAddress());
        simpleMailMessage.setSubject(mail.getTitle());
        simpleMailMessage.setText(mail.getMessage());
        simpleMailMessage.setFrom(mail.getFromAddress());
        simpleMailMessage.setReplyTo(mail.getFromAddress());

        javaMailSender.send(simpleMailMessage);

        System.out.println("메일 전송 완료");
    }
}
