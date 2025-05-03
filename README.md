# Sistema de Ordem de Serviço

Este projeto foi criado para praticar conceitos básicos de Java com JFrame e SQL, sem o uso de frameworks avançados. Ele tem como foco o desenvolvimento de um sistema de gestão de ordens de serviço, usuários e clientes, com interface gráfica simples e banco de dados MySQL.

## Funcionalidades

### CRUD de Usuários e Clientes

### Validações para segurança e consistência

### Geração de Relatórios com Ireport

### Formatação de números e preços

### Interface ajustada para melhor usabilidade

## Tecnologias Utilizadas

- **Java** (JDK 23)
- **JFrame** (GUI)
- **MySQL**(banco de dados)
- **Ireport-5.6.0** (relátorios)

## Avisos
Apague e coloque novamente os drivers da pasta driver para que funcione corretamente!

## Requisitos e Configuração:
- Java JDK 23 e java 8 ou inferior (caso queira exportar o projeto)
- WindowBuilder no Eclipse
- Ireport (com Java 7 configurado caso queira editar)
- My SQL

# Instruções:

## JDK 23

Para executar o projeto na sua IDE, você precisa usar o JDK 23. Caso não tenha, você pode baixá-lo por este link:

[JDK23](https://www.oracle.com/java/technologies/downloads/#jdk23-windows)

## Windowbuilder

Verifique se você possui o WindowBuilder. Caso não tenha, siga estes passos:

1. **NO ECLIPSE**: No painel principal, vá em "Help -> Install New Software".
2. Clique em "Work with" e cole o seguinte link:
   
    ```Link
    WindowBuilder Latest Nightly - https://download.eclipse.org/windowbuilder/lastgoodbuild/
    ```
    
4. Selecione o WindowBuilder e clique em "Finish".

## Ireport
   
Caso precise do Ireport, você pode baixá-lo por aqui: [Ireport](https://sourceforge.net/projects/erpbarcode/files/JasperSoft/iReport-5.6.0-windows-installer.exe/download)

Para executar o Ireport, você precisa do Java 7, que pode ser baixado aqui: [Java 7](https://www.oracle.com/java/technologies/javase/javase7-archive-downloads.html)

  Para executar o Ireport com o Java 7, abra o bloco de notas como administrador e vá no local de instalação do Ireport: iReport-5.6.0\etc\ireport.conf e abra o arquivo. Na parte:

  #default location of JDK/JRE, can be overridden by using --jdkhome <dir> switch
  #jdkhome="/path/to/jdk"
  
  Logo abaixo, adicione a linha:
  
  jdkhome="C:\Program Files\Java\jdk1.7.0_80"

# Importante!

Para que sua IDE abra execute o modo de Impressão você deve adicionar 2 argumentos na hora de executar:

1. vá em window->Preference->Installed JREs
   
3. selecione o jdk e clique em "Edit"
   
5. Adicione os seguintes Argumentos em Default VM arguments:
   
   ```Argumentos
     --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED
   ```
   
7. Clique em "Finish"
   
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

   ![local De Executar](Imagens/IniciarSystema.png)

5. Se seu banco de dados estiver configurado corretamente aparecerá esta tela:

   ![Tela de Login](Imagens/telaLogin.png)
   
use essas credenciais para logar:
- **Login**: "admin"
- **Senha**: "admin"

6. Ele abrirá a tela principal do sitema.
7. 
![Tela Principal](Imagens/telaPrincipal.png)  
