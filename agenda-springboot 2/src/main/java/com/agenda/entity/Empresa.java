package com.agenda.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Debe indicar un nombre.")
    private String nombre;

    @Pattern(regexp = "\\d{2}-\\d{8}-\\d", message = "El formato del CUIT debe ser NN-NNNNNNNN-N")
    @NotNull(message = "Debe indicar una cuit válido.")
    private String cuit;

    @ManyToOne
    @JoinColumn(name = "ciudad_id")
    @NotNull(message = "Debe indicar una ciudad.")
    private Ciudad sedePrincipal;

    @ManyToMany
    @JoinTable(
            name = "empresa_contactos",
            joinColumns = @JoinColumn(name = "empresa_id"),
            inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private Set<Persona> contactos = new HashSet<>();

    public Empresa(String nombre, String cuit, Ciudad ciudad) {
        this.nombre = nombre;
        this.cuit = cuit;
        this.sedePrincipal = ciudad;
    }

    public Empresa() {
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuit() {
        return cuit;
    }

    public void setRubro(String rubro) {
        this.cuit = rubro;
    }

    public Set<Persona> getContactos() {
        return contactos;
    }

    public void setContactos(Set<Persona> contactos) {
        this.contactos = contactos;
    }

    public Ciudad getCiudad() {
        return sedePrincipal;
    }

    public void setCiudad(Ciudad ciudad) {
        this.sedePrincipal = ciudad;
    }
}
