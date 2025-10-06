package com.company;

import com.company.exceptions.ContactoNoExistenteException;
import com.company.exceptions.EmpresaNoExistente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Agenda {

    private List<Persona> personas;
    private List<Empresa> empresas;
    private List<Ciudad> ciudades;

    public Agenda() {
        this.personas = new ArrayList<Persona>();
        this.empresas = new ArrayList<Empresa>();
        this.ciudades = new ArrayList<Ciudad>();
    }


    public void agregarPersona(Persona persona) {
        personas.add(persona);
    }

    public void agregarEmpresa(Empresa empresa) {
        empresas.add(empresa);
    }

    //------- Busquedas por parametro ------- //

    //TODO:consultar sobre esta consideracion:
    // *Considero que pueden existir varias personas con el mismo nombre, apellido e incluso combinacion de nombre y apellido, por es las funciones de busqueda de persona retornan una lista.
    public List<Persona> buscarPersonaPorNombre(String nombre)  {
        return personas.stream().filter(persona -> persona.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList());
    }

    public List<Persona> buscarPersonaPorApellido(String apellido) {
        return personas.stream().filter(persona -> persona.getApellido().equalsIgnoreCase(apellido)).collect(Collectors.toList());
    }

    public List<Persona> buscarPersonaPorNombreyApellido(String nombre, String apellido) {
        return personas.stream().filter(persona -> persona.getNombre().equalsIgnoreCase(nombre)).filter(persona -> persona.getApellido().equalsIgnoreCase(apellido)).collect(Collectors.toList());
    }

    public List<Persona> buscarPersonaPorCiudad(Ciudad ciudad) {
        return personas.stream().filter(persona -> persona.getCiudad().equals(ciudad)).collect(Collectors.toList());
    }

    // Es un optional porque en el main, se utiliza para verificar si existe o no, entonces puede devolver un valor vacio sin generar un error.
    public Optional<Persona> buscarPersonaPorTelefono(String numeroDeTelefono) {
        return personas.stream().filter(persona -> persona.getNumeroDeTelefono().equals(numeroDeTelefono)).findFirst();
    }

    public List<Persona> buscarPorNombreYVariasCiudades(String nombre, String apellido, List<String> ciudades) {
        return personas.stream()
                .filter(persona -> persona.getNombre().equalsIgnoreCase(nombre) && persona.getApellido().equalsIgnoreCase(apellido) &&
                        ciudades.contains(persona.getCiudad().getNombre()))
                .collect(Collectors.toList());
    }

    /* En caso de requerirlo podrian implementarse funciones de busqueda de empresa por cuit o ciudad, pero como la consiga no lo pide en esta caso no las implemente. */
    public Empresa buscarEmpresaPorNombre(String razonSocial) throws EmpresaNoExistente {
        return empresas.stream().filter(empresaFilter -> empresaFilter.getRazonSocial().equalsIgnoreCase(razonSocial)).findFirst().orElseThrow(() -> new EmpresaNoExistente("No existe una empresa con esa razon social"));
    }

    // Es un optional porque en el main, se utiliza para verificar si existe o no, entonces puede devolver un valor vacio sin generar un error.
    public Optional<Empresa> buscarEmpresaPorCuit(String cuit) {
        return empresas.stream().filter(empresaFilter -> empresaFilter.getCuit().equalsIgnoreCase(cuit)).findFirst();
    }

    // Es un optional porque en el main, se utiliza para verificar si existe o no, entonces puede devolver un valor vacio sin generar un error.
    /*
    En esta instancia, si bien se creo a Ciudad como una Clase y no solamente un campo 'String' para poder
    facilitar la escalabilidad en un futuro, y permitir que existan ciudades con el mismo nombre pero de diferentes provincias/paises,
    en este caso aun no se implemento esta posibilidad, de momento consideramos que el nombre de cada ciudad sera unico.
     */
    public Optional<Ciudad> buscarCiudadPorNombre(String ciudad) {
        return ciudades.stream().filter(ciudadFilter -> ciudadFilter.getNombre().equalsIgnoreCase(ciudad)).findFirst();
    }

    public void agregarContactoAEmpresa(String razonSocial, Persona contacto) throws ContactoNoExistenteException, EmpresaNoExistente {

        if (!personas.contains(contacto)) {
            throw new ContactoNoExistenteException("El contecto debe existir en la agenda");
        }
        Empresa empresa = buscarEmpresaPorNombre(razonSocial);
        empresa.getContactos().add(contacto);
    }

    // ------------ Getters & Setters ------------------------ //

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
}
