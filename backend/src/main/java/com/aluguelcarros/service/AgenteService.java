package com.aluguelcarros.service;

import com.aluguelcarros.dto.AgenteDTO;
import com.aluguelcarros.model.Agente;
import com.aluguelcarros.model.enums.TipoAgente;
import com.aluguelcarros.repository.AgenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgenteService {

    private final AgenteRepository agenteRepository;

    public AgenteService(AgenteRepository agenteRepository) {
        this.agenteRepository = agenteRepository;
    }

    public List<AgenteDTO> listarTodos() {
        return agenteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AgenteDTO buscarPorId(Long id) {
        Agente agente = agenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agente nao encontrado"));
        return toDTO(agente);
    }

    public List<AgenteDTO> listarPorTipo(TipoAgente tipo) {
        return agenteRepository.findByTipoAgente(tipo).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AgenteDTO atualizar(Long id, AgenteDTO dto) {
        Agente agente = agenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agente nao encontrado"));

        if (dto.getRazaoSocial() != null) agente.setRazaoSocial(dto.getRazaoSocial());
        if (dto.getTipoAgente() != null) agente.setTipoAgente(dto.getTipoAgente());

        agente = agenteRepository.save(agente);
        return toDTO(agente);
    }

    public void deletar(Long id) {
        if (!agenteRepository.existsById(id)) {
            throw new RuntimeException("Agente nao encontrado");
        }
        agenteRepository.deleteById(id);
    }

    private AgenteDTO toDTO(Agente agente) {
        return new AgenteDTO(agente.getId(), agente.getEmail(), agente.getRazaoSocial(), agente.getCnpj(), agente.getTipoAgente());
    }
}
