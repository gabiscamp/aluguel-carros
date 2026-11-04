package com.aluguelcarros.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    @Column(nullable = false)
    private String rg;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    private String profissao;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Empregador> empregadores = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {}

    public Cliente(String rg, String cpf, String nome, String endereco, String profissao, List<Empregador> empregadores, List<Pedido> pedidos) {
        this.rg = rg;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
        this.empregadores = empregadores;
        this.pedidos = pedidos;
    }

    public String getRg() { return this.rg; }
    public void setRg(String rg) { this.rg = rg; }

    public String getCpf() { return this.cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return this.endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getProfissao() { return this.profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }

    public List<Empregador> getEmpregadores() { return this.empregadores; }
    public void setEmpregadores(List<Empregador> empregadores) { this.empregadores = empregadores; }

    public List<Pedido> getPedidos() { return this.pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
}
