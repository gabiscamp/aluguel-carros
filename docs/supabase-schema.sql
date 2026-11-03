-- ==========================================
-- Schema do Banco de Dados - Supabase (PostgreSQL)
-- Sistema de Aluguel de Carros
-- ==========================================

-- Tabela de Usuários (classe base)
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('CLIENTE', 'AGENTE')),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT PRIMARY KEY REFERENCES usuarios(id) ON DELETE CASCADE,
    rg VARCHAR(20),
    cpf VARCHAR(14) UNIQUE,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(500),
    profissao VARCHAR(255)
);

-- Tabela de Empregadores (máximo 3 por cliente)
CREATE TABLE IF NOT EXISTS empregadores (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL REFERENCES clientes(id) ON DELETE CASCADE,
    nome_empresa VARCHAR(255) NOT NULL,
    cargo VARCHAR(255),
    rendimento DECIMAL(12, 2) NOT NULL
);

-- Tabela de Agentes (Empresas e Bancos)
CREATE TABLE IF NOT EXISTS agentes (
    id BIGINT PRIMARY KEY REFERENCES usuarios(id) ON DELETE CASCADE,
    razao_social VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18) UNIQUE,
    tipo_agente VARCHAR(20) NOT NULL CHECK (tipo_agente IN ('EMPRESA', 'BANCO'))
);

-- Tabela de Automóveis
CREATE TABLE IF NOT EXISTS automoveis (
    id BIGSERIAL PRIMARY KEY,
    matricula VARCHAR(50) NOT NULL UNIQUE,
    ano INTEGER NOT NULL,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    placa VARCHAR(10) NOT NULL UNIQUE,
    disponivel BOOLEAN DEFAULT TRUE
);

-- Tabela de Pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL REFERENCES clientes(id),
    automovel_id BIGINT NOT NULL REFERENCES automoveis(id),
    agente_id BIGINT REFERENCES agentes(id),
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    valor_total DECIMAL(12, 2),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE' CHECK (status IN ('PENDENTE', 'EM_ANALISE', 'APROVADO', 'REJEITADO', 'CANCELADO')),
    parecer_financeiro TEXT
);

-- Tabela de Contratos
CREATE TABLE IF NOT EXISTS contratos (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL UNIQUE REFERENCES pedidos(id),
    automovel_id BIGINT NOT NULL REFERENCES automoveis(id),
    data_contrato TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_contrato VARCHAR(20) NOT NULL CHECK (tipo_contrato IN ('CLIENTE', 'EMPRESA', 'BANCO')),
    valor_mensal DECIMAL(12, 2) NOT NULL,
    prazo_meses INTEGER NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

-- Tabela de Contratos de Crédito
CREATE TABLE IF NOT EXISTS contratos_credito (
    id BIGSERIAL PRIMARY KEY,
    contrato_id BIGINT NOT NULL UNIQUE REFERENCES contratos(id),
    banco_id BIGINT NOT NULL REFERENCES agentes(id),
    taxa_juros DECIMAL(5, 2) NOT NULL,
    valor_financiado DECIMAL(12, 2) NOT NULL,
    numero_parcelas INTEGER NOT NULL
);
