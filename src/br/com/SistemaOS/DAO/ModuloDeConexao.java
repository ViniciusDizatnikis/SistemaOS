/*
 * MIT License
 * 
 * Copyright (c) 2025 Vinicius Dizatnikis
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package br.com.SistemaOS.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por estabelecer a conexão com o banco de dados do sistema.
 * <p>
 * Configura o driver JDBC e gerencia a conexão com um banco MySQL.  
 * Certifique-se de que os parâmetros de configuração estão corretos e que o 
 * driver está incluído no classpath do projeto.
 * </p>
 *
 * @author Vinicius Dizatnikis
 * @version 1.1
 */
public class ModuloDeConexao {

    // URL de conexão com o banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/sistemaOS?characterEncoding=utf-8";
    private static final String USER = "dba";
    private static final String PASSWORD = "SistemaOS@1234";

    /**
     * Construtor Nulo
     */
    public ModuloDeConexao() {
	}
    
    /**
     * Estabelece a conexão com o banco de dados.
     *
     * @return Objeto Connection se a conexão for bem-sucedida, ou {@code null} caso contrário.
     */
    public static Connection conector() {
        Connection con = null;
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Estabelece a conexão com os parâmetros especificados
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return con;
    }
}
