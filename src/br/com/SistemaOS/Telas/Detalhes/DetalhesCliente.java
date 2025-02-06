package br.com.SistemaOS.Telas.Detalhes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.Utils.ScreenTools;

/**
 * Tela de detalhes do cliente, onde é possível visualizar e editar informações
 * do cliente. Também permite a exclusão de um cliente e a atualização dos seus
 * dados.
 */
public class DetalhesCliente extends JFrame {

	private static final long serialVersionUID = 1L;

	// Campos de botão e rótulos
	/** Painel principal da interface. */
	private JPanel contentPane;
	/** Campo de texto para o endereço do cliente. */
	private JTextField enderecoField;
	/** Campo de texto para o nome do cliente. */
	private JTextField nomeField;
	/** Campo de texto para o telefone do cliente. */
	private JTextField foneField;
	/** Campo de texto para o email do cliente. */
	private JTextField emailField;
	/** Botão para excluir o cliente. */
	private JButton btnExcluir;
	/** Botão para salvar e sair da tela de detalhes. */
	private JButton btnSalvarESair;
	/** Label para o título da tela. */
	private JLabel lblTitle;
	/** Label para a foto do usuário. */
	private JLabel fotoUser;
	/** Label para o texto "Cliente:". */
	private JLabel lblCliente;
	/** Label para o nome do cliente. */
	private JLabel lblName;
	/** Label para o texto "Id:". */
	private JLabel lblId;
	/** Label para o ID do cliente. */
	private JLabel Id;
	/** Label para o texto "Nome:". */
	private JLabel lblNome;
	/** Label para o texto "Endereço:". */
	private JLabel lblEndereo;
	/** Label para o texto "Fone:". */
	private JLabel lblFone;
	/** Label para o texto "Email:". */
	private JLabel lblEmail;

	// Dependências
	/** DAO para operações relacionadas aos clientes. */
	private CentroClientesDAO dao = new CentroClientesDAO();
	/** Utilitário para manipulação de elementos da interface gráfica. */
	private final ScreenTools util = new ScreenTools();

	// Informações do Usuário
	/** ID do cliente. */
	private Integer id;
	/** Nome do cliente. */
	private String nome;
	/** Endereço do cliente. */
	private String endereco;
	/** Telefone do cliente. */
	private String fone;
	/** Email do cliente. */
	private String email;

	/**
	 * Construtor para inicializar os detalhes do cliente.
	 * 
	 * @param id       ID do cliente.
	 * @param nome     Nome do cliente.
	 * @param fone     Telefone do cliente.
	 * @param endereco Endereço do cliente.
	 * @param email    Email do cliente.
	 */
	public DetalhesCliente(Integer id, String nome, String fone, String endereco, String email) {
		setUpInfoClient(id, nome, fone, endereco, email);
		configurarFrame();
		adicionarComponentes();
		ConfigureListeners();
	}

	/**
	 * Configura os ouvintes de eventos (listeners) da tela.
	 */
	private void ConfigureListeners() {

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					fecharJanela();
				}
			}
		});
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		// Ouvinte para o botão Excluir
		btnExcluir.addActionListener(e -> {
			int confirmacao = JOptionPane.showConfirmDialog(null,
					"Tem certeza de que deseja excluir este Cliente?\nTodas as ordem e serviços relacioanadas serão apagadas",
					"Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirmacao == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
				dao.deletarCliente(id);
				this.dispose();
			}
		});

		// Ouvinte para o campo de telefone para formatação
		foneField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				util.formatarCelular(foneField); // Formata o celular quando o texto é inserido
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		// Ouvinte para o botão Salvar e Sair
		btnSalvarESair.addActionListener(e -> {
			fecharJanela();
		});

		// Ouvinte para o evento de fechar a janela
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		// Ouvinte para o campo de endereço
		enderecoField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (enderecoField.getText().isEmpty()) {
					enderecoField.setText("Não informado");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (enderecoField.getText().equals("Não informado")) {
					enderecoField.setText("");
				}
			}
		});

		// Ouvinte para o campo de email
		emailField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (emailField.getText().isEmpty()) {
					emailField.setText("Não informado");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (emailField.getText().equals("Não informado")) {
					emailField.setText("");
				}
			}
		});

	}

	/**
	 * Inicializa as informações do cliente.
	 * 
	 * @param id       ID do cliente
	 * @param nome     Nome do cliente
	 * @param fone     Telefone do cliente
	 * @param endereco Endereço do cliente
	 * @param email    Email do cliente
	 */
	private void setUpInfoClient(Integer id, String nome, String fone, String endereco, String email) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.fone = fone;
		this.email = email;
	}

	/**
	 * Configura o frame da janela com título, ícone e outras configurações.
	 */
	private void configurarFrame() {
		setIconImage(util.getLogo());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 540);
		setLocationRelativeTo(null);
		setTitle("Informações do Cliente");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(util.getBackgroundColor());
		contentPane.setLayout(null);
		setContentPane(contentPane);
	}

	/**
	 * Adiciona os componentes à interface gráfica.
	 */
	private void adicionarComponentes() {

		// Título
		lblTitle = new JLabel("Informações do Cliente");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 35));
		lblTitle.setBounds((this.getWidth() - 391) / 2, 11, 391, 47);
		contentPane.add(lblTitle);

		// Foto do usuário
		fotoUser = new JLabel("");
		fotoUser.setIcon(util.mudarTamanhoImg("/br/com/SistemaOS/Icones/icon/homem-usuario.png", 200, 200));
		fotoUser.setHorizontalAlignment(SwingConstants.CENTER);
		fotoUser.setForeground(Color.WHITE);
		fotoUser.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 35));
		fotoUser.setBounds(10, 57, 229, 207);
		contentPane.add(fotoUser);

		// Labels
		lblCliente = new JLabel("Cliente:");
		lblCliente.setForeground(Color.WHITE);
		lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 33));
		lblCliente.setBounds(235, 92, 145, 47);
		contentPane.add(lblCliente);

		lblName = new JLabel("<dynamic>");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblName.setBounds(235, 136, 759, 47);
		lblName.setText(nome);
		contentPane.add(lblName);

		lblId = new JLabel("Id:");
		lblId.setForeground(Color.WHITE);
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 33));
		lblId.setBounds(235, 185, 45, 47);
		contentPane.add(lblId);

		Id = new JLabel("<dynamic>");
		Id.setForeground(Color.WHITE);
		Id.setFont(new Font("Segoe UI", Font.BOLD, 27));
		Id.setBounds(278, 187, 402, 47);
		Id.setText(id.toString());
		contentPane.add(Id);

		lblNome = new JLabel("Nome:");
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblNome.setBounds(87, 297, 89, 35);
		contentPane.add(lblNome);

		lblEndereo = new JLabel("Endereço:");
		lblEndereo.setForeground(Color.WHITE);
		lblEndereo.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblEndereo.setBounds(54, 372, 122, 35);
		contentPane.add(lblEndereo);

		lblFone = new JLabel("Fone:");
		lblFone.setForeground(Color.WHITE);
		lblFone.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblFone.setBounds(519, 297, 64, 35);
		contentPane.add(lblFone);

		lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblEmail.setBounds(519, 372, 75, 35);
		contentPane.add(lblEmail);

		enderecoField = new JTextField();
		util.estilizarField(enderecoField, endereco);
		enderecoField.setBounds(175, 372, 322, 35);
		contentPane.add(enderecoField);

		nomeField = new JTextField();
		util.estilizarField(nomeField, nome);
		nomeField.setBounds(175, 297, 322, 35);
		contentPane.add(nomeField);

		foneField = new JTextField();
		util.estilizarField(foneField, fone);
		foneField.setBounds(593, 297, 322, 35);
		contentPane.add(foneField);

		emailField = new JTextField();
		util.estilizarField(emailField, email);
		emailField.setBounds(593, 372, 322, 35);
		contentPane.add(emailField);

		btnSalvarESair = new JButton("Salvar e Sair");
		btnSalvarESair.setFocusPainted(false);
		util.estilizarBotao(btnSalvarESair);
		btnSalvarESair.setBounds((this.getWidth() - 120) / 2, 456, 120, 30);
		contentPane.add(btnSalvarESair);

		btnExcluir = new JButton("Excluir");
		util.estilizarBotao(btnExcluir);
		btnExcluir.setFocusPainted(false);
		btnExcluir.setBounds(235, 243, 100, 30);
		contentPane.add(btnExcluir);

	}

	/**
	 * Fecha a janela após salvar as modificações no cliente.
	 */
	public void fecharJanela() {
		String nome = nomeField.getText();
		String endereco = enderecoField.getText();
		String fone = foneField.getText();
		String email = emailField.getText();

		if (isCampoVazio(nome, fone)) {
			JOptionPane.showMessageDialog(contentPane, "Nome e Telefone não podem ser vazios.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		dao.updateCliente(nome, endereco, fone, email, id);

		dispose();
	}

	/**
	 * Verifica se algum campo está vazio.
	 * 
	 * @param campos Campos a serem verificados
	 * @return true se algum campo estiver vazio, false caso contrário
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
