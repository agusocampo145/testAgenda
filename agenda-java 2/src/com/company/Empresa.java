package com.company;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    List<Persona> contactos;
    private String razonSocial;
    private String cuit;
    private Ciudad sedePrincipal;

    public Empresa(String razonSocial, String cuit, Ciudad ciudad) {
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.sedePrincipal = ciudad;
        this.contactos = new ArrayList<Persona>();
    }

    // ------------ Getters & Setters ------------------------ //

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public Ciudad getSedePrincipal() {
        return sedePrincipal;
    }

    public void setSedePrincipal(Ciudad sedePrincipal) {
        this.sedePrincipal = sedePrincipal;
    }

    public List<Persona> getContactos() {
        return contactos;
    }

    public void setContactos(List<Persona> contactos) {
        this.contactos = contactos;
    }
}
