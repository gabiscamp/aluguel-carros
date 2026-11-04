package com.aluguelcarros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AutomovelDTO {
    private Long id;

    @NotBlank(message = "Matricula e obrigatoria")
    private String matricula;

    @NotNull(message = "Ano e obrigatorio")
    private Integer ano;

    @NotBlank(message = "Marca e obrigatoria")
    private String marca;

    @NotBlank(message = "Modelo e obrigatorio")
    private String modelo;

    @NotBlank(message = "Placa e obrigatoria")
    private String placa;

    private Boolean disponivel;

    public AutomovelDTO() {}

    public AutomovelDTO(Long id, String matricula, Integer ano, String marca, String modelo, String placa, Boolean disponivel) {
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
