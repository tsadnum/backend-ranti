package com.upc.ranti.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetaUsoDto {
    private String nombre;
    private Long cantidad;
}
