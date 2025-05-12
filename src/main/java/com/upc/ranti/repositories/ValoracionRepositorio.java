package com.upc.ranti.repositories;

import com.upc.ranti.dtos.UsuarioArticuloValoracionDto;
import com.upc.ranti.entities.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValoracionRepositorio extends JpaRepository<Valoracion, Long> {
    List<Valoracion> findValoracionsByUsuarioEvaluado_UsuarioId(Long usuarioId);
    @Query("SELECT v FROM Valoracion v WHERE v.usuarioEvaluado.usuarioId = :usuarioId ORDER BY v.fechaHoraValoracion DESC")
    List<Valoracion> findValoracionsByUsuarioEvaluado_UsuarioIdOrderByFechaHoraValoracion(@Param("usuarioId") Long usuarioId);


    @Query("SELECT new com.upc.ranti.dtos.UsuarioArticuloValoracionDto(" +
            "v.usuarioEvaluado.usuarioId, v.usuarioEvaluado.nombre, " +
            "v.solicitudArticulo.articuloSolicitado.nombre, v.Calificacion) " +
            "FROM Valoracion v " +
            "WHERE v.Calificacion = (SELECT MIN(v2.Calificacion) FROM Valoracion v2)")
    List<UsuarioArticuloValoracionDto> obtenerUsuarioConArticulosValoracionMinima();

    @Query("SELECT new com.upc.ranti.dtos.UsuarioArticuloValoracionDto(" +
            "v.usuarioEvaluado.usuarioId, v.usuarioEvaluado.nombre, " +
            "v.solicitudArticulo.articuloSolicitado.nombre, v.Calificacion) " +
            "FROM Valoracion v " +
            "WHERE v.Calificacion = (SELECT MAX(v2.Calificacion) FROM Valoracion v2)")
    List<UsuarioArticuloValoracionDto> obtenerUsuarioConArticulosValoracionMaxima();

}
