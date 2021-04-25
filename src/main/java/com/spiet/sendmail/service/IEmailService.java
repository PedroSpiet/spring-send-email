package com.spiet.sendmail.service;

import java.util.List;

public interface IEmailService {
    void sendMail(String message, List<String> mailsList);
}
