package com.upc.ranti.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor


public class UsuarioArticuloValoracionDto {
    private Long usuarioId;
    private String usuarioNombre;
    private String articuloNombre;
    private double calificacion;
}
