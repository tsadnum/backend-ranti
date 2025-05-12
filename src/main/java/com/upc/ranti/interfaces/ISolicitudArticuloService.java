package com.upc.ranti.interfaces;

import com.upc.ranti.dtos.SolicitudArticuloDto;
import com.upc.ranti.dtos.TotalArticulosPorCiudadDto;


import java.util.List;

public interface ISolicitudArticuloService {
    public SolicitudArticuloDto grabarSolicitudArticulo (SolicitudArticuloDto solicitudArticuloDto);
    public List<SolicitudArticuloDto> findSolicitudArticulosByUsuarioSolicitante_UsuarioId(Long usuarioSolicitanteId);
    public List<SolicitudArticuloDto> findSolicitudArticulosByUsuarioSolicitado_UsuarioId(Long usuarioSolicitadoId);
    public List<SolicitudArticuloDto> findSolicitudArticulosByUsuarioSolicitado_UsuarioIdAndArticuloOfrecido_ArticuloId(Long usuarioSolicitadoId, Long articuloOfrecidoId);
    public List<SolicitudArticuloDto> findSolicitudArticulosByUsuarioSolicitante_UsuarioIdAndArticuloSolicitado_ArticuloId(Long usuarioSolicitanteUsuarioId, Long articuloSolicitadoArticuloId);
    public List<TotalArticulosPorCiudadDto> obtenerTotalArticulosSolicitadosPorCiudad();

    boolean decidirPropuesta(Long solicitudId, String decision);
    boolean validarUsuarioAutorizado(Long solicitudId, String username);

    void cancelarPropuesta(Long solicitudId, String username);
}
