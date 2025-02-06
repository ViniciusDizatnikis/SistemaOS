package br.com.SistemaOS.Telas.Criar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;

import java.math.BigDecimal;
import java.util.List;

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.DAO.CentroOSDAO;
import br.com.SistemaOS.DAO.RelatoriosDAO;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.Cliente;
import br.com.SistemaOS.modelo.Usuario;

/**
 * Tela para criação de uma Ordem de Serviço. Esta classe cria a interface
 * gráfica e contém a lógica para criação de uma ordem de serviço. O usuário
 * seleciona um cliente, preenche os dados do equipamento, defeito, serviço e
 * valor, e a ordem de serviço é criada no sistema.
 * 
 * <p>
 * O processo de criação de uma ordem de serviço envolve:
 * </p>
 * <ul>
 * <li>Preencher os campos de equipamento, defeito, serviço e valor.</li>
 * <li>Selecionar um cliente de uma lista.</li>
 * <li>Criar uma ordem de serviço com os dados preenchidos.</li>
 * <li>Oferecer a opção de imprimir um relatório da ordem de serviço gerada.</li>
 * </ul>
 * 
 * @author Vinicius Dizatnikis
 */
public class CriarOrdemServico extends JFrame {

	private static final long serialVersionUID = 1L;

	/** Painel principal que contém todos os componentes da tela. */
	private JPanel contentPane;

	/** Campos de texto para inserir informações do equipamento, defeito, serviço, valor e ID do usuário. */
	private JTextField fieldEquipamento, fieldDefeito, fieldServico, fieldValor, fieldIdUser;

	/** Rótulos para os campos de texto. */
	private JLabel lblEquipamento, lblDefeito, lblServico, lblValor, lblIdCliente, lblIdUser;

	/** Botão para criar a ordem de serviço. */
	private JButton btnCriar;

	/** Objeto DAO para operações relacionadas a ordens de serviço. */
	private CentroOSDAO dao = new CentroOSDAO();

	/** Objeto DAO para operações relacionadas a clientes. */
	private CentroClientesDAO clienteDao = new CentroClientesDAO();

	/** Utilitário para operações relacionadas à interface gráfica. */
	private ScreenTools util = new ScreenTools();

	/** Objeto DAO para geração de relatórios. */
	private RelatoriosDAO relatorios = new RelatoriosDAO();

	/** Usuário que está criando a ordem de serviço. */
	private Usuario user;

	/** ComboBox para selecionar um cliente da lista. */
	private JComboBox<Object> comboBoxClientes;

	/**
	 * Construtor da classe CriarOrdemServico. Inicializa a interface gráfica e
	 * define os comportamentos dos componentes.
	 * 
	 * @param user O usuário que está criando a ordem de serviço.
	 */
	public CriarOrdemServico(Usuario user) {
		this.user = user;

		// Inicialização do Frame
		setUpFrame();

		// Configuração das labels
		setUpLabels();

		// Configuração dos campos de texto e ComboBox
		setUpFieldsComboBox();

		// Configuração do botão de criar ordem
		setUpButton();

		// Configuração dos ouvintes de eventos
		configureEventListeners();
	}

	/**
	 * Configura os ouvintes de eventos para os campos e botões.
	 */
	private void configureEventListeners() {
		// Ouvinte para o campo de valor (formatação do valor monetário)
		fieldValor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> {
					String text = fieldValor.getText();
					text = text.replaceAll("[^\\d]", "");

					if (text.length() > 2) {
						text = text.substring(0, text.length() - 2) + "." + text.substring(text.length() - 2);
					}

					fieldValor.setText(text);
					fieldValor.setCaretPosition(text.length());
				});
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		// Ouvinte para o botão de criar ordem
		btnCriar.addActionListener(e -> {
			// Obtenção dos dados dos campos
			String equipamento = fieldEquipamento.getText().trim();
			String defeito = fieldDefeito.getText().trim();
			String servico = fieldServico.getText().trim();
			String valor = fieldValor.getText().trim();

			BigDecimal valorDecimal = null;
			try {
				valorDecimal = new BigDecimal(valor);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Por favor, insira um valor válido.");
				return;
			}

			// Verificação de seleção de cliente
			Object itemSelecionado = comboBoxClientes.getSelectedItem();
			if (itemSelecionado == null || itemSelecionado instanceof String) {
				JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente.");
				return;
			}

			Cliente clienteSelecionado = (Cliente) itemSelecionado;
			Integer idCliente = clienteSelecionado.getId();

			// Verificação de campos vazios
			if (isCampoVazio(equipamento, defeito, servico, valor, idCliente)) {
				JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
				return;
			}

			// Criação da ordem de serviço
			Integer id = dao.criarOS(equipamento, defeito, servico, valorDecimal, idCliente, user);

			// Pergunta ao usuário se deseja imprimir a ordem de serviço
			int resposta = JOptionPane.showConfirmDialog(null, "Deseja Imprimir uma via da Ordem de Serviço?",
					"Confirmação", JOptionPane.YES_NO_OPTION);

			if (resposta == JOptionPane.YES_OPTION) {
				relatorios.getRelatorioServicoId(id);
			}
			this.dispose();
		});
	}

	/**
	 * Configura o botão para criar a ordem de serviço.
	 */
	private void setUpButton() {
		btnCriar = new JButton("Criar Ordem");
		util.estilizarBotao(btnCriar);
		btnCriar.setBounds(460, 420, 100, 40);
		contentPane.add(btnCriar);
	}

	/**
	 * Configura os campos de texto e o ComboBox de clientes.
	 */
	private void setUpFieldsComboBox() {
		// Campos de texto para informações do equipamento, defeito, serviço e valor
		fieldEquipamento = new JTextField();
		util.estilizarField(fieldEquipamento, "");
		fieldEquipamento.setBounds(50, 140, 490, 35);
		contentPane.add(fieldEquipamento);

		fieldDefeito = new JTextField();
		util.estilizarField(fieldDefeito, "");
		fieldDefeito.setBounds(550, 140, 400, 35);
		contentPane.add(fieldDefeito);

		fieldServico = new JTextField();
		util.estilizarField(fieldServico, "");
		fieldServico.setBounds(50, 240, 490, 35);
		contentPane.add(fieldServico);

		fieldValor = new JTextField();
		util.estilizarField(fieldValor, "");
		fieldValor.setBounds(550, 240, 400, 35);
		contentPane.add(fieldValor);

		// ComboBox para selecionar o cliente
		comboBoxClientes = new JComboBox<>();
		comboBoxClientes.addItem("Selecione o cliente");
		List<Cliente> clientes = clienteDao.getClientes();
		for (Cliente cliente : clientes) {
			comboBoxClientes.addItem(cliente);
		}
		comboBoxClientes.setBounds(50, 340, 490, 35);
		contentPane.add(comboBoxClientes);

		// Campo de texto para o nome do usuário
		fieldIdUser = new JTextField();
		util.estilizarField(fieldIdUser, user.getNome());
		fieldIdUser.setEnabled(false);
		fieldIdUser.setBounds(550, 340, 400, 35);
		fieldIdUser.setEditable(false);
		contentPane.add(fieldIdUser);
	}

	/**
	 * Configura as labels para cada campo na interface gráfica.
	 */
	private void setUpLabels() {
		// Título da tela
		JLabel lblCriarOrdem = util.criarLabel("Criar Ordem de Serviço", 0, 20, 1004, 50, 36, true);
		contentPane.add(lblCriarOrdem);

		// Labels para os campos de entrada
		lblEquipamento = util.criarLabel("Equipamento:", 50, 100, 150, 30, 20, false);
		contentPane.add(lblEquipamento);

		lblDefeito = util.criarLabel("Defeito:", 550, 100, 122, 30, 20, false);
		contentPane.add(lblDefeito);

		lblServico = util.criarLabel("Serviço:", 50, 200, 146, 30, 20, false);
		contentPane.add(lblServico);

		lblValor = util.criarLabel("Valor (R$):", 550, 200, 146, 30, 20, false);
		contentPane.add(lblValor);

		lblIdCliente = util.criarLabel("Cliente:", 50, 300, 122, 30, 20, false);
		contentPane.add(lblIdCliente);

		lblIdUser = util.criarLabel("Técnico(a):", 550, 300, 122, 30, 20, false);
		contentPane.add(lblIdUser);
	}

	/**
	 * Configura as propriedades do JFrame.
	 */
	private void setUpFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Criar Ordem E Serviço");
		setIconImage(getIconImage());
		setBounds(100, 100, 1020, 540);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(util.getBackgroundColor());
		contentPane.setLayout(null);
		setContentPane(contentPane);
	}

	/**
	 * Verifica se algum campo está vazio.
	 * 
	 * @param equipamento O nome do equipamento.
	 * @param defeito     O defeito do equipamento.
	 * @param servico     O serviço a ser realizado.
	 * @param valor       O valor do serviço.
	 * @param idCliente   O ID do cliente.
	 * @return true se algum campo estiver vazio, false caso contrário.
	 */
	private boolean isCampoVazio(String equipamento, String defeito, String servico, String valor, Integer idCliente) {
		if (equipamento == null || equipamento.trim().isEmpty()) {
			return true;
		}
		if (defeito == null || defeito.trim().isEmpty()) {
			return true;
		}
		if (servico == null || servico.trim().isEmpty()) {
			return true;
		}
		if (valor == null || valor.trim().isEmpty()) {
			return true;
		}
		if (idCliente == null || idCliente < 1) {
			return true;
		}

		return false;
	}
}