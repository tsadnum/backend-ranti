package com.upc.ranti.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SolicitudArticuloDto implements Serializable {
    Long solicitudId;
    String estado;
    LocalDateTime fechaHoraSolicitud;
    UsuarioDto usuarioSolicitado;
    UsuarioDto usuarioSolicitante;
    ArticuloDto articuloSolicitado;
    ArticuloDto articuloOfrecido;
}