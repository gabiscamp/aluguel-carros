package com.aluguelcarros.model;

import com.aluguelcarros.model.enums.TipoContrato;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_contrato")
    private LocalDateTime dataContrato;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contrato", nullable = false)
    private TipoContrato tipoContrato;

    @Column(name = "valor_mensal", nullable = false)
    private BigDecimal valorMensal;

    @Column(name = "prazo_meses", nullable = false)
    private Integer prazoMeses;

    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @OneToOne(mappedBy = "contrato", cascade = CascadeType.ALL)
    private ContratoCredito contratoCredito;

    public Contrato() {}

    public Contrato(Long id, LocalDateTime dataContrato, TipoContrato tipoContrato, BigDecimal valorMensal, Integer prazoMeses, Boolean ativo, Pedido pedido, ContratoCredito contratoCredito) {
        this.id = id;
        this.dataContrato = dataContrato;
        this.tipoContrato = tipoContrato;
        this.valorMensal = valorMensal;
        this.prazoMeses = prazoMeses;
        this.ativo = ativo;
        this.pedido = pedido;
        this.contratoCredito = contratoCredito;
    }

    @PrePersist
    protected void onCreate() {
        dataContrato = LocalDateTime.now();
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

    public Pedido getPedido() { return this.pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public ContratoCredito getContratoCredito() { return this.contratoCredito; }
    public void setContratoCredito(ContratoCredito contratoCredito) { this.contratoCredito = contratoCredito; }
}
