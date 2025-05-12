package com.upc.ranti.repositories;

import com.upc.ranti.dtos.TotalArticulosPorCiudadDto;
import com.upc.ranti.entities.SolicitudArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudArticuloRepositorio extends JpaRepository<SolicitudArticulo, Long> {
    List<SolicitudArticulo> findSolicitudArticulosByUsuarioSolicitante_UsuarioId(Long usuarioSolicitanteUsuarioId);
    List<SolicitudArticulo> findSolicitudArticulosByUsuarioSolicitado_UsuarioId(Long usuarioSolicitadoUsuarioId);
    List<SolicitudArticulo> findSolicitudArticulosByUsuarioSolicitado_UsuarioIdAndArticuloOfrecido_ArticuloId(Long usuarioSolicitadoId, Long articuloOfrecidoId);
    List<SolicitudArticulo> findSolicitudArticulosByUsuarioSolicitante_UsuarioIdAndArticuloSolicitado_ArticuloId(Long usuarioSolicitanteUsuarioId, Long articuloSolicitadoArticuloId);

    @Query("SELECT new com.upc.ranti.dtos.TotalArticulosPorCiudadDto(u.ciudad, COUNT(sa.solicitudId)) " +
            "FROM SolicitudArticulo sa " +
            "JOIN sa.usuarioSolicitante u " +
            "GROUP BY u.ciudad")
    List<TotalArticulosPorCiudadDto> obtenerTotalArticulosSolicitadosPorCiudad();



}
