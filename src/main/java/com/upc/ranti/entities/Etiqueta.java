package com.upc.ranti.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long etiquetaId;

    @Column(nullable = false, length = 50)
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "etiquetas")
    private List<Articulo> articulos;
}
