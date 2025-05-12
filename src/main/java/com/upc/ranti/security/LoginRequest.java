package com.upc.ranti.security;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

    // Constructor por defecto
    public LoginRequest() {}

    // Constructor con parámetros
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
