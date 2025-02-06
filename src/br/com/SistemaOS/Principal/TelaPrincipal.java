package br.com.SistemaOS.Principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.DAO.CentroOSDAO;
import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.DAO.RelatoriosDAO;
import br.com.SistemaOS.Telas.Criar.CriarCliente;
import br.com.SistemaOS.Telas.Detalhes.DetalhesOrdemServico;
import br.com.SistemaOS.Telas.Gerenciamento.GerenciarClientes;
import br.com.SistemaOS.Telas.Gerenciamento.GerenciarOS;
import br.com.SistemaOS.Telas.Gerenciamento.GerenciarUsuarios;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.OrdemServico;
import br.com.SistemaOS.modelo.Usuario;
import java.awt.event.InputEvent;

/**
 * Classe principal da interface gráfica do sistema, responsável por gerenciar
 * as interações do usuário com o sistema de Ordens de Serviço. Exibe a tela
 * principal com dados do usuário, informações de status, ordens de serviço,
 * entre outros.
 * 
 * @author Vinicius Dizatnikis
 * @version 1.1
 */
/**
 * Classe principal responsável por exibir a interface do sistema de Ordem e
 * Serviço.
 */
public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Painel principal da janela.
	 */
	private JPanel contentPane;

	// Informações do Usuário

	/**
	 * Usuário logado no sistema.
	 */
	private Usuario user;

	/**
	 * Indica se o usuário é um administrador.
	 */
	private boolean isAdmin;

	/**
	 * Indica se o usuário possui privilégios de administrador.
	 */
	private boolean isAdministrador;

	/**
	 * Rótulo para exibir o status.
	 */
	private JLabel status;

	// Tabela

	/**
	 * Modelo da tabela para exibição de dados.
	 */
	private DefaultTableModel model;

	// Dependências

	/**
	 * DAO para geração de relatórios.
	 */
	private RelatoriosDAO relatorios = new RelatoriosDAO();

	/**
	 * DAO para ações relacionadas aos usuários.
	 */
	private CentroUsuariosDAO userDao = new CentroUsuariosDAO();

	/**
	 * DAO para ações relacionadas às ordens de serviço.
	 */
	private CentroOSDAO osDao = new CentroOSDAO();

	/**
	 * Ferramentas utilitárias reutilizáveis.
	 */
	private ScreenTools util = new ScreenTools();

	/**
	 * DAO para ações relacionadas aos clientes.
	 */
	private CentroClientesDAO cliDAO = new CentroClientesDAO();

	/**
	 * Construtor da classe TelaPrincipal.
	 * 
	 * @param usuInfo Informações do usuário que está logado no sistema.
	 */
	public TelaPrincipal(Usuario usuInfo) {
		// Coletar Informações do Usuário
		this.user = usuInfo;
		this.isAdmin = usuInfo.getPerfil().equals("admin");
		this.isAdministrador = usuInfo.getNome().equals("Administrador");

		setUpFrame();

		setUpMenuBar();

		setUpContentPane();
	}

	/**
	 * Configura o frame principal da aplicação.
	 */
	private void setUpFrame() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(TelaPrincipal.class.getResource("/br/com/SistemaOS/Icones/icon/Logo.png")));

		setTitle("Sistema De Ordem e Serviço");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1360, 720);
		setLocationRelativeTo(null);
		setResizable(false);

		addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				carregarDados(model);
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
			}
		});
	}

	/**
	 * Configura a barra de menus da tela principal.
	 * 
	 * @param isAdmin Indica se o usuário possui perfil de administrador.
	 */
	private void setUpMenuBar() {
		// Criar a barra de menus
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Menu Cadastro
		JMenu menuCadastro = new JMenu("Cadastro");
		menuBar.add(menuCadastro);

		JMenuItem menuClientes = new JMenuItem(isAdmin ? "Clientes" : "Criar Cliente");
		menuClientes.setAccelerator(KeyStroke.getKeyStroke("control C"));
		menuClientes.addActionListener(e -> abrirCadastroCliente());
		menuCadastro.add(menuClientes);

		JMenuItem menuOS = new JMenuItem("OS");
		menuOS.setAccelerator(KeyStroke.getKeyStroke("control O"));
		menuOS.addActionListener(e -> abrirCadastroOS());
		menuCadastro.add(menuOS);

		JMenuItem menuUsuarios = new JMenuItem("Usuários");
		menuUsuarios.setAccelerator(KeyStroke.getKeyStroke("control U"));
		menuUsuarios.addActionListener(e -> abrirCadastroUsuarios());
		menuUsuarios.setEnabled(isAdmin);
		menuCadastro.add(menuUsuarios);

		// Menu Relatórios
		JMenu menuRelatorios = new JMenu("Relatórios");
		menuRelatorios.setEnabled(isAdmin);
		menuBar.add(menuRelatorios);

		JMenuItem servicosItem = new JMenuItem("Serviços");
		servicosItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		servicosItem.addActionListener(e -> gerarRelatorioServicos());
		menuRelatorios.add(servicosItem);

		JMenuItem clientesItem = new JMenuItem("Clientes");
		clientesItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		clientesItem.addActionListener(e -> gerarRelatorioClientes());
		menuRelatorios.add(clientesItem);

		JMenuItem usuariosItem = new JMenuItem("Usuarios");
		usuariosItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		usuariosItem.addActionListener(e -> gerarRelatoriosUsuarios());
		menuRelatorios.add(usuariosItem);

		// Menu Ajuda
		JMenu menuAjuda = new JMenu("Ajuda");
		menuBar.add(menuAjuda);

		JMenuItem sobreItem = new JMenuItem("Sobre");
		sobreItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		sobreItem.addActionListener(e -> exibirSobre());
		menuAjuda.add(sobreItem);

		// Menu Opções
		JMenu menuOpcoes = new JMenu("Opções");
		menuBar.add(menuOpcoes);

		JMenuItem logoffItem = new JMenuItem("Logoff");
		logoffItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.ALT_MASK));
		logoffItem.addActionListener(e -> logOffAction());
		menuOpcoes.add(logoffItem);

		JMenuItem sairItem = new JMenuItem("Sair");
		sairItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		sairItem.addActionListener(e -> sairAction());
		menuOpcoes.add(sairItem);
	}

	/**
	 * Abre a tela de cadastro de cliente.
	 */
	private void abrirCadastroCliente() {
		if (cliDAO.getStatusConnection()) {
			if (isAdmin) {
				new GerenciarClientes().setVisible(true);
			} else {
				new CriarCliente().setVisible(true);
			}
		} else {
			util.AvisoDeConexão();
		}
	}

	/**
	 * Abre a tela de cadastro de ordem de serviço.
	 */
	private void abrirCadastroOS() {
		if (userDao.getStatusConnection()) {
			new GerenciarOS(user).setVisible(true);
		} else {
			util.AvisoDeConexão();
		}
	}

	/**
	 * Abre a tela de cadastro de usuários.
	 */
	private void abrirCadastroUsuarios() {
		if (userDao.getStatusConnection()) {
			new GerenciarUsuarios(user).setVisible(true);
		} else {
			util.AvisoDeConexão();
		}
	}

	/**
	 * Gera o relatório de usuários.
	 */
	private void gerarRelatoriosUsuarios() {
		if (relatorios.getStatusConnection()) {
			relatorios.getRelatorioUsuarios();
		} else {
			util.AvisoDeConexão();
		}
	}

	/**
	 * Gera o relatório de serviços.
	 */
	private void gerarRelatorioServicos() {
		if (relatorios.getStatusConnection()) {
			relatorios.getRelatorioServicos();
		} else {
			util.AvisoDeConexão();
		}
	}

	/**
	 * Gera o relatório de clientes.
	 */
	private void gerarRelatorioClientes() {
		if (relatorios.getStatusConnection()) {
			relatorios.getRelatorioClientes();
		} else {
			util.AvisoDeConexão();
		}
	}

	/**
	 * Exibe informações sobre o sistema.
	 */
	private void exibirSobre() {
		JOptionPane.showMessageDialog(this,
				"Sistema de Ordem e Serviço - Versão 1.0 \nDesenvolvido Por: ViniciusDizatnikis", "Sobre",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Executa o logoff do usuário atual e redireciona para a tela de login.
	 */
	private void logOffAction() {
		int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja fazer logoff?", "Confirmação",
				JOptionPane.YES_NO_OPTION);
		if (confirmacao == JOptionPane.YES_OPTION) {
			new TelaLogin().setVisible(true);
			this.dispose();
		}
	}

	/**
	 * Fecha o sistema após confirmação do usuário.
	 */
	private void sairAction() {
		int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza de que deseja sair do sistema?", "Sair",
				JOptionPane.YES_NO_OPTION);
		if (confirmacao == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Configura o painel de conteúdo da tela principal, incluindo a tabela de
	 * ordens de serviço.
	 * 
	 * @param usuInfo Informações do usuário.
	 */
	private void setUpContentPane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(util.getBackgroundColor());
		contentPane.setLayout(null);
		setContentPane(contentPane);

		setUpTable();
		setUpUserIcon(contentPane);
		setUpUserDetails(contentPane);
		setUpDateAndTimePanel();

		String TextPainel = isAdmin ? "Ordens e Serviço da Empresa" : "Suas Ordens e Serviços";

		JLabel lblTexto = new JLabel(TextPainel);
		lblTexto.setForeground(Color.WHITE);
		lblTexto.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 61));
		lblTexto.setHorizontalAlignment(SwingConstants.LEFT);
		lblTexto.setBounds(10, 0, 927, 82);
		contentPane.add(lblTexto);

		status = new JLabel("<dynamic>");
		status.setHorizontalAlignment(SwingConstants.CENTER);
		status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		status.setForeground(Color.WHITE);
		status.setBounds(947, 623, 387, 25);
		contentPane.add(status);
	}

	/**
	 * Atualiza o status de conexão com o banco de dados.
	 */
	private void atualizarStatus() {
		boolean conectado = userDao.getStatusConnection();
		String mensagem = conectado ? "Status: Conectado ao banco de dados"
				: "Status: Erro ao conectar ao banco de dados";
		Color cor = conectado ? util.SUCCESS_COLOR : util.ERROR_COLOR;

		status.setText(mensagem);
		status.setForeground(cor);
	}

	/**
	 * Configura a tabela de ordens de serviço.
	 */
	private void setUpTable() {
		String[] columnNames = new String[] { "ID Venda", "Data", "Equipamento", "Defeito", "Serviço", "Valor",
				"Cliente", "Técnico(a)" };

		model = new DefaultTableModel(columnNames, 0);
		carregarDados(model);

		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		contentPane.add(scrollPane);
		scrollPane.setBounds(10, 92, 927, 555);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
					inspectItem(table);
				}
			}
		});

	}

	/**
	 * Carrega os dados da tabela com as ordens de serviço.
	 * 
	 * @param model Modelo da tabela onde os dados serão carregados.
	 */
	private void carregarDados(DefaultTableModel model) {
		// Limpa os dados antigos antes de carregar os novos
		model.setRowCount(0);

		List<OrdemServico> data = isAdministrador ? osDao.listarTodasOs() : osDao.listarOsComId(user.getId());

		for (OrdemServico os : data) {
			model.addRow(new Object[] { os.getOs(), os.getData(), os.getEquipamento(), os.getDefeito(), os.getServico(),
					os.getValor(), os.getCliente(), os.getTecnico() });
		}
	}

	/**
	 * Exibe os detalhes de uma ordem de serviço.
	 * 
	 * @param table A tabela onde o item será inspecionado.
	 */
	private void inspectItem(JTable table) {
		int rowIndex = table.getSelectedRow();
		if (rowIndex != -1) {
			OrdemServico os;
			if (isAdministrador) {
				os = (OrdemServico) osDao.listarTodasOs().get(rowIndex);
			} else {
				os = osDao.listarOsComId(user.getId()).get(rowIndex);
			}
			DetalhesOrdemServico info = new DetalhesOrdemServico(false);
			info.exibirDetalhes(os);
			info.setVisible(true);
		}
	}

	/**
	 * Configura o ícone do usuário exibido na tela.
	 * 
	 * @param contentPane Painel de conteúdo onde o ícone será adicionado.
	 */
	private void setUpUserIcon(JPanel contentPane) {
		JLabel usuarioIcon = new JLabel();
		usuarioIcon.setHorizontalAlignment(SwingConstants.CENTER);
		usuarioIcon.setBounds(1010, 247, 267, 264);
		usuarioIcon.setIcon(util.mudarTamanhoImg("/br/com/SistemaOS/Icones/icon/homem-usuario.png", 260, 260));
		contentPane.add(usuarioIcon);
	}

	/**
	 * Configura os detalhes do usuário (nome e saudação) exibidos na tela.
	 * 
	 * @param contentPane Painel de conteúdo onde os detalhes serão exibidos.
	 */
	private void setUpUserDetails(JPanel contentPane) {
		String splitName = user.getNome();
		if (user.getNome().contains(" ")) {
			splitName = splitName.split(" ")[0];
		}
		JLabel lblUsuario = new JLabel(splitName);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 25));
		lblUsuario.setBounds(947, 161, 387, 34);
		contentPane.add(lblUsuario);
	}

	/**
	 * Configura a área de data e hora exibida na tela.
	 */
	private void setUpDateAndTimePanel() {
		JPanel desktopPanel_1 = new JPanel();
		desktopPanel_1.setBackground(Color.GRAY);
		desktopPanel_1.setBounds(947, 550, 387, 71);
		contentPane.add(desktopPanel_1);
		desktopPanel_1.setLayout(null);

		JLabel lblText = new JLabel("Data:");
		lblText.setBounds(5, 5, 189, 26);
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		lblText.setForeground(Color.WHITE);
		lblText.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 24));
		desktopPanel_1.add(lblText);

		JLabel lblHora = new JLabel("Hora:");
		lblHora.setHorizontalAlignment(SwingConstants.CENTER);
		lblHora.setForeground(Color.WHITE);
		lblHora.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 24));
		lblHora.setBounds(193, 5, 184, 26);
		desktopPanel_1.add(lblHora);

		JLabel dataSource = new JLabel("<Dynamic>");
		dataSource.setHorizontalAlignment(SwingConstants.CENTER);
		dataSource.setForeground(Color.WHITE);
		dataSource.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
		dataSource.setBounds(5, 34, 189, 26);
		desktopPanel_1.add(dataSource);

		JLabel horaSource = new JLabel("<Dynamic>");
		horaSource.setHorizontalAlignment(SwingConstants.CENTER);
		horaSource.setForeground(Color.WHITE);
		horaSource.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
		horaSource.setBounds(193, 34, 189, 26);
		desktopPanel_1.add(horaSource);

		JLabel lblSaudacao = new JLabel("<dynamic>");
		lblSaudacao.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaudacao.setForeground(Color.WHITE);
		lblSaudacao.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 32));
		lblSaudacao.setBounds(947, 99, 387, 51);
		contentPane.add(lblSaudacao);

		Timer timerClock = new Timer(1000, e -> {
			lblSaudacao.setText(util.getSaudacao());
			dataSource.setText(util.getDataAtual());
			horaSource.setText(util.getHoraAtual());
			atualizarStatus();
		});
		timerClock.start();
	}
}
