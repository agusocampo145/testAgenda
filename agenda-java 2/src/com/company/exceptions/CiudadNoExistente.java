package com.company.exceptions;

public class CiudadNoExistente extends Exception {
    public CiudadNoExistente(String mensaje) {
        super(mensaje);
    }
}