package com.aluguelcarros.service;

import com.aluguelcarros.dto.ClienteDTO;
import com.aluguelcarros.model.Cliente;
import com.aluguelcarros.model.Empregador;
import com.aluguelcarros.repository.ClienteRepository;
import com.aluguelcarros.repository.EmpregadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmpregadorRepository empregadorRepository;

    public ClienteService(ClienteRepository clienteRepository, EmpregadorRepository empregadorRepository) {
        this.clienteRepository = clienteRepository;
        this.empregadorRepository = empregadorRepository;
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));
        return toDTO(cliente);
    }

    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));

        if (dto.getNome() != null) cliente.setNome(dto.getNome());
        if (dto.getEndereco() != null) cliente.setEndereco(dto.getEndereco());
        if (dto.getProfissao() != null) cliente.setProfissao(dto.getProfissao());
        if (dto.getRg() != null) cliente.setRg(dto.getRg());

        cliente = clienteRepository.save(cliente);
        return toDTO(cliente);
    }

    @Transactional
    public ClienteDTO adicionarEmpregador(Long clienteId, ClienteDTO.EmpregadorDTO empregadorDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));

        long count = empregadorRepository.countByClienteId(clienteId);
        if (count >= 3) {
            throw new RuntimeException("Cliente ja possui o maximo de 3 empregadores");
        }

        Empregador empregador = new Empregador();
        empregador.setNomeEmpresa(empregadorDTO.getNomeEmpresa());
        empregador.setCargo(empregadorDTO.getCargo());
        empregador.setRendimento(empregadorDTO.getRendimento());
        empregador.setCliente(cliente);

        empregadorRepository.save(empregador);

        return toDTO(clienteRepository.findById(clienteId).get());
    }

    @Transactional
    public void removerEmpregador(Long clienteId, Long empregadorId) {
        Empregador empregador = empregadorRepository.findById(empregadorId)
                .orElseThrow(() -> new RuntimeException("Empregador nao encontrado"));

        if (!empregador.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Empregador nao pertence a este cliente");
        }

        empregadorRepository.delete(empregador);
    }

    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente nao encontrado");
        }
        clienteRepository.deleteById(id);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        List<ClienteDTO.EmpregadorDTO> empregadoresDTO = null;
        if (cliente.getEmpregadores() != null) {
            empregadoresDTO = cliente.getEmpregadores().stream()
                    .map(e -> new ClienteDTO.EmpregadorDTO(e.getId(), e.getNomeEmpresa(), e.getCargo(), e.getRendimento()))
                    .collect(Collectors.toList());
        }

        return new ClienteDTO(cliente.getId(), cliente.getEmail(), cliente.getRg(), cliente.getCpf(), cliente.getNome(), cliente.getEndereco(), cliente.getProfissao(), empregadoresDTO);
    }
}
