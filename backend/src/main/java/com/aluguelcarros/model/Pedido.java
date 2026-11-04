package com.aluguelcarros.model;

import com.aluguelcarros.model.enums.StatusPedido;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = StatusPedido.PENDENTE;

    @Column(name = "parecer_financeiro")
    private String parecerFinanceiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "automovel_id", nullable = false)
    private Automovel automovel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id")
    private Agente agente;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Contrato contrato;

    public Pedido() {}

    public Pedido(Long id, LocalDateTime dataPedido, LocalDate dataInicio, LocalDate dataFim, BigDecimal valorTotal, StatusPedido status, String parecerFinanceiro, Cliente cliente, Automovel automovel, Agente agente, Contrato contrato) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.status = status;
        this.parecerFinanceiro = parecerFinanceiro;
        this.cliente = cliente;
        this.automovel = automovel;
        this.agente = agente;
        this.contrato = contrato;
    }

    @PrePersist
    protected void onCreate() {
        dataPedido = LocalDateTime.now();
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

    public Cliente getCliente() { return this.cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Automovel getAutomovel() { return this.automovel; }
    public void setAutomovel(Automovel automovel) { this.automovel = automovel; }

    public Agente getAgente() { return this.agente; }
    public void setAgente(Agente agente) { this.agente = agente; }

    public Contrato getContrato() { return this.contrato; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }
}
