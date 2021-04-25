package com.spiet.sendmail.service.impl;

import com.spiet.sendmail.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService implements IEmailService {

    private JavaMailSender javaMailSender;

    @Value("${application.mail.default-remetent}")
    private String remetente;
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String message, List<String> mailsList) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(remetente);
        mailMessage.setSubject("Email do sistema!");
        mailMessage.setText("Olá, estou passando aqui denovo!");

        String[] mails = mailsList.toArray(new String[mailsList.size()]);

        mailMessage.setTo(mails);

        javaMailSender.send(mailMessage);
    }
}
