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

public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valoracionId;
    @Column(nullable = false)
    private double Calificacion;
    private String comentario;
    @Column(nullable = false)
    private LocalDateTime fechaHoraValoracion;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitud_id", nullable = false)
    private SolicitudArticulo solicitudArticulo; // Relación con la solicitud (intercambio) evaluada

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_evaluador_id")
    private Usuario usuarioEvaluador; // Usuario que realiza la valoración

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_evaluado_id", nullable = false)
    private Usuario usuarioEvaluado;

}
