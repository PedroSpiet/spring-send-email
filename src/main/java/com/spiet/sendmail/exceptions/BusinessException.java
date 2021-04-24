package com.spiet.sendmail.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = -4691324007331946466L;

    public BusinessException(String msg) {
        super(msg);
    }
}
