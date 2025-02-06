package br.com.SistemaOS.Principal;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.Utils.CustomDialog;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.Usuario;
import java.util.logging.*;

/**
 * Tela de login do sistema de Ordem e Serviço. Esta classe é responsável por
 * exibir a interface de login, validar as credenciais de um usuário e acessar a
 * tela principal do sistema caso o login seja bem-sucedido.
 */
public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Painel principal da janela.
	 */
	private JPanel contentPane;

	/**
	 * Campo de texto para o nome do usuário.
	 */
	private JTextField txtNome;

	/**
	 * Campo de senha do usuário.
	 */
	private JPasswordField txtPassword;

	/**
	 * Rótulo para exibir o status da conexão.
	 */
	private JLabel lblStatus;

	/**
	 * Timer para atualização do status da conexão.
	 */
	private Timer statusTimer;

	/**
	 * Botão para realizar o login.
	 */
	private JButton btnLogin;

	// Dependências

	/**
	 * DAO para realizar ações relacionadas aos usuários.
	 */
	private final CentroUsuariosDAO dao = new CentroUsuariosDAO();

	/**
	 * Ferramentas utilitárias reutilizáveis.
	 */
	private final ScreenTools util = new ScreenTools();

	/**
	 * Construtor da tela de login. Configura a interface e os eventos.
	 */
	public TelaLogin() {
		configurarJanela();
		inicializarComponentes();
		configurarEventos();
	}

	/**
	 * Configura as propriedades básicas da janela de login, como título, ícone e
	 * tamanho.
	 */
	private void configurarJanela() {
		setTitle("Login - SOS");
		setIconImage(util.getLogo());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1013, 531);
		setLocationRelativeTo(null);
	}

	/**
	 * Inicializa os componentes gráficos da tela de login, incluindo campos de
	 * texto, botões e labels.
	 */
	private void inicializarComponentes() {
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(util.getBackgroundColor());

		JLabel logoImagem = new JLabel(util.mudarTamanhoImg("/br/com/SistemaOS/Icones/icon/Logo.png", 90, 90));
		logoImagem.setBounds(centralizarComponente(80), 0, 80, 80);
		contentPane.add(logoImagem);

		JLabel lblTitulo = createLabel("QuickFix Pro", 71, 22);
		JLabel lblSubtitulo = createLabel("Sistema de Ordem e Serviço", 96, 15);
		contentPane.add(lblTitulo);
		contentPane.add(lblSubtitulo);

		txtNome = criarCampoTexto("Usuário:", 161, 191);
		txtPassword = criarCampoSenha("Senha:", 251, 281);
		btnLogin = criarBotaoLogin();
		lblStatus = criarLabelStatus();

		contentPane.add(txtNome);
		contentPane.add(txtPassword);
		contentPane.add(btnLogin);
		contentPane.add(lblStatus);

		JButton btn = new JButton("Entrar");
		util.estilizarBotao(btn);
		btn.setBounds(centralizarComponente(util.CAMPO_LARGURA), 345, util.CAMPO_LARGURA, util.CAMPO_ALTURA);

		JLabel lblByVinicius = new JLabel("by vinicius", SwingConstants.CENTER);
		lblByVinicius.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		lblByVinicius.setForeground(Color.LIGHT_GRAY);
		lblByVinicius.setBounds(centralizarComponente(100), getHeight() - 80, 100, 20);

		setContentPane(contentPane);
		getRootPane().setDefaultButton(btnLogin);
		atualizarStatus();
	}

	/**
	 * Cria um JLabel centralizado com o texto fornecido.
	 * 
	 * @param texto    Texto do JLabel.
	 * @param posY     Posição vertical do label.
	 * @param fontSize Tamanho da fonte do label.
	 * @return O JLabel criado.
	 */
	private JLabel createLabel(String texto, int posY, int fontSize) {
		JLabel label = new JLabel(texto, SwingConstants.CENTER);
		label.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, fontSize));
		label.setForeground(Color.WHITE);
		label.setBounds(centralizarComponente(util.CAMPO_LARGURA), posY, util.CAMPO_LARGURA, 40);
		return label;
	}

	/**
	 * Cria um campo de texto (JTextField) com o label associado.
	 * 
	 * @param label     Texto do label.
	 * @param labelPosY Posição vertical do label.
	 * @param campoPosY Posição vertical do campo de texto.
	 * @return O JTextField criado.
	 */
	private JTextField criarCampoTexto(String label, int labelPosY, int campoPosY) {
		JLabel lblUsuario = new JLabel(label, SwingConstants.LEFT);
		lblUsuario.setFont(util.DEFAULT_FONT);
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setBounds(centralizarComponente(util.CAMPO_LARGURA), labelPosY, util.CAMPO_LARGURA, 30);
		contentPane.add(lblUsuario);

		JTextField txtCampo = new JTextField();
		util.estilizarField(txtCampo, "");
		txtCampo.setBounds(centralizarComponente(util.CAMPO_LARGURA), campoPosY, util.CAMPO_LARGURA, util.CAMPO_ALTURA);
		txtCampo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		return txtCampo;
	}

	/**
	 * Cria um campo de senha (JPasswordField) com o label associado.
	 * 
	 * @param label     Texto do label.
	 * @param labelPosY Posição vertical do label.
	 * @param campoPosY Posição vertical do campo de senha.
	 * @return O JPasswordField criado.
	 */
	private JPasswordField criarCampoSenha(String label, int labelPosY, int campoPosY) {
		JLabel lblSenha = new JLabel(label, SwingConstants.LEFT);
		lblSenha.setFont(util.DEFAULT_FONT);
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setBounds(centralizarComponente(util.CAMPO_LARGURA), labelPosY, util.CAMPO_LARGURA, 30);
		contentPane.add(lblSenha);

		JPasswordField txtCampo = new JPasswordField();
		txtCampo.setFont(util.DEFAULT_FONT);
		txtCampo.setBounds(centralizarComponente(util.CAMPO_LARGURA), campoPosY, util.CAMPO_LARGURA, util.CAMPO_ALTURA);
		txtCampo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		txtCampo.setBackground(util.INPUT_COLOR);
		txtCampo.setForeground(Color.WHITE);
		return txtCampo;
	}

	/**
	 * Cria o botão de login.
	 * 
	 * @return O JButton do login.
	 */
	private JButton criarBotaoLogin() {
		JButton btn = new JButton("Entrar");
		util.estilizarBotao(btn);
		btn.setBounds(centralizarComponente(util.CAMPO_LARGURA), 345, util.CAMPO_LARGURA, util.CAMPO_ALTURA);
		return btn;
	}

	/**
	 * Cria o label de status para exibir a conexão com o banco de dados.
	 * 
	 * @return O JLabel de status.
	 */
	private JLabel criarLabelStatus() {
		JLabel status = new JLabel("", SwingConstants.CENTER);
		status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		status.setForeground(Color.WHITE);
		status.setBounds(centralizarComponente(util.CAMPO_LARGURA), 420, util.CAMPO_LARGURA, 30);
		return status;
	}

	/**
	 * Centraliza um componente com base em sua largura.
	 * 
	 * @param larguraItem Largura do componente.
	 * @return A posição X centralizada.
	 */
	private int centralizarComponente(int larguraItem) {
		return (getWidth() - larguraItem) / 2;
	}

	/**
	 * Configura os eventos dos componentes da tela, incluindo o botão de login e o
	 * timer de atualização do status de conexão.
	 */
	private void configurarEventos() {
		configurarEventoBotaoLogin();
		configurarTimerStatus();
	}

	/**
	 * Configura o evento de clique no botão de login.
	 */
	private void configurarEventoBotaoLogin() {
		if (!dao.getStatusConnection()) {
			return;
		}
		btnLogin.addActionListener(e -> realizarLogin());
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogin.setBackground(new Color(33, 116, 133));
				btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnLogin.setBackground(new Color(63, 182, 207));
				btnLogin.setCursor(Cursor.getDefaultCursor());
			}
		});
	}

	/**
	 * Configura o timer para atualizar o status de conexão com o banco de dados a
	 * cada 4 segundos.
	 */
	private void configurarTimerStatus() {
		statusTimer = new Timer(4000, e -> atualizarStatus());
		statusTimer.start();
	}

	/**
	 * Atualiza o status de conexão com o banco de dados e altera a cor do texto de
	 * acordo.
	 */
	private void atualizarStatus() {
		boolean conectado = dao.getStatusConnection();
		String mensagem = conectado ? "Status: Conectado ao banco de dados"
				: "Status: Erro ao conectar ao banco de dados";
		Color cor = conectado ? util.SUCCESS_COLOR : util.ERROR_COLOR;

		lblStatus.setText(mensagem);
		lblStatus.setForeground(cor);
	}

	/**
	 * Realiza o processo de login do usuário, verificando se as credenciais estão
	 * corretas.
	 */
	private void realizarLogin() {
		String username = txtNome.getText().trim();
		String password = new String(txtPassword.getPassword()).trim();

		if (username.isEmpty() || password.isEmpty()) {
			exibirMensagem("Por favor, preencha todos os campos.", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			Usuario usuario = dao.login(username, password);
			if (usuario != null) {
				abrirTelaPrincipal(usuario);
			} else {
				exibirMensagem("Credenciais inválidas.", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
			exibirMensagem("Erro ao realizar login.", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Abre a tela principal do sistema após um login bem-sucedido.
	 * 
	 * @param usuario O usuário autenticado.
	 */
	private void abrirTelaPrincipal(Usuario usuario) {
		TelaPrincipal telaPrincipal = new TelaPrincipal(usuario);
		CustomDialog carregandoDialog = new CustomDialog(this, "Carregando, por favor aguarde...", 2000);
		carregandoDialog.setVisible(true);

		telaPrincipal.setVisible(true);
		dispose();
	}

	/**
	 * Exibe uma mensagem em um diálogo de aviso ou erro.
	 * 
	 * @param mensagem    A mensagem a ser exibida.
	 * @param messageType O tipo de mensagem (informação, aviso, erro).
	 */
	private void exibirMensagem(String mensagem, int messageType) {
		JOptionPane.showMessageDialog(this, mensagem, "Aviso", messageType);
	}
}
