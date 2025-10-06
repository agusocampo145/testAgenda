package com.company;

import com.company.exceptions.CiudadNoExistente;
import com.company.exceptions.ContactoNoExistenteException;
import com.company.exceptions.DatoYaexistente;
import com.company.exceptions.EmpresaNoExistente;
import com.company.utils.InputHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Proyecto: Agenda de Contactos - Evaluación Técnica
 * Autor: Agustín Ocampo
 *
 * Descripción:
 * Aplicación de consola en Java para gestionar una agenda de personas y empresas.
 * Permite registrar personas con ciudad, empresas con CUIT validado, y asociar contactos.
 * Incluye buscadores por nombre, ciudad o múltiples ciudades.
 * Implementa validación de datos, manejo de excepciones personalizadas y estructura orientada a objetos.
 * Los datos se almacenan en memoria y se ingresan a través de consola.
 *
 *
 * Consideraciones tomadas fuera de la consigna:
 * Datos de Empresa (CUIT y Sede Principal)
 * Ciudad como clase ( Con campos de ciudad, provincia y pais)
 * Pueden existir mas de una ciudad con el mismo nombre ( Si bien pueden crearse, al momento de busqueda se seleccionara a 1era)
 * Verificacion de dato previamente existente al crear nuevo dato.
 * Verificacion de patron de CUIT
 *
 */
public class Main {

    public static void main(String[] args) {

        Agenda agenda = new Agenda();
        boolean continuarFlujo = true;

        while (continuarFlujo) {
            System.out.println("\n--- MENU AGENDA---");
            System.out.println("1. Agregar persona");
            System.out.println("2. Agregar empresa");
            System.out.println("3. Agregar contacto a una empresa");
            System.out.println("4. Buscar personas por nombre");
            System.out.println("5. Buscar personas por ciudad");
            System.out.println("6. Buscar personas por nombre y múltiples ciudades");
            System.out.println("7. Mostrar todas las empresas y sus contactos");
            System.out.println("8. Mostrar todos los contactos");
            System.out.println("9. Agregar ciudad");
            System.out.println("0. Salir");

            int opcion = InputHelper.leerOpcion("Ingrese el numero de opcion", 0, 9);

            try {
                switch (opcion) {
                    case 1:
                        agregarPersona(agenda);
                        break;
                    case 2:
                        agregarEmpresa(agenda);
                        break;
                    case 3:
                        agregarContactoAEmpresa(agenda);
                        break;
                    case 4:
                        buscarPorNombre(agenda);
                        break;
                    case 5:
                        buscarPorCiudad(agenda);
                        break;
                    case 6:
                        buscarPorNombreApellidoYVariasCiudades(agenda);
                        break;
                    case 7:
                        mostrarEmpresasYContactos(agenda);
                        break;
                    case 8:
                        mostrarContactos(agenda);
                        break;
                    case 9:
                        agregarCiudad(agenda);
                        break;
                    case 0:
                        System.out.println("Cerrando agenda");
                        continuarFlujo = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void agregarPersona(Agenda agenda) throws DatoYaexistente {
        Ciudad ciudad;
        String nombre = InputHelper.leerTexto("Nombre");
        String apellido = InputHelper.leerTexto("Apellido");
        String telefono = InputHelper.leerTexto("Número de teléfono");
        String ciudadNombre = InputHelper.leerTexto("Ciudad");
        String provincia = InputHelper.leerTexto("Provincia");

        /* -
        En este utilizo la busqueda por telefono como identificador unico, por considerar que
        pueden existir mas de una persona con el mimso nombre y apellido.
        Tambien podria usarse el id.
         - */
        if (agenda.buscarPersonaPorTelefono(telefono).isPresent()) {
            throw new DatoYaexistente("Ya existe un contacto con este numero: " + telefono);
        }
        Optional<Ciudad> ciudadExistente = agenda.buscarCiudadPorNombre(ciudadNombre);
        if (ciudadExistente.isPresent()) {
            ciudad = ciudadExistente.get();
        } else {
            ciudad = new Ciudad(ciudadNombre, provincia, "Argentina");
            agenda.getCiudades().add(ciudad);
        }
        Persona persona = new Persona(telefono, nombre, apellido, ciudad);
        agenda.agregarPersona(persona);
        System.out.println("Persona agregada correctamente");
    }

    private static void agregarEmpresa(Agenda agenda) throws DatoYaexistente {
        Ciudad ciudad;
        String cuit = InputHelper.leerCUIT("CUIT");
        /*
        Se utiliza al cuit como identificador unico, en este caso empresa no tiene id.
        */

        if (agenda.buscarEmpresaPorCuit(cuit).isPresent()) {
            throw new DatoYaexistente("Ya existe una empresa con ese CUIT");
        }
        String razonSocial = InputHelper.leerTexto("Razón social");
        String ciudadNombre = InputHelper.leerTexto("Ciudad");
        String provincia = InputHelper.leerTexto("Provincia");
        Optional<Ciudad> ciudadExistente = agenda.buscarCiudadPorNombre(ciudadNombre);
        if (ciudadExistente.isPresent()) {
            ciudad = ciudadExistente.get();
        } else {
            ciudad = new Ciudad(ciudadNombre, provincia, "Argentina");
            agenda.getCiudades().add(ciudad);
        }
        Empresa empresa = new Empresa(razonSocial, cuit, ciudad);
        agenda.agregarEmpresa(empresa);
        System.out.println("Empresa agregada correctamente");
    }


    //TODO: Consultar si consideramos la existenia de mas de un nombre y/o apellido, o si ambos nombres irian dentro del campo nombre, mismo con mas de un apellido
    private static void agregarContactoAEmpresa(Agenda agenda) throws ContactoNoExistenteException, EmpresaNoExistente {
        String nombreEmpresa = InputHelper.leerTexto("Nombre de la empresa");
        String nombre = InputHelper.leerTexto("Nombre de la persona a agregar");
        String apellido = InputHelper.leerTexto("Apellido de la persona a agregar");

        Persona persona = agenda.buscarPersonaPorNombreyApellido(nombre, apellido).get(0);
        // En caso de generar error la funcion de agregado, se maneja en el try/catch del menu principal
        agenda.agregarContactoAEmpresa(nombreEmpresa, persona);
        System.out.println("Contacto agregado.");
    }

    private static void buscarPorNombre(Agenda agenda) throws ContactoNoExistenteException {
        String nombre = InputHelper.leerTexto("Nombre");
        List<Persona> resultados = agenda.buscarPersonaPorNombre(nombre);
        mostrarResultados(resultados);
    }

    private static void buscarPorCiudad(Agenda agenda) throws CiudadNoExistente {
        String ciudad = InputHelper.leerTexto("Ciudad");
        Optional<Ciudad> ciudadExistente = agenda.buscarCiudadPorNombre(ciudad);
        if (!ciudadExistente.isPresent()) {
            throw new CiudadNoExistente("No existe una ciudad con ese nombre");
        }
        List<Persona> resultados = agenda.buscarPersonaPorCiudad(ciudadExistente.get());
        mostrarResultados(resultados);
    }

    private static void buscarPorNombreApellidoYVariasCiudades(Agenda agenda) {
        String nombre = InputHelper.leerTexto("Nombre");
        String apellido = InputHelper.leerTexto("Appelido");
        int cantidad = InputHelper.leerEntero("Cantidad de ciudades");
        List<String> ciudades = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            ciudades.add(InputHelper.leerTexto("Ciudad #" + (i + 1)));
        }
        List<Persona> resultados = agenda.buscarPorNombreYVariasCiudades(nombre, apellido, ciudades);
        mostrarResultados(resultados);
    }

    private static void mostrarResultados(List<Persona> personas) {
        if (personas.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            for (Persona p : personas) {
                System.out.println(p.getId() + " - " + p.getNombre() + " " + p.getApellido() + " (" + p.getCiudad().getNombre() + ", " + p.getCiudad().getProvincia() + " - " + p.getCiudad().getPais() + ")");
            }
        }
    }

    private static void agregarCiudad(Agenda agenda) throws DatoYaexistente {
        Ciudad ciudad;
        String ciudadNombre = InputHelper.leerTexto("Ciudad");
        String provincia = InputHelper.leerTexto("Provincia");
        String pais = InputHelper.leerTexto("Pais");
        Optional<Ciudad> ciudadExistente = agenda.buscarCiudadPorNombre(ciudadNombre);
        if (ciudadExistente.isPresent()) {
            throw new DatoYaexistente("Ya existe una ciudad con ese nombre");
        } else {
            ciudad = new Ciudad(ciudadNombre, provincia, pais);
            agenda.getCiudades().add(ciudad);
        }
    }

    private static void mostrarEmpresasYContactos(Agenda agenda) {
        for (Empresa empresa : agenda.getEmpresas()) {
            System.out.println("\n" + empresa.getRazonSocial() + " (" + empresa.getCuit() + ")");
            System.out.println("Sede principal: " + empresa.getSedePrincipal().getNombre() + ", " + empresa.getSedePrincipal().getProvincia() + " - " + empresa.getSedePrincipal().getPais());
            if (empresa.getContactos().isEmpty()) {
                System.out.println("   - Sin contactos aún.");
            } else {
                System.out.println("   - Contactos:");
                for (Persona p : empresa.getContactos()) {
                    System.out.println("     · " + p.getNombre() + " " + p.getApellido() + " (" + p.getId() + ")");
                }
            }
        }
    }

    private static void mostrarContactos(Agenda agenda) {
        if (agenda.getPersonas().isEmpty()) {
            System.out.println("   - Sin contactos aún.");
        } else {
            System.out.println("   - Contactos:");
            for (Persona p : agenda.getPersonas()) {
                System.out.println("     · " + p.getNombre() + " " + p.getApellido() + " (" + p.getId() + ")" + " " + p.getCiudad().getNombre() + ", " + p.getCiudad().getProvincia() + " - " + p.getCiudad().getPais());
            }
        }
    }
}
