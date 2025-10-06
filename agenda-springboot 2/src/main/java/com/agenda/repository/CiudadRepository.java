package com.agenda.repository;

import com.agenda.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    Ciudad findByNombre(String nombre);

    List<Ciudad> findByProvincia(String provincia);

    List<Ciudad> findByPais(String pais);

    Optional<Ciudad> findByNombreAndProvinciaAndPais(String nombre, String provincia, String pais);

}
