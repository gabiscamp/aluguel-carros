package com.aluguelcarros.dto;

import java.math.BigDecimal;
import java.util.List;

public class ClienteDTO {
    private Long id;
    private String email;
    private String rg;
    private String cpf;
    private String nome;
    private String endereco;
    private String profissao;
    private List<EmpregadorDTO> empregadores;

    public ClienteDTO() {}

    public ClienteDTO(Long id, String email, String rg, String cpf, String nome, String endereco, String profissao, List<EmpregadorDTO> empregadores) {
        this.id = id;
        this.email = email;
        this.rg = rg;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
        this.empregadores = empregadores;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

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

    public List<EmpregadorDTO> getEmpregadores() { return this.empregadores; }
    public void setEmpregadores(List<EmpregadorDTO> empregadores) { this.empregadores = empregadores; }

    public static class EmpregadorDTO {
        private Long id;
        private String nomeEmpresa;
        private String cargo;
        private BigDecimal rendimento;

        public EmpregadorDTO() {}

        public EmpregadorDTO(Long id, String nomeEmpresa, String cargo, BigDecimal rendimento) {
            this.id = id;
            this.nomeEmpresa = nomeEmpresa;
            this.cargo = cargo;
            this.rendimento = rendimento;
        }

        public Long getId() { return this.id; }
        public void setId(Long id) { this.id = id; }

        public String getNomeEmpresa() { return this.nomeEmpresa; }
        public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }

        public String getCargo() { return this.cargo; }
        public void setCargo(String cargo) { this.cargo = cargo; }

        public BigDecimal getRendimento() { return this.rendimento; }
        public void setRendimento(BigDecimal rendimento) { this.rendimento = rendimento; }
    }
}
