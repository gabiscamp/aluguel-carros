# Histórias de Usuário - Sistema de Aluguel de Carros

## 1. Autenticação

### HU-01: Cadastro de Usuário
**Como** visitante do sistema,  
**Quero** me cadastrar com email e senha,  
**Para que** eu possa acessar as funcionalidades do sistema.

**Critérios de aceitação:**
- O sistema deve exigir email válido e senha com mínimo de 6 caracteres
- O sistema deve impedir cadastro com email já existente
- Após o cadastro, o usuário deve ser redirecionado para a tela de login
- O tipo de usuário (Cliente ou Agente) deve ser selecionado no cadastro

---

### HU-02: Login
**Como** usuário cadastrado,  
**Quero** realizar login com meu email e senha,  
**Para que** eu possa utilizar o sistema.

**Critérios de aceitação:**
- O sistema deve validar as credenciais do usuário
- Após login bem-sucedido, o usuário é redirecionado ao dashboard
- Em caso de credenciais inválidas, exibir mensagem de erro
- A sessão deve ser mantida via token JWT

---

### HU-03: Logout
**Como** usuário autenticado,  
**Quero** sair do sistema,  
**Para que** minha sessão seja encerrada com segurança.

**Critérios de aceitação:**
- O token JWT deve ser invalidado
- O usuário deve ser redirecionado para a tela de login

---

## 2. Gestão de Perfil do Cliente

### HU-04: Completar Perfil de Cliente
**Como** cliente,  
**Quero** preencher meus dados pessoais (RG, CPF, Nome, Endereço, Profissão),  
**Para que** meu cadastro esteja completo e eu possa realizar pedidos de aluguel.

**Critérios de aceitação:**
- Todos os campos de identificação são obrigatórios (RG, CPF, Nome, Endereço)
- CPF deve ser validado (formato e dígitos verificadores)
- Os dados devem ser persistidos no banco de dados

---

### HU-05: Cadastrar Empregadores e Rendimentos
**Como** cliente,  
**Quero** cadastrar minhas entidades empregadoras e respectivos rendimentos,  
**Para que** minha análise financeira possa ser feita pelo agente.

**Critérios de aceitação:**
- O cliente pode cadastrar no máximo 3 empregadores
- Cada empregador deve ter: nome da empresa, cargo e rendimento
- O rendimento deve ser um valor numérico positivo

---

### HU-06: Editar Perfil
**Como** cliente,  
**Quero** editar meus dados pessoais e empregadores,  
**Para que** minhas informações estejam sempre atualizadas.

**Critérios de aceitação:**
- Todos os campos editáveis devem vir preenchidos com os valores atuais
- CPF não pode ser alterado após o primeiro cadastro
- As alterações devem ser salvas no banco de dados

---

### HU-07: Excluir Conta
**Como** cliente,  
**Quero** excluir minha conta do sistema,  
**Para que** meus dados sejam removidos.

**Critérios de aceitação:**
- O sistema deve solicitar confirmação antes da exclusão
- Pedidos em andamento devem ser cancelados automaticamente
- Todos os dados do cliente devem ser removidos

---

## 3. Gestão de Automóveis

### HU-08: Cadastrar Automóvel
**Como** agente,  
**Quero** cadastrar um automóvel no sistema,  
**Para que** ele fique disponível para aluguel.

**Critérios de aceitação:**
- Os campos obrigatórios são: matrícula, ano, marca, modelo e placa
- A placa deve seguir o formato válido (Mercosul ou antigo)
- Não deve ser possível cadastrar dois automóveis com a mesma placa

---

### HU-09: Listar Automóveis
**Como** usuário autenticado,  
**Quero** visualizar a lista de automóveis disponíveis,  
**Para que** eu possa escolher qual alugar.

**Critérios de aceitação:**
- A lista deve mostrar marca, modelo, ano e placa
- Deve ser possível filtrar por disponibilidade
- Apenas automóveis disponíveis aparecem para o cliente

---

### HU-10: Editar Automóvel
**Como** agente,  
**Quero** editar os dados de um automóvel,  
**Para que** as informações estejam atualizadas.

**Critérios de aceitação:**
- Todos os campos devem vir preenchidos com os valores atuais
- A placa não pode ser duplicada

---

### HU-11: Remover Automóvel
**Como** agente,  
**Quero** remover um automóvel do sistema,  
**Para que** ele não fique mais disponível para aluguel.

**Critérios de aceitação:**
- O sistema deve solicitar confirmação
- Automóveis com pedidos em andamento não podem ser removidos

---

## 4. Gestão de Pedidos

### HU-12: Criar Pedido de Aluguel
**Como** cliente,  
**Quero** criar um pedido de aluguel para um automóvel,  
**Para que** eu possa solicitar o aluguel.

**Critérios de aceitação:**
- O cliente deve selecionar o automóvel, data de início e data de fim
- O sistema deve calcular o valor total com base no período
- O pedido é criado com status "PENDENTE"
- O cliente deve ter o perfil completo para criar pedidos

---

### HU-13: Consultar Pedidos
**Como** usuário autenticado,  
**Quero** consultar meus pedidos de aluguel,  
**Para que** eu possa acompanhar o status de cada um.

**Critérios de aceitação:**
- Clientes veem apenas seus próprios pedidos
- Agentes veem todos os pedidos para avaliação
- A lista exibe automóvel, período, valor e status

---

### HU-14: Modificar Pedido
**Como** cliente,  
**Quero** modificar um pedido de aluguel,  
**Para que** eu possa ajustar datas ou trocar o automóvel.

**Critérios de aceitação:**
- Apenas pedidos com status "PENDENTE" podem ser modificados
- O valor total é recalculado após a modificação

---

### HU-15: Cancelar Pedido
**Como** cliente,  
**Quero** cancelar um pedido de aluguel,  
**Para que** eu desista da solicitação.

**Critérios de aceitação:**
- Apenas pedidos com status "PENDENTE" ou "EM_ANALISE" podem ser cancelados
- O status do pedido muda para "CANCELADO"
- O automóvel volta a ficar disponível

---

### HU-16: Avaliar Pedido (Análise Financeira)
**Como** agente,  
**Quero** avaliar financeiramente um pedido de aluguel,  
**Para que** eu possa decidir se o cliente tem condições de arcar com o aluguel.

**Critérios de aceitação:**
- O agente deve visualizar os dados do cliente e seus rendimentos
- O agente registra um parecer financeiro (texto)
- O status do pedido muda para "EM_ANALISE"

---

### HU-17: Aprovar ou Rejeitar Pedido
**Como** agente,  
**Quero** aprovar ou rejeitar um pedido após a análise financeira,  
**Para que** o contrato possa ser gerado ou o pedido encerrado.

**Critérios de aceitação:**
- Apenas pedidos "EM_ANALISE" podem ser aprovados/rejeitados
- Aprovação muda o status para "APROVADO" e gera o contrato
- Rejeição muda o status para "REJEITADO"

---

## 5. Gestão de Contratos

### HU-18: Gerar Contrato de Aluguel
**Como** agente,  
**Quero** que um contrato seja gerado ao aprovar um pedido,  
**Para que** o aluguel seja formalizado.

**Critérios de aceitação:**
- O contrato é gerado automaticamente na aprovação do pedido
- O tipo do contrato define a propriedade do automóvel (CLIENTE, EMPRESA ou BANCO)
- O contrato registra valor mensal e prazo em meses

---

### HU-19: Consultar Contratos
**Como** usuário autenticado,  
**Quero** consultar meus contratos ativos,  
**Para que** eu possa acompanhar meus aluguéis.

**Critérios de aceitação:**
- Clientes veem seus próprios contratos
- Agentes veem todos os contratos
- A lista exibe automóvel, tipo, valor e status (ativo/inativo)

---

### HU-20: Associar Contrato de Crédito
**Como** agente bancário,  
**Quero** associar um contrato de crédito a um contrato de aluguel,  
**Para que** o cliente possa financiar o aluguel.

**Critérios de aceitação:**
- Apenas agentes do tipo BANCO podem criar contratos de crédito
- O contrato de crédito registra taxa de juros, valor financiado e número de parcelas
- Um contrato de aluguel pode ter no máximo um contrato de crédito associado
