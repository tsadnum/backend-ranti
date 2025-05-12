package com.upc.ranti.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class TotalArticulosPorCiudadDto {
    private String ciudad;
    private Long totalSolicitudes;
}
