/**
 * Classe RelatoriosDAO
 * Responsável por gerar e exibir relatórios utilizando JasperReports.
 * A classe interage com o banco de dados para preencher os relatórios com dados necessários.
 */
package br.com.SistemaOS.DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Classe Responsavel por comandar os Relatórios
 */
public class RelatoriosDAO {

	private final Connection con;

	/**
	 * Construtor da classe RelatoriosDAO. Inicializa a conexão com o banco de dados
	 * através da classe ModuloDeConexao.
	 */
	public RelatoriosDAO() {
		this.con = ModuloDeConexao.conector();
	}

	/**
	 * Retorna o status da conexão com o banco de dados.
	 * 
	 * @return {@code true} se a conexão estiver ativa, {@code false} caso
	 *         contrário.
	 */
	public boolean getStatusConnection() {
		return con != null;
	}

	/**
	 * Gera o relatório de clientes e exibe-o na interface gráfica.
	 */
	public void getRelatorioClientes() {
		try {
			// Localiza o arquivo Jasper do relatório de clientes
			InputStream relatorioStream = getClass().getResourceAsStream("/reports/Clientes.jasper");

			// Verifica se o arquivo foi encontrado
			if (relatorioStream == null) {
				System.err.println("Erro: Arquivo Clientes.jasper não encontrado no diretório reports.");
				return;
			}

			// Define os parâmetros do relatório (nenhum parâmetro é passado nesse caso)
			HashMap<String, Object> parametros = new HashMap<>();

			// Preenche o relatório com os dados da base de dados
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, con);

			// Exibe o relatório
			JasperViewer.viewReport(print, false);

		} catch (JRException e) {
			System.err.println("Erro ao gerar o relatório: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Gera o relatório de serviços e exibe-o na interface gráfica.
	 */
	public void getRelatorioServicos() {
		try {
			// Localiza o arquivo Jasper do relatório de serviços
			InputStream relatorioStream = getClass().getResourceAsStream("/reports/Servicos.jasper");

			// Verifica se o arquivo foi encontrado
			if (relatorioStream == null) {
				System.err.println("Erro: Arquivo Servicos.jasper não encontrado no diretório reports.");
				return;
			}

			// Define os parâmetros do relatório (nenhum parâmetro é passado nesse caso)
			HashMap<String, Object> parametros = new HashMap<>();

			// Preenche o relatório com os dados da base de dados
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, con);

			// Exibe o relatório
			JasperViewer.viewReport(print, false);

		} catch (JRException e) {
			System.err.println("Erro ao gerar o relatório: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Gera o relatório de Ordem de Serviço com base no ID do serviço fornecido e
	 * exibe-o na interface gráfica.
	 * 
	 * @param id ID do serviço para o qual o relatório será gerado.
	 */
	public void getRelatorioServicoId(Integer id) {
		try {
			// Localiza o arquivo Jasper do relatório de Ordem de Serviço
			InputStream relatorioStream = getClass().getResourceAsStream("/reports/Os.jasper");

			// Verifica se o arquivo foi encontrado
			if (relatorioStream == null) {
				System.err.println("Erro: Arquivo Os.jasper não encontrado no diretório reports.");
				return;
			}

			// Define os parâmetros do relatório, incluindo o ID do serviço
			HashMap<String, Object> parametros = new HashMap<>();
			parametros.put("id", id);

			// Preenche o relatório com os dados da base de dados
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, con);

			// Exibe o relatório
			JasperViewer.viewReport(print, false);
		} catch (JRException e) {
			System.err.println("Erro ao gerar o relatório: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Gera o relatório de usuários e exibe-o na interface gráfica.
	 */
	public void getRelatorioUsuarios() {
		try {
			// Localiza o arquivo Jasper do relatório de usuários
			InputStream relatorioStream = getClass().getResourceAsStream("/reports/Usuarios.jasper");

			// Verifica se o arquivo foi encontrado
			if (relatorioStream == null) {
				System.err.println("Erro: Arquivo Usuarios.jasper não encontrado no diretório reports.");
				return;
			}

			// Define os parâmetros do relatório (nenhum parâmetro é passado nesse caso)
			HashMap<String, Object> parametros = new HashMap<>();

			// Preenche o relatório com os dados da base de dados
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, con);

			// Exibe o relatório
			JasperViewer.viewReport(print, false);

		} catch (JRException e) {
			System.err.println("Erro ao gerar o relatório: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
