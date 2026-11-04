# Diagrama de Casos de Uso - Sistema de Aluguel de Carros

```mermaid
flowchart LR
    subgraph Atores
        C((Cliente))
        A((Agente\nEmpresa/Banco))
    end

    subgraph Sistema de Aluguel de Carros
        subgraph Autenticação
            UC01[Realizar Cadastro]
            UC02[Realizar Login]
            UC03[Realizar Logout]
        end

        subgraph Gestão de Perfil
            UC04[Visualizar Perfil]
            UC05[Editar Perfil]
            UC06[Excluir Conta]
        end

        subgraph Gestão de Automóveis
            UC07[Cadastrar Automóvel]
            UC08[Listar Automóveis]
            UC09[Editar Automóvel]
            UC10[Remover Automóvel]
        end

        subgraph Gestão de Pedidos
            UC11[Criar Pedido de Aluguel]
            UC12[Consultar Pedidos]
            UC13[Modificar Pedido]
            UC14[Cancelar Pedido]
            UC15[Avaliar Pedido\nAnálise Financeira]
            UC16[Aprovar/Rejeitar Pedido]
        end

        subgraph Gestão de Contratos
            UC17[Gerar Contrato de Aluguel]
            UC18[Consultar Contratos]
            UC19[Associar Contrato de Crédito]
        end
    end

    C --- UC01
    C --- UC02
    C --- UC03
    C --- UC04
    C --- UC05
    C --- UC06
    C --- UC11
    C --- UC12
    C --- UC13
    C --- UC14
    C --- UC18

    A --- UC02
    A --- UC03
    A --- UC07
    A --- UC08
    A --- UC09
    A --- UC10
    A --- UC12
    A --- UC13
    A --- UC15
    A --- UC16
    A --- UC17
    A --- UC18
    A --- UC19

    UC11 -.->|include| UC02
    UC15 -.->|include| UC12
    UC16 -.->|include| UC15
    UC17 -.->|include| UC16
```

## Descrição dos Casos de Uso

| ID | Caso de Uso | Ator(es) | Descrição |
|----|------------|----------|-----------|
| UC01 | Realizar Cadastro | Cliente, Agente | Cadastro com email, senha e tipo de usuário |
| UC02 | Realizar Login | Cliente, Agente | Autenticação com email e senha (JWT) |
| UC03 | Realizar Logout | Cliente, Agente | Encerramento da sessão |
| UC04 | Visualizar Perfil | Cliente | Visualizar dados pessoais e empregadores |
| UC05 | Editar Perfil | Cliente | Alterar dados pessoais e empregadores |
| UC06 | Excluir Conta | Cliente | Remover conta e dados do sistema |
| UC07 | Cadastrar Automóvel | Agente | Registrar novo automóvel no sistema |
| UC08 | Listar Automóveis | Cliente, Agente | Visualizar automóveis disponíveis |
| UC09 | Editar Automóvel | Agente | Alterar dados de um automóvel |
| UC10 | Remover Automóvel | Agente | Remover automóvel do sistema |
| UC11 | Criar Pedido de Aluguel | Cliente | Solicitar aluguel de um automóvel |
| UC12 | Consultar Pedidos | Cliente, Agente | Visualizar pedidos e seus status |
| UC13 | Modificar Pedido | Cliente, Agente | Alterar dados de um pedido pendente |
| UC14 | Cancelar Pedido | Cliente | Cancelar um pedido pendente ou em análise |
| UC15 | Avaliar Pedido | Agente | Análise financeira do cliente |
| UC16 | Aprovar/Rejeitar Pedido | Agente | Decisão final sobre o pedido |
| UC17 | Gerar Contrato de Aluguel | Agente | Criação automática do contrato na aprovação |
| UC18 | Consultar Contratos | Cliente, Agente | Visualizar contratos ativos |
| UC19 | Associar Contrato de Crédito | Agente (Banco) | Vincular financiamento ao contrato |
