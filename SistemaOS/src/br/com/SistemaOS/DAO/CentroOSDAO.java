package br.com.SistemaOS.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CentroOSDAO {

	private Connection con;
	private boolean status = false;

	public CentroOSDAO() {
		this.con = ModuloDeConexao.conector();
		if (con != null) {
			status = true;
		}
	}

	public Object[][] getOrdensEServico(int id) {
		String sql = "SELECT O.os, O.data_os, O.equipamento, O.defeito, O.servico, O.valor, C.nome AS cliente, C.fone AS contato "
				+ "FROM tbos AS O " + "JOIN clientes AS C ON O.idcliente = C.idcliente " + "WHERE O.iduser = ?";

		Object[][] data = null;

		try (PreparedStatement pst = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				rs.last();
				int rowCount = rs.getRow();
				rs.beforeFirst();

				data = new Object[rowCount][8];

				int rowIndex = 0;
				while (rs.next()) {
					data[rowIndex][0] = rs.getInt("os");
					data[rowIndex][1] = rs.getString("data_os");
					data[rowIndex][2] = rs.getString("cliente");
					data[rowIndex][3] = rs.getString("contato");
					data[rowIndex][4] = rs.getString("equipamento");
					data[rowIndex][5] = rs.getString("defeito");
					data[rowIndex][6] = rs.getString("servico");
					data[rowIndex][7] = rs.getDouble("valor");
					rowIndex++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}
}
