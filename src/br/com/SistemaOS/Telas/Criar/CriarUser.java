package br.com.SistemaOS.Telas.Criar;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.Utils.ScreenTools;

/**
 * Tela de criação de usuários. Esta classe cria a interface gráfica para o
 * cadastro de um novo usuário no sistema. O usuário preenche os campos de nome,
 * telefone, login, senha e perfil, e o sistema cria o novo usuário.
 * 
 * <p>
 * O processo de criação de um usuário envolve:
 * </p>
 * <ul>
 * <li>Preencher os campos: nome, telefone, login, senha e selecionar o
 * perfil.</li>
 * <li>Verificar se todos os campos foram preenchidos corretamente.</li>
 * <li>Verificar se o login já está em uso.</li>
 * <li>Criar o usuário no banco de dados.</li>
 * </ul>
 * 
 * @author Vinicius Dizatnikis
 */
public class CriarUser extends JFrame {

	private static final long serialVersionUID = 1L;

	/** Painel principal que contém todos os componentes da tela. */
	private JPanel contentPane;

	/** Campos de texto para inserir informações do usuário. */
	private JTextField fieldNome, fieldFone, fieldSenha, fieldLogin;

	/** Rótulo para o campo de perfil. */
	private JLabel lblPerfil;

	/** ComboBox para selecionar o perfil do usuário. */
	private JComboBox<String> cbPerfil;

	/** Botão para criar o usuário. */
	private JButton btnCriar;

	/** Objeto DAO para operações relacionadas a usuários. */
	private CentroUsuariosDAO dao = new CentroUsuariosDAO();

	/** Utilitário para operações relacionadas à interface gráfica. */
	private ScreenTools util = new ScreenTools();

	/**
	 * Construtor da classe CriarUser. Inicializa a interface gráfica e define os
	 * comportamentos dos componentes.
	 */
	public CriarUser() {

		// Configurações da interface gráfica
		setUpFrame();
		setUpLabels();
		setUpLabelsComboBox();
		SetUpButton();
		configureListeners();
	}

	/**
	 * Configura o botão "Criar" e define sua ação.
	 */
	private void SetUpButton() {
		btnCriar = new JButton("Criar");
		util.estilizarBotao(btnCriar);
		btnCriar.setBounds(460, 420, 100, 40);
		contentPane.add(btnCriar);
	}

	/**
	 * Configura os ouvintes de eventos para os campos de entrada e o botão de criar
	 * usuário.
	 */
	private void configureListeners() {
		// Ouvinte para o campo de telefone, que formata o número inserido
		fieldFone.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// Método não utilizado
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				util.formatarCelular(fieldFone);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// Método não utilizado
			}
		});

		// Ouvinte para o botão "Criar"
		btnCriar.addActionListener(e -> cadastrarUsuario());
	}

	/**
	 * Realiza o cadastro do usuário, verificando se os campos estão preenchidos
	 * corretamente e se o login não está em uso.
	 */
	private void cadastrarUsuario() {
		String nome = fieldNome.getText().trim();
		String fone = fieldFone.getText().trim();
		String login = fieldLogin.getText().trim();
		String senha = fieldSenha.getText().trim();
		String perfil = cbPerfil.getSelectedItem().toString().toLowerCase();

		// Verifica se algum campo está vazio
		if (isCampoVazio(nome, fone, login, senha)) {
			JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
			return;
		}

		// Verifica se o login já está em uso
		if (dao.loginExistente(login)) {
			JOptionPane.showMessageDialog(null, "O login já está em uso!", "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Cria o novo usuário
		criarUsuario(nome, fone, login, senha, perfil);
		JOptionPane.showMessageDialog(this, "Usuário criado com sucesso!");
		this.dispose();
	}

	/**
	 * Configura os campos de texto e o ComboBox para seleção de perfil.
	 */
	private void setUpLabelsComboBox() {
		fieldNome = new JTextField();
		util.estilizarField(fieldNome, "");
		fieldNome.setBounds(50, 140, 490, 35);
		contentPane.add(fieldNome);

		cbPerfil = new JComboBox<>(new String[] { "User", "Admin" });
		cbPerfil.setBounds(550, 140, 200, 35);
		cbPerfil.setBorder(new LineBorder(Color.GRAY, 1, true));
		contentPane.add(cbPerfil);

		fieldFone = new JTextField();
		util.estilizarField(fieldFone, "");
		fieldFone.setBounds(50, 240, 490, 35);
		contentPane.add(fieldFone);

		fieldLogin = new JTextField();
		util.estilizarField(fieldLogin, "");
		fieldLogin.setBounds(50, 340, 490, 35);
		contentPane.add(fieldLogin);

		fieldSenha = new JTextField();
		util.estilizarField(fieldSenha, "");
		fieldSenha.setBounds(550, 340, 400, 35);
		contentPane.add(fieldSenha);
	}

	/**
	 * Configura as labels para cada campo na interface gráfica.
	 */
	private void setUpLabels() {
		// Título da tela
		JLabel lblCriarUsuario = util.criarLabel("Criar Usuário", 0, 20, 1004, 50, 36, true);
		contentPane.add(lblCriarUsuario);

		// Labels para os campos
		JLabel lblNome = util.criarLabel("Nome:", 50, 100, 122, 30, 20, false);
		contentPane.add(lblNome);

		lblPerfil = util.criarLabel("Perfil:", 550, 100, 122, 30, 20, false);
		contentPane.add(lblPerfil);

		JLabel lblTelefone = util.criarLabel("Telefone:", 50, 200, 146, 30, 20, false);
		contentPane.add(lblTelefone);

		JLabel lblLogin = util.criarLabel("Login:", 50, 300, 95, 30, 20, false);
		contentPane.add(lblLogin);

		JLabel lblSenha = util.criarLabel("Senha:", 550, 300, 146, 30, 20, false);
		contentPane.add(lblSenha);
	}

	/**
	 * Configura as propriedades do JFrame.
	 */
	private void setUpFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 540);
		setTitle("Criar Novo Usuário");
		setIconImage(util.getLogo());
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(util.getBackgroundColor());
		contentPane.setLayout(null);
		setContentPane(contentPane);
	}

	/**
	 * Cria o usuário no banco de dados chamando o método correspondente na classe
	 * CentroUsuariosDAO.
	 * 
	 * @param nome   O nome do usuário.
	 * @param fone   O telefone do usuário.
	 * @param login  O login do usuário.
	 * @param senha  A senha do usuário.
	 * @param perfil O perfil do usuário (User ou Admin).
	 */
	public void criarUsuario(String nome, String fone, String login, String senha, String perfil) {
		dao.criarUsuario(nome, fone, login, senha, perfil);
	}

	/**
	 * Verifica se algum dos campos fornecidos está vazio.
	 * 
	 * @param campos Os campos a serem verificados.
	 * @return true se algum campo estiver vazio, false caso contrário.
	 */
	private boolean isCampoVazio(String... campos) {
		for (String campo : campos) {
			if (campo == null || campo.trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}
}