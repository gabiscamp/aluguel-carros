# Diagrama de Casos de Uso (v2 - Revisado) - Sistema de Aluguel de Carros

> **Revisão:** Sem alterações significativas em relação à v1. O diagrama de casos de uso se manteve estável durante o desenvolvimento, pois os requisitos funcionais foram bem definidos desde o início da modelagem.

## Alterações em relação à v1

| Alteração | Motivo |
|-----------|--------|
| Adicionado UC08 para ambos os atores | Na implementação, tanto Cliente quanto Agente podem listar automóveis (Cliente apenas visualiza, Agente também gerencia) |
| Nenhuma outra alteração | Os casos de uso se mantiveram conforme a especificação inicial |

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
    C --- UC08
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
