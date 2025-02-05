package br.com.SistemaOS.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.SistemaOS.modelo.Cliente;
import br.com.SistemaOS.modelo.OrdemServico;
import br.com.SistemaOS.modelo.Usuario;

public class CentroOSDAO {

	private final Connection con;
	private final boolean status;

	public CentroOSDAO() {
		this.con = ModuloDeConexao.conector();
		this.status = con != null;
	}

	public void criarOS(String equipamento, String defeito, String servico, BigDecimal valor, Integer idcliente,
			Usuario user) {
		String sql = "INSERT INTO tbos (equipamento, defeito, servico, tecnico, valor, idcliente, iduser) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setString(1, equipamento);
			pst.setString(2, defeito);
			pst.setString(3, servico);
			pst.setString(4, user.getNome());
			pst.setBigDecimal(5, valor);
			pst.setInt(6, idcliente);
			pst.setInt(7, user.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<OrdemServico> listarTodasOs() {
	    String sql = """
	                SELECT
	                    O.os, O.data_os, O.equipamento, O.defeito, O.servico, O.valor,
	                    C.nome AS cliente, C.fone AS contato, C.idcliente,
	                    U.usuario AS tecnico
	                FROM
	                    tbos AS O
	                JOIN
	                    clientes AS C ON O.idcliente = C.idcliente
	                JOIN
	                    usuarios AS U ON O.iduser = U.iduser;
	            """;

	    List<OrdemServico> listaOS = new ArrayList<>();

	    try (PreparedStatement pst = con.prepareStatement(sql)) {
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
	        e.printStackTrace();
	    }

	    return listaOS;
	}


	public List<OrdemServico> listarOsComId(Integer id) {
		String sql = """
				    SELECT
				        O.os, O.data_os, O.equipamento, O.defeito, O.servico, O.valor,
				        C.nome AS cliente, C.fone AS contato, C.idcliente,
				        U.usuario AS tecnico
				    FROM
				        tbos AS O
				    JOIN
				        clientes AS C ON O.idcliente = C.idcliente
				    JOIN
				        usuarios AS U ON O.iduser = U.iduser
				    WHERE
				        O.iduser = ?;
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
			e.printStackTrace();
		}

		return listaOS;
	}

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
			e.printStackTrace();
		}
	}

}
