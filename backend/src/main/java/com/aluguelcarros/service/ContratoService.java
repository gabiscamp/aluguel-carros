package com.aluguelcarros.service;

import com.aluguelcarros.dto.ContratoDTO;
import com.aluguelcarros.dto.ContratoCreditoDTO;
import com.aluguelcarros.model.Agente;
import com.aluguelcarros.model.Contrato;
import com.aluguelcarros.model.ContratoCredito;
import com.aluguelcarros.model.enums.TipoAgente;
import com.aluguelcarros.repository.AgenteRepository;
import com.aluguelcarros.repository.ContratoCreditoRepository;
import com.aluguelcarros.repository.ContratoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final ContratoCreditoRepository contratoCreditoRepository;
    private final AgenteRepository agenteRepository;

    public ContratoService(ContratoRepository contratoRepository,
                           ContratoCreditoRepository contratoCreditoRepository,
                           AgenteRepository agenteRepository) {
        this.contratoRepository = contratoRepository;
        this.contratoCreditoRepository = contratoCreditoRepository;
        this.agenteRepository = agenteRepository;
    }

    public List<ContratoDTO> listarTodos() {
        return contratoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ContratoDTO> listarAtivos() {
        return contratoRepository.findByAtivoTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ContratoDTO buscarPorId(Long id) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato nao encontrado"));
        return toDTO(contrato);
    }

    public ContratoDTO buscarPorPedido(Long pedidoId) {
        Contrato contrato = contratoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RuntimeException("Contrato nao encontrado para este pedido"));
        return toDTO(contrato);
    }

    @Transactional
    public ContratoDTO atualizar(Long id, ContratoDTO dto) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato nao encontrado"));

        if (dto.getTipoContrato() != null) contrato.setTipoContrato(dto.getTipoContrato());
        if (dto.getValorMensal() != null) contrato.setValorMensal(dto.getValorMensal());
        if (dto.getPrazoMeses() != null) contrato.setPrazoMeses(dto.getPrazoMeses());
        if (dto.getAtivo() != null) contrato.setAtivo(dto.getAtivo());

        contrato = contratoRepository.save(contrato);
        return toDTO(contrato);
    }

    @Transactional
    public ContratoDTO associarCredito(Long contratoId, ContratoCreditoDTO creditoDTO) {
        Contrato contrato = contratoRepository.findById(contratoId)
                .orElseThrow(() -> new RuntimeException("Contrato nao encontrado"));

        if (contrato.getContratoCredito() != null) {
            throw new RuntimeException("Contrato ja possui um contrato de credito associado");
        }

        Agente agenteBanco = agenteRepository.findById(creditoDTO.getAgenteBancoId())
                .orElseThrow(() -> new RuntimeException("Agente banco nao encontrado"));

        if (agenteBanco.getTipoAgente() != TipoAgente.BANCO) {
            throw new RuntimeException("O agente informado nao e do tipo BANCO");
        }

        ContratoCredito credito = new ContratoCredito();
        credito.setTaxaJuros(creditoDTO.getTaxaJuros());
        credito.setValorFinanciado(creditoDTO.getValorFinanciado());
        credito.setNumeroParcelas(creditoDTO.getNumeroParcelas());
        credito.setContrato(contrato);
        credito.setAgenteBanco(agenteBanco);

        contratoCreditoRepository.save(credito);

        return toDTO(contratoRepository.findById(contratoId).get());
    }

    private ContratoDTO toDTO(Contrato contrato) {
        ContratoCreditoDTO creditoDTO = null;
        if (contrato.getContratoCredito() != null) {
            ContratoCredito c = contrato.getContratoCredito();
            creditoDTO = new ContratoCreditoDTO(c.getId(), c.getTaxaJuros(), c.getValorFinanciado(), c.getNumeroParcelas(), contrato.getId(), c.getAgenteBanco().getId());
        }

        return new ContratoDTO(contrato.getId(), contrato.getDataContrato(), contrato.getTipoContrato(), contrato.getValorMensal(), contrato.getPrazoMeses(), contrato.getAtivo(), contrato.getPedido().getId(), creditoDTO);
    }
}
