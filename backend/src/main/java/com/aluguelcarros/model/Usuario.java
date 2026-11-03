package com.aluguelcarros.model;

import com.aluguelcarros.model.enums.TipoUsuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    public Usuario() {}

    public Usuario(Long id, String email, String senha, TipoUsuario tipo, LocalDateTime dataCriacao) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.dataCriacao = dataCriacao;
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return this.senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public TipoUsuario getTipo() { return this.tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }

    public LocalDateTime getDataCriacao() { return this.dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
