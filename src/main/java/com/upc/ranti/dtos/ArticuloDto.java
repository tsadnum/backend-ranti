package com.upc.ranti.dtos;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloDto implements Serializable {
    Long articuloId;
    String nombre;
    String descripcion;
    boolean publico;
    String estado;
    PostDto post;
    UsuarioDto usuario;
    private Boolean inapropiado;
    private List<String> etiquetas;
}
