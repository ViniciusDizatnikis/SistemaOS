/**
 * Classe CentroUsuariosDAO
 * Esta classe é responsável por realizar operações no banco de dados
 * relacionadas aos usuários do sistema, como criar, atualizar, deletar e consultar dados.
 */
package br.com.SistemaOS.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.SistemaOS.modelo.Usuario;

/**
 * Classe Responsável pelo CRUD Dos Usuário
 */
public class CentroUsuariosDAO {
	/**
	 * Conexão Com Banco de Dados
	 */
    private Connection con;

    /**
     * Construtor que inicializa a conexão com o banco de dados.
     */
    public CentroUsuariosDAO() {
        this.con = ModuloDeConexao.conector(); 
    }

    /**
     * Retorna o status da conexão com o banco de dados.
     * 
     * @return {@code true} se a conexão estiver ativa, {@code false} caso contrário.
     */
    public Boolean getStatusConnection() {
        return con != null;
    }

    /**
     * Cria um novo usuário no banco de dados.
     * 
     * @param name  Nome do usuário.
     * @param fone  Telefone do usuário.
     * @param login Login do usuário.
     * @param senha Senha do usuário.
     * @param perfil Perfil do usuário.
     * @return {@code true} se a inserção foi bem-sucedida, {@code false} caso contrário.
     */
    public boolean criarUsuario(String name, String fone, String login, String senha, String perfil) {
        String sql = "INSERT INTO usuarios (usuario, fone, login, senha, perfil) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, fone);
            pst.setString(3, login);
            pst.setString(4, senha);
            pst.setString(5, perfil);

            int rowsInserted = pst.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Realiza o login de um usuário.
     * 
     * @param nome  Login do usuário.
     * @param senha Senha do usuário.
     * @return Um objeto {@link Usuario} se as credenciais forem válidas, {@code null} caso contrário.
     */
    public Usuario login(String nome, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login=? AND senha=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, nome);
            pst.setString(2, senha);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Usuario user = new Usuario();
                    user.setId(rs.getInt("iduser"));
                    user.setNome(rs.getString("usuario"));
                    user.setFone(rs.getString("fone"));
                    user.setLogin(rs.getString("login"));
                    user.setSenha(rs.getString("senha"));
                    user.setPerfil(rs.getString("perfil"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtém a lista de todos os usuários cadastrados.
     * 
     * @return Uma lista de objetos {@link Usuario}.
     */
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("iduser"));
                usuario.setNome(rs.getString("usuario"));
                usuario.setFone(rs.getString("fone"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPerfil(rs.getString("perfil"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    /**
     * Exclui um usuário do banco de dados pelo ID.
     * 
     * @param id ID do usuário a ser excluído.
     */
    public void deleteUser(int id) {
        String sqlDeleteRelated = "DELETE FROM tbos WHERE iduser = ?";
        String sqlDeleteUser = "DELETE FROM usuarios WHERE iduser = ?";

        try {
            con.setAutoCommit(false);

            try (PreparedStatement stmtRelated = con.prepareStatement(sqlDeleteRelated)) {
                stmtRelated.setInt(1, id);
                stmtRelated.executeUpdate();
            }

            try (PreparedStatement stmtUser = con.prepareStatement(sqlDeleteUser)) {
                stmtUser.setInt(1, id);
                int rowsAffected = stmtUser.executeUpdate();

                if (!(rowsAffected > 0)) {
                    System.out.println("Nenhum usuário encontrado com o ID " + id + ".");
                } 
            }

            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
                System.out.println("Erro ao deletar o usuário com ID " + id + ". Operação desfeita.");
            } catch (SQLException rollbackEx) {
                System.err.println("Erro ao realizar o rollback: " + rollbackEx.getMessage());
            }
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Verifica se um login já existe no banco de dados.
     * 
     * @param object Login a ser verificado.
     * @return {@code true} se o login existir, {@code false} caso contrário.
     * @throws IllegalArgumentException Se o parâmetro de login for nulo ou vazio.
     */
    public boolean loginExistente(Object object) {
        String login = object != null ? object.toString() : null;

        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("O parâmetro de login não pode ser nulo ou vazio.");
        }

        String sql = "SELECT 1 FROM usuarios WHERE login = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, login);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Atualiza os dados de um usuário.
     * 
     * @param nome   Nome do usuário.
     * @param fone   Telefone do usuário.
     * @param login  Login do usuário.
     * @param senha  Senha do usuário.
     * @param perfil Perfil do usuário.
     * @param id     ID do usuário a ser atualizado.
     */
    public void updateUser(Object nome, Object fone, Object login, Object senha, Object perfil, Integer id) {
        StringBuilder query = new StringBuilder("UPDATE usuarios SET ");
        ArrayList<Object> params = new ArrayList<>();

        if (nome != null) {
            query.append("usuario = ?, ");
            params.add(nome);
        }
        if (fone != null) {
            query.append("fone = ?, ");
            params.add(fone);
        }
        if (login != null) {
            query.append("login = ?, ");
            params.add(login);
        }
        if (senha != null) {
            query.append("senha = ?, ");
            params.add(senha);
        }
        if (perfil != null) {
            query.append("perfil = ? ");
            params.add(perfil);
        }

        if (query.toString().endsWith(", ")) {
            query.delete(query.length() - 2, query.length());
        }

        query.append("WHERE iduser = ?");
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

    /**
     * Gera um relatório de Ordens de Serviço associado a um usuário.
     * 
     * @param name Nome do técnico.
     * @return Um array bidimensional com os dados das ordens de serviço.
     */
    public Object[][] GetRelatorioOrdemEServico(String name) {
        String sql = "SELECT O.os, O.equipamento, O.defeito, O.servico, O.valor, "
                   + "C.nome AS cliente, C.fone AS contato, "
                   + "U.usuario AS tecnico_responsavel "
                   + "FROM tbos AS O "
                   + "JOIN clientes AS C ON O.idcliente = C.idcliente "
                   + "JOIN usuarios AS U ON O.iduser = U.iduser";

        Object[][] data = null;

        try (PreparedStatement pst = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = pst.executeQuery()) {

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            data = new Object[rowCount][7];

            int rowIndex = 0;
            while (rs.next()) {
                data[rowIndex][0] = rs.getInt("os");
                data[rowIndex][1] = rs.getString("equipamento");
                data[rowIndex][2] = rs.getString("defeito");
                data[rowIndex][3] = rs.getString("servico");
                data[rowIndex][4] = rs.getDouble("valor");
                data[rowIndex][5] = rs.getString("cliente");

                String tecnicoResponsavel = rs.getString("tecnico_responsavel");
                if (tecnicoResponsavel != null && tecnicoResponsavel.equals(name)) {
                    data[rowIndex][6] = "Você";
                } else {
                    data[rowIndex][6] = tecnicoResponsavel;
                }

                rowIndex++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}