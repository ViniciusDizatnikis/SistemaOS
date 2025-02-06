package br.com.SistemaOS.DAO;

import br.com.SistemaOS.modelo.OrdemServico;
import br.com.SistemaOS.modelo.Usuario;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para gerenciamento de ordens de serviço. Inclui operações de
 * criação, leitura, atualização e exclusão (CRUD).
 */
public class CentroOSDAO {
	/**
	 * Conexão Com Banco de Dados
	 */
	private final Connection con;

	/**
	 * Retorna Um boolean (true) conexão funcionando (false) falha na conexão.
	 */
	public CentroOSDAO() {
		this.con = ModuloDeConexao.conector();
	}

	/**
	 * Cria uma nova ordem de serviço.
	 * 
	 * @param equipamento O nome do equipamento relacionado à ordem de serviço.
	 * @param defeito     A descrição do defeito no equipamento.
	 * @param servico     O tipo de serviço a ser realizado.
	 * @param valor       O valor do serviço.
	 * @param idCliente   O ID do cliente relacionado à ordem de serviço.
	 * @param user        O usuário responsável pela criação da ordem de serviço.
	 * @return O ID da nova ordem de serviço criada, ou {@code null} se a criação falhar.
	 */
	public Integer criarOS(String equipamento, String defeito, String servico, BigDecimal valor, Integer idCliente,
			Usuario user) {
		String sql = """
				INSERT INTO tbos (equipamento, defeito, servico, tecnico, valor, idcliente, iduser)
				VALUES (?, ?, ?, ?, ?, ?, ?)
				""";

		Integer idGerado = null;

		try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pst.setString(1, equipamento);
			pst.setString(2, defeito);
			pst.setString(3, servico);
			pst.setString(4, user.getNome());
			pst.setBigDecimal(5, valor);
			pst.setInt(6, idCliente);
			pst.setInt(7, user.getId());

			int linhasAfetadas = pst.executeUpdate();

			if (linhasAfetadas > 0) {
				try (ResultSet rs = pst.getGeneratedKeys()) {
					if (rs.next()) {
						idGerado = rs.getInt(1);
					}
				}
			}

		} catch (SQLException e) {
			System.err.println("Erro ao criar Ordem de Serviço: " + e.getMessage());
		}

		return idGerado;
	}

	/**
	 * Atualiza uma ordem de serviço existente.
	 * 
	 * @param id          O ID da ordem de serviço a ser atualizada.
	 * @param equipamento O nome do equipamento relacionado à ordem de serviço.
	 * @param defeito     A descrição do defeito no equipamento.
	 * @param servico     O tipo de serviço a ser realizado.
	 * @param valor       O valor do serviço.
	 */
	public void atualizarOS(String id, String equipamento, String defeito, String servico, String valor) {
		StringBuilder query = new StringBuilder("UPDATE tbos SET ");
		ArrayList<Object> params = new ArrayList<>();

		if (equipamento != null) {
			query.append("equipamento = ?, ");
			params.add(equipamento);
		}
		if (defeito != null) {
			query.append("defeito = ?, ");
			params.add(defeito);
		}
		if (servico != null) {
			query.append("servico = ?, ");
			params.add(servico);
		}
		if (valor != null) {
			query.append("valor = ?, ");
			params.add(valor);
		}

		if (query.toString().endsWith(", ")) {
			query.delete(query.length() - 2, query.length());
		}

		query.append(" WHERE os = ?");
		params.add(id);

		try (PreparedStatement stmt = con.prepareStatement(query.toString())) {
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}

			stmt.executeUpdate();
			System.out.println("Ordem de Serviço atualizada com sucesso.");

		} catch (SQLException e) {
			System.err.println("Erro ao atualizar Ordem de Serviço: " + e.getMessage());
		}
	}

	/**
	 * Lista todas as ordens de serviço cadastradas.
	 * 
	 * @return Uma lista de objetos {@link OrdemServico} contendo todas as ordens de serviço.
	 */
	public List<OrdemServico> listarTodasOs() {
		String sql = """
				SELECT O.os, O.data_os, O.equipamento, O.defeito, O.servico, O.valor,
				       C.nome AS cliente, C.fone AS contato, C.idcliente,
				       U.usuario AS tecnico
				FROM tbos AS O
				JOIN clientes AS C ON O.idcliente = C.idcliente
				JOIN usuarios AS U ON O.iduser = U.iduser
				""";

		List<OrdemServico> listaOS = new ArrayList<>();

		try (PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				OrdemServico ordem = new OrdemServico();
				ordem.setOs(rs.getInt("os"));
				ordem.setDataOs(rs.getString("data_os"));
				ordem.setEquipamento(rs.getString("equipamento"));
				ordem.setDefeito(rs.getString("defeito"));
				ordem.setServico(rs.getString("servico"));
				ordem.setTecnico(rs.getString("tecnico"));
				ordem.setValor(rs.getBigDecimal("valor"));
				ordem.setCliente(rs.getString("cliente"));
				ordem.setContato(rs.getString("contato"));
				ordem.setIdCliente(rs.getInt("idcliente"));

				listaOS.add(ordem);
			}

		} catch (SQLException e) {
			System.err.println("Erro ao listar Ordens de Serviço: " + e.getMessage());
		}

		return listaOS;
	}

	/**
	 * Lista as ordens de serviço associadas a um ID de usuário específico.
	 * 
	 * @param id O ID do usuário para filtrar as ordens de serviço.
	 * @return Uma lista de objetos {@link OrdemServico} associados ao ID do usuário.
	 */
	public List<OrdemServico> listarOsComId(Integer id) {
		String sql = """
				SELECT O.os, O.data_os, O.equipamento, O.defeito, O.servico, O.valor,
				       C.nome AS cliente, C.fone AS contato, C.idcliente,
				       U.usuario AS tecnico
				FROM tbos AS O
				JOIN clientes AS C ON O.idcliente = C.idcliente
				JOIN usuarios AS U ON O.iduser = U.iduser
				WHERE O.iduser = ?
				""";

		List<OrdemServico> listaOS = new ArrayList<>();

		try (PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					OrdemServico ordem = new OrdemServico();
					ordem.setOs(rs.getInt("os"));
					ordem.setDataOs(rs.getString("data_os"));
					ordem.setEquipamento(rs.getString("equipamento"));
					ordem.setDefeito(rs.getString("defeito"));
					ordem.setServico(rs.getString("servico"));
					ordem.setTecnico(rs.getString("tecnico"));
					ordem.setValor(rs.getBigDecimal("valor"));
					ordem.setCliente(rs.getString("cliente"));
					ordem.setContato(rs.getString("contato"));
					ordem.setIdCliente(rs.getInt("idcliente"));

					listaOS.add(ordem);
				}
			}

		} catch (SQLException e) {
			System.err.println("Erro ao listar Ordens de Serviço por usuário: " + e.getMessage());
		}

		return listaOS;
	}

	/**
	 * Exclui uma ordem de serviço pelo ID.
	 * 
	 * @param id O ID da ordem de serviço a ser excluída.
	 */
	public void excluirOs(String id) {
		String sql = "DELETE FROM tbos WHERE os = ?";

		try (PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setString(1, id);

			int linhasAfetadas = pst.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("Ordem de Serviço excluída com sucesso.");
			} else {
				System.out.println("Nenhuma Ordem de Serviço encontrada com o ID fornecido.");
			}

		} catch (SQLException e) {
			System.err.println("Erro ao excluir Ordem de Serviço: " + e.getMessage());
		}
	}

	/**
	 * Verifica o status da conexão com o banco de dados.
	 * 
	 * @return {@code true} se a conexão estiver ativa, {@code false} caso contrário.
	 */
	public boolean getStatusConnection() {
		return con != null;
	}
}