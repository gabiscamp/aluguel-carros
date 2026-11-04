# Diagrama de Classes (v2 - Revisado) - Sistema de Aluguel de Carros

> **Revisão:** Este diagrama foi atualizado após a fase de implementação para refletir as decisões técnicas tomadas durante o desenvolvimento do sistema.

## Alterações em relação à v1

| Alteração | Motivo |
|-----------|--------|
| Adicionado `@PrePersist` em `Usuario.dataCriacao` | A data de criação é preenchida automaticamente pelo JPA ao persistir, não pelo usuário |
| Removido método `autenticar()` de `Usuario` | A autenticação é feita pelo `AuthService` com BCrypt + JWT, não pela entidade |
| Adicionados métodos de negócio nos Services | A lógica de negócio foi movida para a camada Service (MVC), não fica nas entidades |
| `Contrato.valorMensal` calculado automaticamente | Na aprovação, o valor mensal é calculado com base no `valorTotal` do pedido e `prazoMeses` |
| Adicionado `Pedido.automovelDescricao` e `clienteNome` como campos derivados | Usados nos DTOs para exibição no frontend sem precisar de joins extras |
| Estratégia de herança `JOINED` confirmada | Cada subclasse (Cliente, Agente) tem sua própria tabela no banco, referenciando `usuarios` |

```mermaid
classDiagram
    direction TB

    class Usuario {
        -Long id
        -String email
        -String senha
        -TipoUsuario tipo
        -LocalDateTime dataCriacao
        +onCreate() void
    }

    class Cliente {
        -String rg
        -String cpf
        -String nome
        -String endereco
        -String profissao
        -List~Empregador~ empregadores
    }

    class Agente {
        -String razaoSocial
        -String cnpj
        -TipoAgente tipoAgente
    }

    class Empregador {
        -Long id
        -String nomeEmpresa
        -String cargo
        -BigDecimal rendimento
        -Cliente cliente
    }

    class Automovel {
        -Long id
        -String matricula
        -Integer ano
        -String marca
        -String modelo
        -String placa
        -Boolean disponivel
    }

    class Pedido {
        -Long id
        -LocalDateTime dataPedido
        -LocalDate dataInicio
        -LocalDate dataFim
        -BigDecimal valorTotal
        -StatusPedido status
        -String parecerFinanceiro
        -Cliente cliente
        -Automovel automovel
        -Agente agente
    }

    class Contrato {
        -Long id
        -LocalDateTime dataContrato
        -TipoContrato tipoContrato
        -BigDecimal valorMensal
        -Integer prazoMeses
        -Boolean ativo
        -Pedido pedido
        -Automovel automovel
        -ContratoCredito contratoCredito
    }

    class ContratoCredito {
        -Long id
        -BigDecimal taxaJuros
        -BigDecimal valorFinanciado
        -Integer numeroParcelas
        -Contrato contrato
        -Agente agenteBanco
    }

    class TipoUsuario {
        <<enumeration>>
        CLIENTE
        AGENTE
    }

    class TipoAgente {
        <<enumeration>>
        EMPRESA
        BANCO
    }

    class StatusPedido {
        <<enumeration>>
        PENDENTE
        EM_ANALISE
        APROVADO
        REJEITADO
        CANCELADO
    }

    class TipoContrato {
        <<enumeration>>
        CLIENTE
        EMPRESA
        BANCO
    }

    Usuario <|-- Cliente : herda (JOINED)
    Usuario <|-- Agente : herda (JOINED)
    Usuario --> TipoUsuario

    Cliente "1" *-- "0..3" Empregador : possui
    Cliente "1" --> "0..*" Pedido : realiza

    Agente --> TipoAgente

    Pedido "0..*" --> "1" Automovel : refere-se a
    Pedido "1" --> "0..1" Contrato : gera
    Pedido "0..*" --> "0..1" Agente : avaliado por
    Pedido --> StatusPedido

    Contrato "1" --> "0..1" ContratoCredito : pode ter
    Contrato --> TipoContrato
    Contrato --> Automovel : registra propriedade

    ContratoCredito "0..*" --> "1" Agente : concedido por banco
```
