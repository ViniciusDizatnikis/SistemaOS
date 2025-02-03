package br.com.SistemaOS.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ModuloDeConexao {
    public static Connection conector() {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/sistemaOS";
        String user = "root"; 
        String password = "12345678";

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return con;
    }
}
