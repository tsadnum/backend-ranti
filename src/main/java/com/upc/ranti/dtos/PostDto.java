package com.upc.ranti.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class PostDto implements Serializable {
    private Long postId;
    private String titulo;
    private String descripcion;
    private LocalDate fechaPublicacion;
    private String estado;
    UsuarioDto usuario;
}