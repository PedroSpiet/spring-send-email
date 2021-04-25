package com.spiet.sendmail;

import com.spiet.sendmail.service.impl.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class SendmailApplication {

    @Autowired
    EmailService emailService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Scheduled(cron = "0 47 21 1/1 * ?")
    private void teste() {
        System.out.println("TESTANDOOOO!");
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            List<String> emails = Arrays.asList("testepedro-8535fc@inbox.mailtrap.io");
            emailService.sendMail("Testando serviço de emails", emails);
            System.out.println("FOI");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SendmailApplication.class, args);
    }

}
