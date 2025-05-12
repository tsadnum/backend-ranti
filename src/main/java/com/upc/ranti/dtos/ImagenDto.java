package com.upc.ranti.dtos;

import lombok.*;

import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor


public class ImagenDto implements Serializable {
    Long imagenId;
    String url;
    String descripcion;
    ArticuloDto articulo;
}