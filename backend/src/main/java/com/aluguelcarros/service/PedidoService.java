package com.aluguelcarros.service;

import com.aluguelcarros.dto.PedidoDTO;
import com.aluguelcarros.model.*;
import com.aluguelcarros.model.enums.StatusPedido;
import com.aluguelcarros.model.enums.TipoContrato;
import com.aluguelcarros.model.enums.TipoUsuario;
import com.aluguelcarros.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final AutomovelRepository automovelRepository;
    private final AgenteRepository agenteRepository;
    private final ContratoRepository contratoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                         AutomovelRepository automovelRepository, AgenteRepository agenteRepository,
                         ContratoRepository contratoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.automovelRepository = automovelRepository;
        this.agenteRepository = agenteRepository;
        this.contratoRepository = contratoRepository;
    }

    @Transactional
    public PedidoDTO criar(PedidoDTO dto, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));

        Automovel automovel = automovelRepository.findById(dto.getAutomovelId())
                .orElseThrow(() -> new RuntimeException("Automovel nao encontrado"));

        if (!automovel.getDisponivel()) {
            throw new RuntimeException("Automovel nao esta disponivel");
        }

        if (dto.getDataInicio().isBefore(LocalDate.now())) {
            throw new RuntimeException("Data de inicio nao pode ser no passado");
        }

        if (dto.getDataFim().isBefore(dto.getDataInicio())) {
            throw new RuntimeException("Data de fim deve ser posterior a data de inicio");
        }

        long dias = ChronoUnit.DAYS.between(dto.getDataInicio(), dto.getDataFim());
        BigDecimal valorDiario = new BigDecimal("150.00");
        BigDecimal valorTotal = valorDiario.multiply(BigDecimal.valueOf(dias));

        Pedido pedido = new Pedido();
        pedido.setDataInicio(dto.getDataInicio());
        pedido.setDataFim(dto.getDataFim());
        pedido.setValorTotal(valorTotal);
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setCliente(cliente);
        pedido.setAutomovel(automovel);

        automovel.setDisponivel(false);
        automovelRepository.save(automovel);

        pedido = pedidoRepository.save(pedido);
        return toDTO(pedido);
    }

    @Transactional
    public PedidoDTO modificar(Long pedidoId, PedidoDTO dto, Long usuarioId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));

        if (pedido.getStatus() != StatusPedido.PENDENTE && pedido.getStatus() != StatusPedido.EM_ANALISE) {
            throw new RuntimeException("Pedido nao pode ser modificado no status atual: " + pedido.getStatus());
        }

        if (dto.getDataInicio() != null) pedido.setDataInicio(dto.getDataInicio());
        if (dto.getDataFim() != null) pedido.setDataFim(dto.getDataFim());

        if (dto.getDataInicio() != null || dto.getDataFim() != null) {
            long dias = ChronoUnit.DAYS.between(pedido.getDataInicio(), pedido.getDataFim());
            BigDecimal valorDiario = new BigDecimal("150.00");
            pedido.setValorTotal(valorDiario.multiply(BigDecimal.valueOf(dias)));
        }

        if (dto.getAutomovelId() != null && !dto.getAutomovelId().equals(pedido.getAutomovel().getId())) {
            Automovel novoAutomovel = automovelRepository.findById(dto.getAutomovelId())
                    .orElseThrow(() -> new RuntimeException("Automovel nao encontrado"));
            if (!novoAutomovel.getDisponivel()) {
                throw new RuntimeException("Novo automovel nao esta disponivel");
            }
            pedido.getAutomovel().setDisponivel(true);
            automovelRepository.save(pedido.getAutomovel());
            novoAutomovel.setDisponivel(false);
            automovelRepository.save(novoAutomovel);
            pedido.setAutomovel(novoAutomovel);
        }

        pedido = pedidoRepository.save(pedido);
        return toDTO(pedido);
    }

    @Transactional
    public PedidoDTO cancelar(Long pedidoId, Long clienteId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));

        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Pedido nao pertence a este cliente");
        }

        if (pedido.getStatus() == StatusPedido.APROVADO || pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new RuntimeException("Pedido nao pode ser cancelado no status atual: " + pedido.getStatus());
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.getAutomovel().setDisponivel(true);
        automovelRepository.save(pedido.getAutomovel());

        pedido = pedidoRepository.save(pedido);
        return toDTO(pedido);
    }

    public PedidoDTO consultar(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));
        return toDTO(pedido);
    }

    public List<PedidoDTO> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoDTO avaliar(Long pedidoId, Long agenteId, String parecerFinanceiro) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new RuntimeException("Apenas pedidos pendentes podem ser avaliados");
        }

        Agente agente = agenteRepository.findById(agenteId)
                .orElseThrow(() -> new RuntimeException("Agente nao encontrado"));

        pedido.setAgente(agente);
        pedido.setStatus(StatusPedido.EM_ANALISE);
        pedido.setParecerFinanceiro(parecerFinanceiro);

        pedido = pedidoRepository.save(pedido);
        return toDTO(pedido);
    }

    @Transactional
    public PedidoDTO aprovar(Long pedidoId, Long agenteId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));

        if (pedido.getStatus() != StatusPedido.EM_ANALISE) {
            throw new RuntimeException("Apenas pedidos em analise podem ser aprovados");
        }

        Agente agente = agenteRepository.findById(agenteId)
                .orElseThrow(() -> new RuntimeException("Agente nao encontrado"));

        pedido.setAgente(agente);
        pedido.setStatus(StatusPedido.APROVADO);
        pedido = pedidoRepository.save(pedido);

        // Generate contract on approval
        long meses = ChronoUnit.MONTHS.between(pedido.getDataInicio().withDayOfMonth(1), pedido.getDataFim().withDayOfMonth(1));
        if (meses < 1) meses = 1;

        BigDecimal valorMensal = pedido.getValorTotal().divide(BigDecimal.valueOf(meses), 2, RoundingMode.HALF_UP);

        Contrato contrato = new Contrato();
        contrato.setTipoContrato(TipoContrato.CLIENTE);
        contrato.setValorMensal(valorMensal);
        contrato.setPrazoMeses((int) meses);
        contrato.setAtivo(true);
        contrato.setPedido(pedido);
        contratoRepository.save(contrato);

        return toDTO(pedido);
    }

    @Transactional
    public PedidoDTO rejeitar(Long pedidoId, Long agenteId, String motivo) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));

        if (pedido.getStatus() != StatusPedido.EM_ANALISE) {
            throw new RuntimeException("Apenas pedidos em analise podem ser rejeitados");
        }

        pedido.setAgente(agenteRepository.findById(agenteId)
                .orElseThrow(() -> new RuntimeException("Agente nao encontrado")));
        pedido.setStatus(StatusPedido.REJEITADO);
        pedido.setParecerFinanceiro(motivo);

        pedido.getAutomovel().setDisponivel(true);
        automovelRepository.save(pedido.getAutomovel());

        pedido = pedidoRepository.save(pedido);
        return toDTO(pedido);
    }

    private PedidoDTO toDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getDataInicio(),
                pedido.getDataFim(),
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getParecerFinanceiro(),
                pedido.getAutomovel().getId(),
                pedido.getCliente().getId(),
                pedido.getAgente() != null ? pedido.getAgente().getId() : null,
                pedido.getCliente().getNome(),
                pedido.getAutomovel().getMarca() + " " + pedido.getAutomovel().getModelo() + " - " + pedido.getAutomovel().getPlaca()
        );
    }
}
