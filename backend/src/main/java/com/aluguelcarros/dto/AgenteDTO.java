package com.aluguelcarros.dto;

import com.aluguelcarros.model.enums.TipoAgente;

public class AgenteDTO {
    private Long id;
    private String email;
    private String razaoSocial;
    private String cnpj;
    private TipoAgente tipoAgente;

    public AgenteDTO() {}

    public AgenteDTO(Long id, String email, String razaoSocial, String cnpj, TipoAgente tipoAgente) {
        this.id = id;
        this.email = email;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.tipoAgente = tipoAgente;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getRazaoSocial() { return this.razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getCnpj() { return this.cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public TipoAgente getTipoAgente() { return this.tipoAgente; }
    public void setTipoAgente(TipoAgente tipoAgente) { this.tipoAgente = tipoAgente; }
}
