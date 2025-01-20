package br.com.SistemaOS.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.SistemaOS.modelo.Cliente;

public class CentroClientesDAO {
    private Connection con;

    public CentroClientesDAO() {
        this.con = new ModuloDeConexao().conector();
        if (this.con == null) {
            throw new IllegalStateException("Erro ao conectar ao banco de dados.");
        }
    }

    // Método para buscar todos os clientes
    public List<Cliente> getClientes() {
        String sql = "SELECT * FROM clientes";
        List<Cliente> listaClientes = new ArrayList<>();

        try (PreparedStatement pstm = con.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("idCliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setFone(rs.getString("fone"));
                cliente.setEmail(rs.getString("email"));
                listaClientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaClientes;
    }
    
    
    public void criarCliente(String nome, String endereco, String telefone, String email) {
        // Substitui valores nulos por "Não informado"
        endereco = (endereco == null || endereco.trim().isEmpty()) ? "Não informado" : endereco;
        email = (email == null || email.trim().isEmpty()) ? "Não informado" : email;

        // SQL com todas as colunas
        String sql = "INSERT INTO clientes (nome, endereco, fone, email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            // Configura os parâmetros
            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, telefone);
            stmt.setString(4, email);

            // Executa o comando
            stmt.executeUpdate();
            System.out.println("Cliente criado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
