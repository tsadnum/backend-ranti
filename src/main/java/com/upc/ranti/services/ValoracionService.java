package com.upc.ranti.services;

import com.upc.ranti.dtos.UsuarioArticuloValoracionDto;
import com.upc.ranti.dtos.ValoracionDto;
import com.upc.ranti.entities.SolicitudArticulo;
import com.upc.ranti.entities.Valoracion;
import com.upc.ranti.interfaces.IValoracionService;
import com.upc.ranti.repositories.SolicitudArticuloRepositorio;
import com.upc.ranti.repositories.ValoracionRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValoracionService implements IValoracionService {
    @Autowired
    private ValoracionRepositorio valoracionRepositorio;
    @Autowired
    private SolicitudArticuloRepositorio solicitudArticuloRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ValoracionDto grabarValoracion(ValoracionDto valoracionDto) {
        Valoracion valoracion = modelMapper.map(valoracionDto, Valoracion.class);

        SolicitudArticulo solicitud = solicitudArticuloRepositorio.findById(valoracionDto.getSolicitudArticulo().getSolicitudId())
                .orElseThrow(() -> new IllegalStateException("La solicitud no existe"));

        if (solicitud.getEstado() == null) {
            throw new IllegalStateException("El estado de la solicitud es nulo.");
        }

        if (!solicitud.getEstado().equals("Completada") && !solicitud.getEstado().equals("completada")) {
            throw new IllegalStateException("El intercambio no ha sido completado.");
        }

        valoracion.setSolicitudArticulo(solicitud);

        Valoracion valoracionGuardada = valoracionRepositorio.save(valoracion);

        return modelMapper.map(valoracionGuardada, ValoracionDto.class);
    }

    @Override
    @Transactional
    public List<ValoracionDto> findValoracionsByUsuarioEvaluado_UsuarioId(Long usuarioId) {
        List<Valoracion> valoraciones = valoracionRepositorio.findValoracionsByUsuarioEvaluado_UsuarioId(usuarioId);

        return valoraciones.stream()
                .map(valoracion -> modelMapper.map(valoracion, ValoracionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ValoracionDto> findValoracionsByUsuarioEvaluado_UsuarioIdOrderByFechaHoraValoracion(Long usuarioId) {
        List<Valoracion> valoraciones = valoracionRepositorio.findValoracionsByUsuarioEvaluado_UsuarioIdOrderByFechaHoraValoracion(usuarioId);

        return valoraciones.stream()
                .map(valoracion -> modelMapper.map(valoracion, ValoracionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UsuarioArticuloValoracionDto> obtenerUsuarioConArticulosValoracionMinima() {
        return valoracionRepositorio.obtenerUsuarioConArticulosValoracionMinima();
    }

    @Override
    @Transactional
    public List<UsuarioArticuloValoracionDto> obtenerUsuarioConArticulosValoracionMaxima() {
        return valoracionRepositorio.obtenerUsuarioConArticulosValoracionMaxima();
    }

}
