package com.agenda.service;

import com.agenda.dto.CiudadRequestDto;
import com.agenda.dto.EmpresaRequestDto;
import com.agenda.dto.PersonaRequestDto;
import com.agenda.entity.Ciudad;
import com.agenda.entity.Empresa;
import com.agenda.entity.Persona;
import com.agenda.exception.DuplicateDataException;
import com.agenda.exception.InvalidDataException;
import com.agenda.exception.ResourceNotFoundException;
import com.agenda.repository.CiudadRepository;
import com.agenda.repository.EmpresaRepository;
import com.agenda.repository.PersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    private static final Logger logger = LoggerFactory.getLogger(AgendaService.class);
    private final PersonaRepository personaRepository;
    private final CiudadRepository ciudadRepository;
    private final EmpresaRepository empresaRepository;


    public AgendaService(PersonaRepository personaRepository,
                         CiudadRepository ciudadRepository,
                         EmpresaRepository empresaRepository) {
        this.personaRepository = personaRepository;
        this.ciudadRepository = ciudadRepository;
        this.empresaRepository = empresaRepository;
    }

    public Persona crearPersona(PersonaRequestDto persona) {
        if (personaRepository.existsByTelefono(persona.getTelefono())) {
            throw new DuplicateDataException("Ya existe una persona con ese teléfono.");
        }

        if (persona.getNombre() == null || persona.getNombre().isBlank()) {
            throw new InvalidDataException("El nombre de la persona es obligatorio.");
        }
        Ciudad ciudadEntrada = persona.getCiudad();
        Ciudad ciudadAsignada = null;

        /*
            Esta verificacion de una ciudad ya existente, fue creada pensando en la consigna de el ejercicio
            'Agenda Springboot' es decir, la cual se testearia mediante request's de Postman, Swagger, o alguna otra herramienta
            Para ese caso, se penso en la spobilidad de que una persona podria no conocer la exitencia de que ciudades ya existen
            y no conozca el id de la ciudad que desea seleccionar e intente seleccionarla mediante un nombre.
            Al integrarlo con el frontend, y al mostrarle al usuario las ciudades existentes, el frontend conoce el id de la ciudad
            (o bien podria hacer una busqueda por nombre y encontrar el id) y no haria falta esta verificacion, ya que se seleccionaria
            directamente mediante ID la ciudad buscada.
            Como se reutilizo el codigo de la consigna 'Agenda Springboot' se dejó este metodo un poco mas largo y no tan eficiente.
         */
        if (ciudadEntrada.getId() != null && ciudadEntrada.getId() > 0) {
            ciudadAsignada = ciudadRepository.findById(ciudadEntrada.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ciudad no encontrada con ID: " + ciudadEntrada.getId()));
        } else if (ciudadEntrada.getNombre() != null && !ciudadEntrada.getNombre().isBlank()) {
            ciudadAsignada = ciudadRepository.findByNombre(ciudadEntrada.getNombre());
            if (ciudadAsignada == null) {
                throw new ResourceNotFoundException("Ciudad no encontrada con nombre: " + ciudadEntrada.getNombre());
            }
        } else {
            throw new InvalidDataException("La ciudad debe tener al menos un ID o nombre válido.");
        }

        persona.setCiudad(ciudadAsignada);

        logger.info("Creando persona: {} {}", persona.getNombre(), persona.getApellido());
        Persona nuevaPersona = new Persona(persona.getNombre(), persona.getApellido(), persona.getCiudad(), persona.getTelefono());
        return personaRepository.save(nuevaPersona);
    }

    public List<Persona> obtenerTodasLasPersonas() {
        return personaRepository.findAll();
    }

    public Persona obtenerPersonaPorId(Long id) {
        return personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));
    }

    public List<Persona> buscarPorNombre(String nombre) {
        return personaRepository.findByNombre(nombre);
    }

    public List<Persona> buscarPorCiudad(Ciudad ciudad) {
        return personaRepository.findByCiudad(ciudad);
    }

    public List<Persona> buscarPorNombreYVariasCiudades(String nombre, List<String> ciudades) {
        List<Persona> personas = new ArrayList<Persona>();
        for (String ciudad : ciudades) {
            Ciudad ciudad1 = ciudadRepository.findByNombre(ciudad);
            List<Persona> resultados = personaRepository.findByNombreAndCiudad(nombre, ciudad1);
            personas.addAll(resultados);
        }
        return personas;
    }

    public Ciudad crearCiudad(CiudadRequestDto ciudad) {
        Optional<Ciudad> existente = ciudadRepository.findByNombreAndProvinciaAndPais(ciudad.getNombre(), ciudad.getProvincia(), ciudad.getPais());
        if (existente.isPresent()) {
            throw new DuplicateDataException("La ciudad ya existe: " + ciudad.getNombre() + ", " +
                    ciudad.getProvincia() + " - " + ciudad.getPais());
        }
        logger.info("Creando ciudad: {} {} {}", ciudad.getNombre(), ciudad.getProvincia(), ciudad.getPais());
        Ciudad nuevCiudad = new Ciudad(ciudad.getNombre(), ciudad.getProvincia(), ciudad.getPais());
        return ciudadRepository.save(nuevCiudad);
    }

    public List<Ciudad> listarCiudades() {
        return ciudadRepository.findAll();
    }

    public Empresa crearEmpresa(EmpresaRequestDto empresa) {
        if (empresaRepository.existsByNombre(empresa.getRazonSocial())) {
            throw new DuplicateDataException("Ya existe una empresa con ese nombre.");
        }

        // Verificar existencia de ciudad (por ID o nombre)
        Ciudad ciudadEntrada = empresa.getCiudad();
        Ciudad ciudadAsignada = null;


        if (ciudadEntrada.getId() != null && ciudadEntrada.getId() > 0) {
            ciudadAsignada = ciudadRepository.findById(ciudadEntrada.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ciudad no encontrada con ID: " + ciudadEntrada.getId()));
        } else if (ciudadEntrada.getNombre() != null && !ciudadEntrada.getNombre().isBlank()) {
            ciudadAsignada = ciudadRepository.findByNombre(ciudadEntrada.getNombre());
            if (ciudadAsignada == null) {
                throw new ResourceNotFoundException("Ciudad no encontrada con nombre: " + ciudadEntrada.getNombre());
            }
        } else {
            throw new InvalidDataException("La ciudad debe tener al menos un ID o nombre válido.");
        }

        empresa.setCiudad(ciudadAsignada);

        logger.info("Creando empresa: {} {}", empresa.getRazonSocial(), empresa.getCuit());
        Empresa nuevaEmpresa = new Empresa(empresa.getRazonSocial(), empresa.getCuit(), empresa.getCiudad());
        return empresaRepository.save(nuevaEmpresa);
    }


    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    public Empresa agregarContactoAEmpresa(Long empresaId, Long personaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con ID: " + empresaId));

        Optional<Persona> persona = personaRepository.findById(personaId);
        if (persona.isEmpty()) {
            throw new InvalidDataException("El contacto debe ser agregado previamente a la agenda");
        }
        empresa.getContactos().add(persona.get());

        return empresaRepository.save(empresa);
    }

}
