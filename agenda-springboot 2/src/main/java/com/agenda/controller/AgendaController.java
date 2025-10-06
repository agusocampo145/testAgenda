package com.agenda.controller;

import com.agenda.dto.CiudadRequestDto;
import com.agenda.dto.EmpresaRequestDto;
import com.agenda.dto.PersonaRequestDto;
import com.agenda.entity.Ciudad;
import com.agenda.entity.Empresa;
import com.agenda.entity.Persona;
import com.agenda.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Proyecto Agenda - versión Spring Boot
 * Autor: Agustin Ocampo
 * <p>
 * Esta aplicación permite gestionar una agenda de personas y empresas.
 * Las funcionalidades incluyen:
 * - Alta, consulta y búsqueda de personas.
 * - Alta de empresas y asignación de contactos existentes.
 * - Búsquedas por nombre, ciudad y múltiples ciudades.
 * - Manejo centralizado de excepciones.
 * - Persistencia en base de datos H2 (en memoria).
 * - Exposición de endpoints REST para pruebas con Postman o Swagger.
 */

@RestController
@RequestMapping("/api")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    // --- PERSONAS --- //

    @Operation(summary = "Crear una persona", description = "Crea una nueva persona y la guarda en la base de datos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona creada correctamente")
    })
    @PostMapping("/personas")
    public ResponseEntity<Persona> crearPersona(@Valid @RequestBody PersonaRequestDto persona) {
        return ResponseEntity.ok(agendaService.crearPersona(persona));
    }

    @Operation(summary = "Listar todas las personas")
    @GetMapping("/personas")
    public ResponseEntity<List<Persona>> listarPersonas() {
        return ResponseEntity.ok(agendaService.obtenerTodasLasPersonas());
    }

    @Operation(summary = "Obtener persona por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendaService.obtenerPersonaPorId(id));
    }

    @Operation(summary = "Buscar personas por nombre")
    @GetMapping("/personas/buscar/nombre")
    public ResponseEntity<List<Persona>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(agendaService.buscarPorNombre(nombre));
    }

    @Operation(summary = "Buscar personas por ciudad")
    @PostMapping("/personas/buscar/ciudad")
    public ResponseEntity<List<Persona>> buscarPorCiudad(@RequestBody Ciudad ciudad) {
        return ResponseEntity.ok(agendaService.buscarPorCiudad(ciudad));
    }

    @Operation(summary = "Buscar personas por nombre en múltiples ciudades")
    @GetMapping("/personas/buscar/nombre-ciudades")
    public ResponseEntity<List<Persona>> buscarPorNombreYCiudades(
            @RequestParam String nombre,
            @RequestParam List<String> ciudades) {
        return ResponseEntity.ok(agendaService.buscarPorNombreYVariasCiudades(nombre, ciudades));
    }

    // --- CIUDADES --- //

    @Operation(summary = "Crear una ciudad")
    @PostMapping("/ciudades")
    public ResponseEntity<Ciudad> crearCiudad(@Valid @RequestBody CiudadRequestDto ciudad) {
        return ResponseEntity.ok(agendaService.crearCiudad(ciudad));
    }

    @Operation(summary = "Listar todas las ciudades")
    @GetMapping("/ciudades")
    public ResponseEntity<List<Ciudad>> listarCiudades() {
        return ResponseEntity.ok(agendaService.listarCiudades());
    }

    // --- EMPRESAS --- //

    @Operation(summary = "Crear una empresa")
    @PostMapping("/empresas")
    public ResponseEntity<Empresa> crearEmpresa(@Valid @RequestBody EmpresaRequestDto empresa) {
        return ResponseEntity.ok(agendaService.crearEmpresa(empresa));
    }

    @Operation(summary = "Listar todas las empresas")
    @GetMapping("/empresas")
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        return ResponseEntity.ok(agendaService.listarEmpresas());
    }

    @Operation(summary = "Agregar contacto a una empresa", description = "Asigna una lista de personas existentes como contactos de una empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contactos agregados correctamente"),
            @ApiResponse(responseCode = "404", description = "Empresa o persona no encontrada")
    })
    @PostMapping("/empresas/{id}/contacto/{personaId}")
    public ResponseEntity<Empresa> agregarContactoAEmpresa(
            @PathVariable Long id,
            @PathVariable Long personaId) {
        return ResponseEntity.ok(agendaService.agregarContactoAEmpresa(id, personaId));
    }
}
