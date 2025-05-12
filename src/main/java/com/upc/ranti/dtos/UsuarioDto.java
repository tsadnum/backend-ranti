package com.upc.ranti.dtos;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto implements Serializable {
    Long usuarioId;
    String username;
    String password;
    String nombre;
    String apellido;
    LocalDate fechaNacimiento;
    String telefono;
    String dni;
    String correo;
    String ciudad;
    String pais;
    String estado;
    Set<RolDto> roles;
}