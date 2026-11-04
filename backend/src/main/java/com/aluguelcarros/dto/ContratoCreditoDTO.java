package com.aluguelcarros.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ContratoCreditoDTO {
    private Long id;

    @NotNull(message = "Taxa de juros e obrigatoria")
    private BigDecimal taxaJuros;

    @NotNull(message = "Valor financiado e obrigatorio")
    private BigDecimal valorFinanciado;

    @NotNull(message = "Numero de parcelas e obrigatorio")
    private Integer numeroParcelas;

    private Long contratoId;

    @NotNull(message = "ID do agente banco e obrigatorio")
    private Long agenteBancoId;

    public ContratoCreditoDTO() {}

    public ContratoCreditoDTO(Long id, BigDecimal taxaJuros, BigDecimal valorFinanciado, Integer numeroParcelas, Long contratoId, Long agenteBancoId) {
        this.id = id;
        this.taxaJuros = taxaJuros;
        this.valorFinanciado = valorFinanciado;
        this.numeroParcelas = numeroParcelas;
        this.contratoId = contratoId;
        this.agenteBancoId = agenteBancoId;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getTaxaJuros() { return this.taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }

    public BigDecimal getValorFinanciado() { return this.valorFinanciado; }
    public void setValorFinanciado(BigDecimal valorFinanciado) { this.valorFinanciado = valorFinanciado; }

    public Integer getNumeroParcelas() { return this.numeroParcelas; }
    public void setNumeroParcelas(Integer numeroParcelas) { this.numeroParcelas = numeroParcelas; }

    public Long getContratoId() { return this.contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }

    public Long getAgenteBancoId() { return this.agenteBancoId; }
    public void setAgenteBancoId(Long agenteBancoId) { this.agenteBancoId = agenteBancoId; }
}
