package com.company.exceptions;

public class ContactoNoExistenteException extends Exception {
    public ContactoNoExistenteException(String mensaje) {
        super(mensaje);
    }
}
