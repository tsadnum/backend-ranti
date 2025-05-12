package com.upc.ranti.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UsuariosReportDto {
    private String nombre;
    private String ciudad;
    private double calificacionPromedio;
}
