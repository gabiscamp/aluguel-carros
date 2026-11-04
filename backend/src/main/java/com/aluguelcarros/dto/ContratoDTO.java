package com.aluguelcarros.dto;

import com.aluguelcarros.model.enums.TipoContrato;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContratoDTO {
    private Long id;
    private LocalDateTime dataContrato;
    private TipoContrato tipoContrato;
    private BigDecimal valorMensal;
    private Integer prazoMeses;
    private Boolean ativo;
    private Long pedidoId;
    private ContratoCreditoDTO contratoCredito;

    public ContratoDTO() {}

    public ContratoDTO(Long id, LocalDateTime dataContrato, TipoContrato tipoContrato, BigDecimal valorMensal, Integer prazoMeses, Boolean ativo, Long pedidoId, ContratoCreditoDTO contratoCredito) {
        this.id = id;
        this.dataContrato = dataContrato;
        this.tipoContrato = tipoContrato;
        this.valorMensal = valorMensal;
        this.prazoMeses = prazoMeses;
        this.ativo = ativo;
        this.pedidoId = pedidoId;
        this.contratoCredito = contratoCredito;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataContrato() { return this.dataContrato; }
    public void setDataContrato(LocalDateTime dataContrato) { this.dataContrato = dataContrato; }

    public TipoContrato getTipoContrato() { return this.tipoContrato; }
    public void setTipoContrato(TipoContrato tipoContrato) { this.tipoContrato = tipoContrato; }

    public BigDecimal getValorMensal() { return this.valorMensal; }
    public void setValorMensal(BigDecimal valorMensal) { this.valorMensal = valorMensal; }

    public Integer getPrazoMeses() { return this.prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }

    public Boolean getAtivo() { return this.ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Long getPedidoId() { return this.pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public ContratoCreditoDTO getContratoCredito() { return this.contratoCredito; }
    public void setContratoCredito(ContratoCreditoDTO contratoCredito) { this.contratoCredito = contratoCredito; }
}
