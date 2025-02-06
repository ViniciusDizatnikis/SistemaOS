# Sistema de Ordem de Serviço

 Este projeto tem como objetivo aprender e praticar os conceitos básicos de **JFrame** e **SQL**. Foi desenvolvido com ferramentas básicas, sem o uso de frameworks avançados, para proporcionar uma experiência prática de como funciona o desenvolvimento de aplicações com interface gráfica e banco de dados.

 ## Objetivo

 O projeto visa aprender e praticar conceitos básicos de JFrame para criação de interfaces gráficas e SQL para interação com banco de dados, através da implementação de um Sistema de Ordem de Serviço. A ideia é explorar como essas tecnologias podem ser combinadas para desenvolver uma aplicação prática.

 - **JFrame** para criar interfaces gráficas simples.
 - **SQL** para interação com banco de dados, realizando operações como inserção, atualização, consulta e exclusão de dados.

 ## Funcionalidades

 Atualmente, o sistema oferece as seguintes funcionalidades:

- **CRUD Usuários**: Permite ao Administrador e aos usuários com a permissão "Admin" criar, editar, deletar e visualizar usuários cadastrados no sistema.  
- **CRUD Clientes**: Funcionalidade semelhante à de usuários, permitindo a gestão completa dos clientes.  
- **Validações**: O sistema possui validações robustas para evitar erros e garantir uma experiência segura e eficiente.  
- **Geração de Relatórios**: Agora é possível gerar relatórios detalhados de clientes, serviços e usuários, facilitando o controle e a gestão.  
- **Formatação de Números e Preços**: Os valores financeiros e numéricos são formatados corretamente para melhor visualização.  
- **Tela Principal Ajustada**: A interface da tela principal foi melhorada para proporcionar uma navegação mais intuitiva e eficiente.  

 ## Status do Projeto

 Atualmente, o sistema está em fase de desenvolvimento. Estou criando as funcionalidades de **Clientes** e pretendo gerar um **arquivo executável** para facilitar os testes e a execução do sistema sem a necessidade de IDE.

 ## Tecnologias Utilizadas

 - **Java** (JDK 8 ou superior, atualmente JDK 23)
 - **JFrame** para a construção da interface gráfica
 - **MySQL** para a gestão de dados."

 ## Como Executar

 1. Clone o repositório para o seu computador:
    ```bash
      git clone [https://github.com/ViniciusDizatnikis/SistemaOS.git](https://github.com/ViniciusDizatnikis/SistemaOS.git)
    ```
 2. Configure seu banco de dados com a estrutura abaixo para que funcione corretamente.

 ```sql
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
 ```
3. Configure a conexão com seu banco de dados SQL em "ModuloDeConexao.java"

   ![Conexão SQL](Imagens/configSQL.png)  


4. Abra sua IDE com o projeto e procure por "br.com.StartSystem/OpenSistemOS.java" e a execute, é por ela onde começa todo o sistema.

   ![local De Executar](Imagens/Iniciar Systema.png)

5. Se seu banco de dados estiver configurado corretamente aparecerá esta tela:

   ![Tela de Login](Imagens/telaLogin.png)
   
use essas credenciais para logar:
- **Login**: "admin"
- **Senha**: "admin"

6. Ele abrirá a tela principal do sitema. 
![Tela Principal](Imagens/telaPrincipal.png)  

## Javadoc

O projeto conta com documentação Javadoc detalhada, disponível em "Info/javadoc", que descreve todas as classes e métodos implementados, facilitando a compreensão e manutenção do código.

Este projeto ainda está Finalizado. Se encontrar algum problema, sinta-se à vontade para abrir uma issue no repositório do GitHub.
