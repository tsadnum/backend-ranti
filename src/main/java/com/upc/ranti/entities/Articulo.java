package com.upc.ranti.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articuloId;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private boolean publico;

    @Column(nullable = false)
    private Boolean inapropiado = false;

    @ColumnDefault("'disponible'")
    @Column(nullable = false, length = 24)
    private String estado = "disponible";

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "id_post")
    private Post post;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL)
    private List<Imagen> imagenes;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "articulo_etiqueta",
            joinColumns = @JoinColumn(name = "articulo_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private List<Etiqueta> etiquetas;
}
