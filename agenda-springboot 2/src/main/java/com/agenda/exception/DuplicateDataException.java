package com.agenda.exception;

public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException(String mensaje) {
        super(mensaje);
    }
}
