package com.agenda.repository;

import com.agenda.entity.Ciudad;
import com.agenda.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByNombre(String nombre);

    boolean existsByTelefono(String telefono);

    List<Persona> findByCiudad(Ciudad ciudad);

    List<Persona> findByNombreAndCiudad(String nombre, Ciudad ciudad);

}
