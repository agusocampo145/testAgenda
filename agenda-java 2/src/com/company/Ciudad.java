package com.company;

/* Se creo ciudad como una clase y no solo un campo 'string' para poder tolerar ciudades con el mismo nombre pero de diferentes provincias/paises.
    Tambien facilitaria en un futuro la escalabilidad si el sistema quiere usarse de manera internacional.
*/
public class Ciudad {
    private String nombre;
    private String provincia;
    private String pais;

    public Ciudad(String nombre, String provincia, String pais) {
        this.nombre = nombre;
        this.provincia = provincia;
        this.pais = pais;
    }

    // ------------ Getters & Setters ------------------------ //

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getPais() {
        return pais;
    }
}
