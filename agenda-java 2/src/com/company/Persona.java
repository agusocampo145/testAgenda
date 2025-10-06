package com.company;

public class Persona {

    private static int contadorId = 1;

    private int id;
    private String numeroDeTelefono;
    private String nombre;
    private String apellido;
    private Ciudad ciudad;


    public Persona(String numeroDeTelefono, String nombre, String apellido, Ciudad ciudad) {
        this.id = contadorId++;
        this.numeroDeTelefono = numeroDeTelefono;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ciudad = ciudad;
    }


    // ------------ Getters & Setters ------------------------ //


    public int getId() {
        return id;
    }

    public String getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    public void setNumeroDeTelefono(String numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
    }

    public String getNombre() {
        return nombre;
    }

    /*
    Estaba en duda en si crear los setters de 'Nombre' y 'Apellido' porque si bien es comun que una persona
     cambie de ciudad o numero, no es tan comun que cambie de nombre o apellido.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

}
