package com.aluguelcarros.dto;

import com.aluguelcarros.model.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PedidoDTO {
    private Long id;
    private LocalDateTime dataPedido;

    @NotNull(message = "Data de inicio e obrigatoria")
    private LocalDate dataInicio;

    @NotNull(message = "Data de fim e obrigatoria")
    private LocalDate dataFim;

    private BigDecimal valorTotal;
    private StatusPedido status;
    private String parecerFinanceiro;

    @NotNull(message = "ID do automovel e obrigatorio")
    private Long automovelId;

    private Long clienteId;
    private Long agenteId;
    private String clienteNome;
    private String automovelDescricao;

    public PedidoDTO() {}

    public PedidoDTO(Long id, LocalDateTime dataPedido, LocalDate dataInicio, LocalDate dataFim, BigDecimal valorTotal, StatusPedido status, String parecerFinanceiro, Long automovelId, Long clienteId, Long agenteId, String clienteNome, String automovelDescricao) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.status = status;
        this.parecerFinanceiro = parecerFinanceiro;
        this.automovelId = automovelId;
        this.clienteId = clienteId;
        this.agenteId = agenteId;
        this.clienteNome = clienteNome;
        this.automovelDescricao = automovelDescricao;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataPedido() { return this.dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }

    public LocalDate getDataInicio() { return this.dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return this.dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public BigDecimal getValorTotal() { return this.valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public StatusPedido getStatus() { return this.status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public String getParecerFinanceiro() { return this.parecerFinanceiro; }
    public void setParecerFinanceiro(String parecerFinanceiro) { this.parecerFinanceiro = parecerFinanceiro; }

    public Long getAutomovelId() { return this.automovelId; }
    public void setAutomovelId(Long automovelId) { this.automovelId = automovelId; }

    public Long getClienteId() { return this.clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getAgenteId() { return this.agenteId; }
    public void setAgenteId(Long agenteId) { this.agenteId = agenteId; }

    public String getClienteNome() { return this.clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }

    public String getAutomovelDescricao() { return this.automovelDescricao; }
    public void setAutomovelDescricao(String automovelDescricao) { this.automovelDescricao = automovelDescricao; }
}
