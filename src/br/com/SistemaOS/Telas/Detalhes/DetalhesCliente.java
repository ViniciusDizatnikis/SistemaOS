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

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.Utils.ScreenTools;

public class DetalhesCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField enderecoField;
	private JTextField nomeField;
	private JTextField foneField;
	private JTextField emailField;

	private CentroClientesDAO dao = new CentroClientesDAO();
	private final ScreenTools util = new ScreenTools();

	private Integer id;
	private String nome;
	private String endereco;
	private String fone;
	private String email;

	public DetalhesCliente(Integer id, String nome, String fone, String endereco, String email) {
		setUpInfoClient(id, nome, fone, endereco, email);
		configurarFrame();
		adicionarComponentes();
	}

	private void setUpInfoClient(Integer id, String nome, String fone, String endereco, String email) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.fone = fone;
		this.email = email;
	}

	private void configurarFrame() {
		setIconImage(util.getLogo());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 540);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(util.getBackgroundColor());
		contentPane.setLayout(null);

		setContentPane(contentPane);
	}

	private void adicionarComponentes() {

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

		JLabel lblTitle = new JLabel("Informações do Cliente");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 35));
		lblTitle.setBounds((this.getWidth() - 391) / 2, 11, 391, 47);
		contentPane.add(lblTitle);

		JLabel fotoUser = new JLabel("");
		fotoUser.setIcon(util.mudarTamanhoImg("/br/com/SistemaOS/Icones/icon/homem-usuario.png", 200, 200));
		fotoUser.setHorizontalAlignment(SwingConstants.CENTER);
		fotoUser.setForeground(Color.WHITE);
		fotoUser.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 35));
		fotoUser.setBounds(10, 57, 229, 207);
		contentPane.add(fotoUser);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setForeground(Color.WHITE);
		lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 33));
		lblCliente.setBounds(235, 92, 145, 47);
		contentPane.add(lblCliente);

		JLabel lblName = new JLabel("<dynamic>");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblName.setBounds(235, 136, 759, 47);
		lblName.setText(nome);
		contentPane.add(lblName);

		JLabel lblId = new JLabel("Id:");
		lblId.setForeground(Color.WHITE);
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 33));
		lblId.setBounds(235, 185, 45, 47);
		contentPane.add(lblId);

		JLabel Id = new JLabel("<dynamic>");
		Id.setForeground(Color.WHITE);
		Id.setFont(new Font("Segoe UI", Font.BOLD, 27));
		Id.setBounds(278, 187, 402, 47);
		Id.setText(id.toString());
		contentPane.add(Id);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblNome.setBounds(87, 297, 89, 35);
		contentPane.add(lblNome);

		JLabel lblEndereo = new JLabel("Endereço:");
		lblEndereo.setForeground(Color.WHITE);
		lblEndereo.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblEndereo.setBounds(54, 372, 122, 35);
		contentPane.add(lblEndereo);

		JLabel lblFone = new JLabel("Fone:");
		lblFone.setForeground(Color.WHITE);
		lblFone.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblFone.setBounds(519, 297, 64, 35);
		contentPane.add(lblFone);

		JLabel lblEmail = new JLabel("Email:");
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

		JButton btnSalvarESair = new JButton("Salvar e Sair");
		btnSalvarESair.setFocusPainted(false);
		util.estilizarBotao(btnSalvarESair);
		btnSalvarESair.setBounds((this.getWidth() - 120) / 2, 456, 120, 30);
		contentPane.add(btnSalvarESair);

		JButton btnExcluir = new JButton("Excluir");
		util.estilizarBotao(btnExcluir);
		btnExcluir.setFocusPainted(false);
		btnExcluir.setBounds(235, 243, 100, 30);
		contentPane.add(btnExcluir);

		btnExcluir.addActionListener(e -> {
			int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir este Cliente?",
					"Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirmacao == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
				dao.deletarCliente(id);
				this.dispose();
			}
		});

		btnSalvarESair.addActionListener(e -> {
			fecharJanela();
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fecharJanela();
			}
		});

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

	private boolean isCampoVazio(String... campos) {
		for (String campo : campos) {
			if (campo == null || campo.trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}
}
