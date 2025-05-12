package com.upc.ranti.services;

import com.upc.ranti.dtos.SolicitudArticuloDto;
import com.upc.ranti.dtos.TotalArticulosPorCiudadDto;
import com.upc.ranti.entities.SolicitudArticulo;
import com.upc.ranti.interfaces.ISolicitudArticuloService;
import com.upc.ranti.repositories.SolicitudArticuloRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SolicitudArticuloService implements ISolicitudArticuloService {

    @Autowired
    private SolicitudArticuloRepositorio solicitudArticuloRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public SolicitudArticuloDto grabarSolicitudArticulo(SolicitudArticuloDto solicitudArticuloDto) {
        SolicitudArticulo solicitudArticulo = modelMapper.map(solicitudArticuloDto, SolicitudArticulo.class);
        SolicitudArticulo guardar = solicitudArticuloRepositorio.save(solicitudArticulo);
        return modelMapper.map(guardar, SolicitudArticuloDto.class);
    }

    @Override
    @Transactional
    public List<SolicitudArticuloDto> findSolicitudArticulosByUsuarioSolicitante_UsuarioId(Long usuarioSolicitanteId) {
        List<SolicitudArticulo> solicitudes = solicitudArticuloRepositorio.findSolicitudArticulosByUsuarioSolicitante_UsuarioId(usuarioSolicitanteId);

        return solicitudes.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudArticuloDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SolicitudArticuloDto> findSolicitudArticulosByUsuarioSolicitado_UsuarioId(Long usuarioSolicitadoId) {
        List<SolicitudArticulo> solicitudes = solicitudArticuloRepositorio.findSolicitudArticulosByUsuarioSolicitado_UsuarioId(usuarioSolicitadoId);

        return solicitudes.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudArticuloDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SolicitudArticuloDto> findSolicitudArticulosByUsuarioSolicitado_UsuarioIdAndArticuloOfrecido_ArticuloId(Long usuarioSolicitadoId, Long articuloOfrecidoId) {

        List<SolicitudArticulo> solicitudes = solicitudArticuloRepositorio
                .findSolicitudArticulosByUsuarioSolicitado_UsuarioIdAndArticuloOfrecido_ArticuloId(usuarioSolicitadoId, articuloOfrecidoId);

        return solicitudes.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudArticuloDto.class))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public List<SolicitudArticuloDto> findSolicitudArticulosByUsuarioSolicitante_UsuarioIdAndArticuloSolicitado_ArticuloId(Long usuarioSolicitanteUsuarioId, Long articuloSolicitadoArticuloId) {
        List<SolicitudArticulo> solicitudes = solicitudArticuloRepositorio
                .findSolicitudArticulosByUsuarioSolicitante_UsuarioIdAndArticuloSolicitado_ArticuloId(usuarioSolicitanteUsuarioId, articuloSolicitadoArticuloId);

        return solicitudes.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudArticuloDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TotalArticulosPorCiudadDto> obtenerTotalArticulosSolicitadosPorCiudad() {
        return solicitudArticuloRepositorio.obtenerTotalArticulosSolicitadosPorCiudad();
    }

    @Override
    @Transactional
    public boolean validarUsuarioAutorizado(Long solicitudId, String username) {
        SolicitudArticulo solicitud = solicitudArticuloRepositorio.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("La solicitud no existe."));

        return solicitud.getUsuarioSolicitado().getUsername().equals(username);
    }

    @Override
    @Transactional
    public boolean decidirPropuesta(Long solicitudId, String decision) {
        SolicitudArticulo solicitud = solicitudArticuloRepositorio.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("La solicitud no existe."));

        if (!"Aceptada".equalsIgnoreCase(decision) && !"Rechazada".equalsIgnoreCase(decision)) {
            throw new IllegalArgumentException("Decisión inválida. Debe ser 'Aceptada' o 'Rechazada'.");
        }

        solicitud.setEstado(decision);
        solicitudArticuloRepositorio.save(solicitud);

        return true;
    }

    @Override
    @Transactional
    public void cancelarPropuesta(Long solicitudId, String username) {
        SolicitudArticulo solicitud = solicitudArticuloRepositorio.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("La solicitud no existe."));

        if (!solicitud.getUsuarioSolicitante().getUsername().equals(username)) {
            throw new SecurityException("No tienes permiso para cancelar esta propuesta.");
        }

        if (!"Pendiente".equals(solicitud.getEstado())) {
            throw new IllegalStateException("La solicitud no puede ser cancelada, ya está en un estado de decisión.");
        }

        solicitud.setEstado("Cancelada");
        solicitudArticuloRepositorio.save(solicitud);
    }


}
