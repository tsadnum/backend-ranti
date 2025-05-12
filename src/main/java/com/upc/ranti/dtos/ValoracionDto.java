package com.upc.ranti.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ValoracionDto implements Serializable {
    Long valoracionId;
    int Calificacion;
    String comentario;
    LocalDateTime fechaHoraValoracion;
    SolicitudArticuloDto solicitudArticulo;
    UsuarioDto usuarioEvaluador;
    UsuarioDto usuarioEvaluado;
}