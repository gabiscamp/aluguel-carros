package com.aluguelcarros.model;

import com.aluguelcarros.model.enums.TipoAgente;
import jakarta.persistence.*;

@Entity
@Table(name = "agentes")
public class Agente extends Usuario {

    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_agente", nullable = false)
    private TipoAgente tipoAgente;

    public Agente() {}

    public Agente(String razaoSocial, String cnpj, TipoAgente tipoAgente) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.tipoAgente = tipoAgente;
    }

    public String getRazaoSocial() { return this.razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getCnpj() { return this.cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public TipoAgente getTipoAgente() { return this.tipoAgente; }
    public void setTipoAgente(TipoAgente tipoAgente) { this.tipoAgente = tipoAgente; }
}
