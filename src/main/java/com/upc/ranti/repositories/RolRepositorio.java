package com.upc.ranti.repositories;

import com.upc.ranti.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
