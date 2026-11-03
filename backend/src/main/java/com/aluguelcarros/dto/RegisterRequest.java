package com.aluguelcarros.dto;

import com.aluguelcarros.model.enums.TipoAgente;
import com.aluguelcarros.model.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {

    @NotBlank(message = "Email e obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "Senha e obrigatoria")
    private String senha;

    @NotNull(message = "Tipo de usuario e obrigatorio")
    private TipoUsuario tipo;

    // Cliente fields
    private String rg;
    private String cpf;
    private String nome;
    private String endereco;
    private String profissao;

    // Agente fields
    private String razaoSocial;
    private String cnpj;
    private TipoAgente tipoAgente;

    public RegisterRequest() {}

    public RegisterRequest(String email, String senha, TipoUsuario tipo, String rg, String cpf, String nome, String endereco, String profissao, String razaoSocial, String cnpj, TipoAgente tipoAgente) {
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.rg = rg;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.tipoAgente = tipoAgente;
    }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return this.senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public TipoUsuario getTipo() { return this.tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }

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

    public String getRazaoSocial() { return this.razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getCnpj() { return this.cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public TipoAgente getTipoAgente() { return this.tipoAgente; }
    public void setTipoAgente(TipoAgente tipoAgente) { this.tipoAgente = tipoAgente; }
}
