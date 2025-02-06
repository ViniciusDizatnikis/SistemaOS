-- Criar o banco de dados
CREATE DATABASE sistemaOS;
USE sistemaOS;

-- Tabela de usuários
CREATE TABLE usuarios (
    iduser INT PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(50) NOT NULL,
    fone VARCHAR(15),
    login VARCHAR(15) NOT NULL UNIQUE,
    senha VARCHAR(15) NOT NULL,
    perfil VARCHAR(20) NOT NULL 
);

-- Inserir usuários
INSERT INTO usuarios(usuario, fone, login, senha, perfil) VALUES 
('Administrador', '41 99999-9999', 'admin', 'admin', 'admin');

-- Tabela de clientes
CREATE TABLE clientes (
    idcliente INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    endereco VARCHAR(100),
    fone VARCHAR(50) NOT NULL,
    email VARCHAR(50)
);

-- Tabela de ordens de serviço
CREATE TABLE tbos (
    os INT PRIMARY KEY AUTO_INCREMENT,
    data_os TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    equipamento VARCHAR(150) NOT NULL,
    defeito VARCHAR(150) NOT NULL,
    servico VARCHAR(150),
    tecnico VARCHAR(30),
    valor DECIMAL(10, 2),
    idcliente INT NOT NULL,
    iduser INT NOT NULL,
    FOREIGN KEY (idcliente) REFERENCES clientes(idcliente),
    FOREIGN KEY (iduser) REFERENCES usuarios(iduser)
);



