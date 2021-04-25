package com.spiet.sendmail;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class SendmailApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Scheduled(cron = "0 47 21 1/1 * ?")
    private void teste() {
        System.out.println("TESTANDOOOO!");
    }

    public static void main(String[] args) {
        SpringApplication.run(SendmailApplication.class, args);
    }

}
