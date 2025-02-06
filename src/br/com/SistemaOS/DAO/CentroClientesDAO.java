package br.com.SistemaOS.DAO;

import br.com.SistemaOS.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelo gerenciamento de dados relacionados aos clientes no banco de dados.
 * Inclui operações de criação, leitura, atualização e exclusão (CRUD).
 * 
 * @author Vinicius Dizatnikis
 * @version 1.1
 */
public class CentroClientesDAO {
    private final Connection con;

    /**
     * Construtor da classe que estabelece a conexão com o banco de dados.
     * Lança uma exceção caso a conexão falhe.
     */
    public CentroClientesDAO() {
        this.con = new ModuloDeConexao().conector();
        if (this.con == null) {
            throw new IllegalStateException("Erro ao conectar ao banco de dados.");
        }
    }

    /**
     * Verifica se a conexão com o banco de dados está ativa.
     * 
     * @return {@code true} se a conexão estiver ativa, {@code false} caso contrário.
     */
    public boolean getStatusConnection() {
        return con != null;
    }

    /**
     * Atualiza os dados de um cliente com base nos valores informados.
     * Apenas os parâmetros não nulos são atualizados.
     *
     * @param nome     Nome do cliente (pode ser {@code null} para não alterar).
     * @param endereco Endereço do cliente (pode ser {@code null} para não alterar).
     * @param fone     Telefone do cliente (pode ser {@code null} para não alterar).
     * @param email    E-mail do cliente (pode ser {@code null} para não alterar).
     * @param id       ID do cliente a ser atualizado.
     */
    public void updateCliente(Object nome, Object endereco, Object fone, Object email, Integer id) {
        StringBuilder query = new StringBuilder("UPDATE clientes SET ");
        List<Object> params = new ArrayList<>();

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

        query.append(" WHERE idCliente = ?");
        params.add(id);

        try (PreparedStatement stmt = con.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i).toString());
            }

            stmt.executeUpdate();
            System.out.println("Cliente atualizado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    /**
     * Retorna uma lista com todos os clientes cadastrados.
     *
     * @return Lista contendo objetos {@link Cliente}.
     */
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
            System.err.println("Erro ao buscar clientes: " + e.getMessage());
        }

        return listaClientes;
    }

    /**
     * Deleta um cliente e suas ordens de serviço relacionadas.
     *
     * @param id ID do cliente a ser deletado.
     */
    public void deletarCliente(int id) {
        String sqlOS = "DELETE FROM tbos WHERE idcliente = ?";
        String sqlCliente = "DELETE FROM clientes WHERE idcliente = ?";

        try (PreparedStatement stmtOS = con.prepareStatement(sqlOS);
             PreparedStatement stmtCliente = con.prepareStatement(sqlCliente)) {

            stmtOS.setInt(1, id);
            stmtOS.executeUpdate();

            stmtCliente.setInt(1, id);
            stmtCliente.executeUpdate();

            System.out.println("Cliente e suas ordens de serviço excluídos com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca um cliente pelo seu ID.
     *
     * @param id ID do cliente a ser pesquisado.
     * @return Objeto {@link Cliente} caso encontrado, ou {@code null} caso contrário.
     */
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
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }

        return cliente;
    }

    /**
     * Cria um novo cliente no banco de dados.
     *
     * @param nome     Nome do cliente.
     * @param endereco Endereço do cliente ("Não informado" se nulo/vazio).
     * @param telefone Telefone do cliente.
     * @param email    E-mail do cliente ("Não informado" se nulo/vazio).
     */
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

        } catch (SQLException e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
        }
    }
}