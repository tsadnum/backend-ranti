package com.upc.ranti.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;

    @Column(name = "username", nullable = false, length = 24, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false, length = 40)
    private String nombre;

    @Column(nullable = false, length = 40)
    private String apellido;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(unique = true, nullable = false, length = 9)
    private String telefono;

    @Column(unique = true, nullable = false, length = 8)
    private String dni;

    @Column(unique = true, nullable = false, length = 250)
    private String correo;

    @Column(nullable = false, length = 40)
    private String ciudad;

    @Column(nullable = false, length = 40)
    private String pais;

    @Column(name = "estado", nullable = false, length = 24)
    private String estado;


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Articulo> articulos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @JsonManagedReference("user_roles")
    private Set<Rol> roles = new LinkedHashSet<>();

    public boolean isEnabled() {
        return "activo".equals(estado);
    }

    public void agregarRolPredeterminado(Rol rol) {
        if (roles == null) {
            roles = new LinkedHashSet<>();
        }
        roles.add(rol);
    }
}
