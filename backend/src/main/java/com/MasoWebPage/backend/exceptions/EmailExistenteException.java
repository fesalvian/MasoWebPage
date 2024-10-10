package com.MasoWebPage.backend.exceptions;

public class EmailExistenteException extends RuntimeException {
    public EmailExistenteException(String s) {
        super(s);
    }
}
