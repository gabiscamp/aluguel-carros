package com.aluguelcarros.dto;

import com.aluguelcarros.model.enums.TipoUsuario;

public class AuthResponse {
    private String token;
    private Long id;
    private String email;
    private TipoUsuario tipo;

    public AuthResponse() {}

    public AuthResponse(String token, Long id, String email, TipoUsuario tipo) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.tipo = tipo;
    }

    public String getToken() { return this.token; }
    public void setToken(String token) { this.token = token; }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public TipoUsuario getTipo() { return this.tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
}
