package com.aluguelcarros.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "contratos_credito")
public class ContratoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "taxa_juros", nullable = false)
    private BigDecimal taxaJuros;

    @Column(name = "valor_financiado", nullable = false)
    private BigDecimal valorFinanciado;

    @Column(name = "numero_parcelas", nullable = false)
    private Integer numeroParcelas;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_banco_id", nullable = false)
    private Agente agenteBanco;

    public ContratoCredito() {}

    public ContratoCredito(Long id, BigDecimal taxaJuros, BigDecimal valorFinanciado, Integer numeroParcelas, Contrato contrato, Agente agenteBanco) {
        this.id = id;
        this.taxaJuros = taxaJuros;
        this.valorFinanciado = valorFinanciado;
        this.numeroParcelas = numeroParcelas;
        this.contrato = contrato;
        this.agenteBanco = agenteBanco;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getTaxaJuros() { return this.taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }

    public BigDecimal getValorFinanciado() { return this.valorFinanciado; }
    public void setValorFinanciado(BigDecimal valorFinanciado) { this.valorFinanciado = valorFinanciado; }

    public Integer getNumeroParcelas() { return this.numeroParcelas; }
    public void setNumeroParcelas(Integer numeroParcelas) { this.numeroParcelas = numeroParcelas; }

    public Contrato getContrato() { return this.contrato; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }

    public Agente getAgenteBanco() { return this.agenteBanco; }
    public void setAgenteBanco(Agente agenteBanco) { this.agenteBanco = agenteBanco; }
}
