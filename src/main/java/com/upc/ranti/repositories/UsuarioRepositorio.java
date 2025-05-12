package com.upc.ranti.repositories;

import com.upc.ranti.dtos.UsuariosReportDto;
import com.upc.ranti.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);

    @Query("SELECT new com.upc.ranti.dtos.UsuariosReportDto(u.nombre, u.ciudad, " +
            "(SELECT AVG(v.Calificacion) FROM Valoracion v WHERE v.usuarioEvaluado = u)) " +
            "FROM Usuario u")
    List<UsuariosReportDto> obtenerUsuariosConPromedioCalifacion();


}
