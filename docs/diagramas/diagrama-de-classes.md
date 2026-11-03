# Diagrama de Classes - Sistema de Aluguel de Carros

```mermaid
classDiagram
    direction TB

    class Usuario {
        -Long id
        -String email
        -String senha
        -TipoUsuario tipo
        -LocalDateTime dataCriacao
        +autenticar(email, senha) Boolean
    }

    class Cliente {
        -Long id
        -String rg
        -String cpf
        -String nome
        -String endereco
        -String profissao
        +criarPedido(pedido) Pedido
        +cancelarPedido(pedidoId) void
    }

    class Agente {
        -Long id
        -String razaoSocial
        -String cnpj
        -TipoAgente tipoAgente
        +avaliarPedido(pedidoId) void
        +aprovarPedido(pedidoId) void
        +rejeitarPedido(pedidoId) void
    }

    class Empregador {
        -Long id
        -String nomeEmpresa
        -String cargo
        -BigDecimal rendimento
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
        +modificar() void
        +cancelar() void
    }

    class Contrato {
        -Long id
        -LocalDateTime dataContrato
        -TipoContrato tipoContrato
        -BigDecimal valorMensal
        -Integer prazoMeses
        -Boolean ativo
    }

    class ContratoCredito {
        -Long id
        -BigDecimal taxaJuros
        -BigDecimal valorFinanciado
        -Integer numeroParcelas
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

    Usuario <|-- Cliente : herda
    Usuario <|-- Agente : herda
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

## Decisões de Projeto

| Decisão | Justificativa |
|---------|--------------|
| `Usuario` como classe base com herança para `Cliente` e `Agente` | Evita duplicação de atributos de autenticação (email, senha). Usa estratégia JOINED no JPA. |
| `Empregador` como composição (0..3) do `Cliente` | O PDF especifica máximo 3 entidades empregadoras com rendimentos. Composição porque empregador não existe sem cliente. |
| `ContratoCredito` como classe separada | Nem todo contrato tem crédito. Apenas agentes do tipo BANCO podem conceder. Relação opcional (0..1). |
| `TipoContrato` (CLIENTE/EMPRESA/BANCO) | O PDF diz que automóveis podem ser propriedade de clientes, empresas ou bancos dependendo do tipo de contrato. |
| `StatusPedido` como enum com 5 estados | Fluxo completo: PENDENTE → EM_ANALISE → APROVADO/REJEITADO, com possibilidade de CANCELADO pelo cliente. |
