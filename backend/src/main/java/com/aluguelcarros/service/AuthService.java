package com.aluguelcarros.service;

import com.aluguelcarros.config.JwtService;
import com.aluguelcarros.dto.AuthResponse;
import com.aluguelcarros.dto.LoginRequest;
import com.aluguelcarros.dto.RegisterRequest;
import com.aluguelcarros.model.Agente;
import com.aluguelcarros.model.Cliente;
import com.aluguelcarros.model.Usuario;
import com.aluguelcarros.model.enums.TipoUsuario;
import com.aluguelcarros.repository.AgenteRepository;
import com.aluguelcarros.repository.ClienteRepository;
import com.aluguelcarros.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final AgenteRepository agenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository,
                       AgenteRepository agenteRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.agenteRepository = agenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email ja cadastrado");
        }

        Usuario usuario;

        if (request.getTipo() == TipoUsuario.CLIENTE) {
            if (request.getCpf() == null || request.getNome() == null || request.getRg() == null || request.getEndereco() == null) {
                throw new RuntimeException("Dados de cliente incompletos: rg, cpf, nome e endereco sao obrigatorios");
            }
            if (clienteRepository.existsByCpf(request.getCpf())) {
                throw new RuntimeException("CPF ja cadastrado");
            }
            Cliente cliente = new Cliente();
            cliente.setEmail(request.getEmail());
            cliente.setSenha(passwordEncoder.encode(request.getSenha()));
            cliente.setTipo(TipoUsuario.CLIENTE);
            cliente.setRg(request.getRg());
            cliente.setCpf(request.getCpf());
            cliente.setNome(request.getNome());
            cliente.setEndereco(request.getEndereco());
            cliente.setProfissao(request.getProfissao());
            usuario = clienteRepository.save(cliente);
        } else if (request.getTipo() == TipoUsuario.AGENTE) {
            if (request.getRazaoSocial() == null || request.getCnpj() == null || request.getTipoAgente() == null) {
                throw new RuntimeException("Dados de agente incompletos: razaoSocial, cnpj e tipoAgente sao obrigatorios");
            }
            if (agenteRepository.existsByCnpj(request.getCnpj())) {
                throw new RuntimeException("CNPJ ja cadastrado");
            }
            Agente agente = new Agente();
            agente.setEmail(request.getEmail());
            agente.setSenha(passwordEncoder.encode(request.getSenha()));
            agente.setTipo(TipoUsuario.AGENTE);
            agente.setRazaoSocial(request.getRazaoSocial());
            agente.setCnpj(request.getCnpj());
            agente.setTipoAgente(request.getTipoAgente());
            usuario = agenteRepository.save(agente);
        } else {
            throw new RuntimeException("Tipo de usuario invalido");
        }

        String token = jwtService.generateToken(usuario.getEmail(), usuario.getTipo().name());

        return new AuthResponse(token, usuario.getId(), usuario.getEmail(), usuario.getTipo());
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais invalidas"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Credenciais invalidas");
        }

        String token = jwtService.generateToken(usuario.getEmail(), usuario.getTipo().name());

        return new AuthResponse(token, usuario.getId(), usuario.getEmail(), usuario.getTipo());
    }
}
