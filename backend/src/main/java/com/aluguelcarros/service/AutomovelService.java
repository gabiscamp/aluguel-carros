package com.aluguelcarros.service;

import com.aluguelcarros.dto.AutomovelDTO;
import com.aluguelcarros.model.Automovel;
import com.aluguelcarros.repository.AutomovelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutomovelService {

    private final AutomovelRepository automovelRepository;

    public AutomovelService(AutomovelRepository automovelRepository) {
        this.automovelRepository = automovelRepository;
    }

    public List<AutomovelDTO> listarTodos() {
        return automovelRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AutomovelDTO> listarDisponiveis() {
        return automovelRepository.findByDisponivelTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AutomovelDTO buscarPorId(Long id) {
        Automovel automovel = automovelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Automovel nao encontrado"));
        return toDTO(automovel);
    }

    @Transactional
    public AutomovelDTO criar(AutomovelDTO dto) {
        if (automovelRepository.findByPlaca(dto.getPlaca()).isPresent()) {
            throw new RuntimeException("Ja existe um automovel com esta placa");
        }
        if (automovelRepository.findByMatricula(dto.getMatricula()).isPresent()) {
            throw new RuntimeException("Ja existe um automovel com esta matricula");
        }

        Automovel automovel = new Automovel();
        automovel.setMatricula(dto.getMatricula());
        automovel.setAno(dto.getAno());
        automovel.setMarca(dto.getMarca());
        automovel.setModelo(dto.getModelo());
        automovel.setPlaca(dto.getPlaca());
        automovel.setDisponivel(dto.getDisponivel() != null ? dto.getDisponivel() : true);

        automovel = automovelRepository.save(automovel);
        return toDTO(automovel);
    }

    @Transactional
    public AutomovelDTO atualizar(Long id, AutomovelDTO dto) {
        Automovel automovel = automovelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Automovel nao encontrado"));

        if (dto.getMatricula() != null) automovel.setMatricula(dto.getMatricula());
        if (dto.getAno() != null) automovel.setAno(dto.getAno());
        if (dto.getMarca() != null) automovel.setMarca(dto.getMarca());
        if (dto.getModelo() != null) automovel.setModelo(dto.getModelo());
        if (dto.getPlaca() != null) automovel.setPlaca(dto.getPlaca());
        if (dto.getDisponivel() != null) automovel.setDisponivel(dto.getDisponivel());

        automovel = automovelRepository.save(automovel);
        return toDTO(automovel);
    }

    public void deletar(Long id) {
        if (!automovelRepository.existsById(id)) {
            throw new RuntimeException("Automovel nao encontrado");
        }
        automovelRepository.deleteById(id);
    }

    private AutomovelDTO toDTO(Automovel a) {
        return new AutomovelDTO(a.getId(), a.getMatricula(), a.getAno(), a.getMarca(), a.getModelo(), a.getPlaca(), a.getDisponivel());
    }
}
