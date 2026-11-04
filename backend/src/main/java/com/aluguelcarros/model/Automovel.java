package com.aluguelcarros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "automoveis")
public class Automovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private Boolean disponivel = true;

    public Automovel() {}

    public Automovel(Long id, String matricula, Integer ano, String marca, String modelo, String placa, Boolean disponivel) {
        this.id = id;
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.disponivel = disponivel;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getMatricula() { return this.matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public Integer getAno() { return this.ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getMarca() { return this.marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return this.modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getPlaca() { return this.placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public Boolean getDisponivel() { return this.disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }
}
