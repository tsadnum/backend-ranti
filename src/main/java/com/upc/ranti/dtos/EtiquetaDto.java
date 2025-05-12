package com.upc.ranti.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class EtiquetaDto implements Serializable {
    Long etiquetaId;
    String nombre;
    List<ArticuloDto> articulos;
}