package com.upc.ranti.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }


}