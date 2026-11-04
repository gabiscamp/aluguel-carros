package com.aluguelcarros.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "empregadores")
public class Empregador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_empresa", nullable = false)
    private String nomeEmpresa;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    private BigDecimal rendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public Empregador() {}

    public Empregador(Long id, String nomeEmpresa, String cargo, BigDecimal rendimento, Cliente cliente) {
        this.id = id;
        this.nomeEmpresa = nomeEmpresa;
        this.cargo = cargo;
        this.rendimento = rendimento;
        this.cliente = cliente;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeEmpresa() { return this.nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }

    public String getCargo() { return this.cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public BigDecimal getRendimento() { return this.rendimento; }
    public void setRendimento(BigDecimal rendimento) { this.rendimento = rendimento; }

    public Cliente getCliente() { return this.cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
