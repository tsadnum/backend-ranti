package com.upc.ranti.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class SolicitudArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long solicitudId;
    @Column(nullable = false, length = 24)
    private String estado; // Valores: "Pendiente", "Aceptada", "Rechazada", "Completada", "Cancelada"
    @Column(nullable = false)
    private LocalDateTime fechaHoraSolicitud;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_solicitado_id")
    private Usuario usuarioSolicitado; // El usuario que tiene el artículo y lo ofrece

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_solicitante_id")
    private Usuario usuarioSolicitante; // El usuario que solicita el artículo

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "articulo_solicitado_id")
    private Articulo articuloSolicitado; // El artículo que el solicitante quiere

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "articulo_ofrecido_id")
    private Articulo articuloOfrecido; // El artículo que el solicitante ofrece a cambio

}
