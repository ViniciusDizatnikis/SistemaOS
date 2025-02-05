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


    public void updateCliente(Object nome, Object endereco, Object fone, Object email, Integer id) {
        StringBuilder query = new StringBuilder("UPDATE clientes SET ");
        ArrayList<Object> params = new ArrayList<>();

        if (nome != null) {
            query.append("nome = ?, ");
            params.add(nome);
        }
        if (endereco != null) {
            query.append("endereco = ?, ");
            params.add(endereco);
        }
        if (fone != null) {
            query.append("fone = ?, ");
            params.add(fone);
        }
        if (email != null) {
            query.append("email = ? ");
            params.add(email);
        }

        if (query.toString().endsWith(", ")) {
            query.delete(query.length() - 2, query.length());
        }

        query.append("WHERE idCliente = ?");
        params.add(id);

        try (PreparedStatement stmt = con.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i).toString());
            }
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
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
    
    public void deletarCliente(int id) {
        String sql = "DELETE FROM tbos WHERE idcliente = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar ordem de serviço: " + e.getMessage());
            e.printStackTrace();
        }

        sql = "DELETE FROM clientes WHERE idcliente = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Cliente pesquisarClientePorId(int id) {
        String sql = "SELECT * FROM clientes WHERE idCliente = ?";
        Cliente cliente = null;

        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, id);  
            ResultSet rs = pstm.executeQuery(); 

            if (rs.next()) {  
                cliente = new Cliente();
                cliente.setId(rs.getInt("idCliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setFone(rs.getString("fone"));
                cliente.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente; 
    }


    
    
    public void criarCliente(String nome, String endereco, String telefone, String email) {
        endereco = (endereco == null || endereco.trim().isEmpty()) ? "Não informado" : endereco;
        email = (email == null || email.trim().isEmpty()) ? "Não informado" : email;
        
        String sql = "INSERT INTO clientes (nome, endereco, fone, email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, telefone);
            stmt.setString(4, email);


            stmt.executeUpdate();
            System.out.println("Cliente criado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
