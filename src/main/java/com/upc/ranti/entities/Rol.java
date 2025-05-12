package com.upc.ranti.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(name = "roles_ak_1", columnNames = {"nombre"})
})
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 32)
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    @JsonBackReference("user_roles")
    private Set<Usuario> usuarios = new LinkedHashSet<>();
}