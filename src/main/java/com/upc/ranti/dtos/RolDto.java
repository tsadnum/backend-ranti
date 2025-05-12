package com.upc.ranti.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RolDto {
    private Long id;
    @NotNull
    @Size(max = 32)
    private String nombre;
}