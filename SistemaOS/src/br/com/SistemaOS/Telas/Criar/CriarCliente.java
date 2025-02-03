package br.com.SistemaOS.Telas.Criar;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

public class CriarCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private ScreenTools util = new ScreenTools();
	private CentroClientesDAO dao = new CentroClientesDAO();

	private JTextField fieldNome;
	private JTextField fieldEndereco;
	private JTextField fieldTelefone;
	private JTextField fieldEmail;

	public CriarCliente() {
		configurarFrame();
		adicionarTitulo();
		adicionarCamposTexto();
		adicionarBotaoCriar();
	}

	private void configurarFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 540);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(util.getBackgroundColor());
		contentPane.setLayout(null);
		setContentPane(contentPane);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}
		});
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

	}

	private void adicionarTitulo() {
		JLabel lblCriarCliente = new JLabel("Criar Cliente");
		lblCriarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblCriarCliente.setForeground(Color.WHITE);
		lblCriarCliente.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 36));
		lblCriarCliente.setBounds(0, 20, 1004, 50);
		contentPane.add(lblCriarCliente);
	}

	private void adicionarCamposTexto() {
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNome.setBounds(54, 181, 150, 30);
		contentPane.add(lblNome);

		fieldNome = new JTextField();
		util.estilizarField(fieldNome, "");
		fieldNome.setBounds(54, 221, 440, 35);
		contentPane.add(fieldNome);

		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setForeground(Color.WHITE);
		lblEndereco.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblEndereco.setBounds(54, 281, 150, 30);
		contentPane.add(lblEndereco);

		fieldEndereco = new JTextField();
		util.estilizarField(fieldEndereco, "");
		fieldEndereco.setBounds(54, 321, 440, 35);
		contentPane.add(fieldEndereco);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setForeground(Color.WHITE);
		lblTelefone.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTelefone.setBounds(504, 181, 150, 30);
		contentPane.add(lblTelefone);

		fieldTelefone = new JTextField();
		util.estilizarField(fieldTelefone, "");
		fieldTelefone.setBounds(504, 221, 440, 35);
		contentPane.add(fieldTelefone);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblEmail.setBounds(504, 281, 150, 30);
		contentPane.add(lblEmail);

		fieldEmail = new JTextField();
		util.estilizarField(fieldEmail, "");
		fieldEmail.setBounds(504, 321, 440, 35);
		contentPane.add(fieldEmail);
	}

	private void adicionarBotaoCriar() {
		JButton btnCriar = new JButton("Criar");
		util.estilizarBotao(btnCriar);
		btnCriar.setBounds(460, 420, 100, 40);
		contentPane.add(btnCriar);
		btnCriar.addActionListener(e -> validarEProcessarCadastro());
	}

	private void validarEProcessarCadastro() {
		String nome = fieldNome.getText().trim();
		String endereco = fieldEndereco.getText().trim();
		String telefone = fieldTelefone.getText().trim();
		String email = fieldEmail.getText().trim();

		endereco = endereco.isEmpty() ? "" : endereco;
		email = email.isEmpty() ? "" : email;

		if (isCampoVazio(nome, telefone)) {
			JOptionPane.showMessageDialog(this, "Nome e Telefone não podem ser vazios.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		criarCliente(nome, endereco, telefone, email);
		JOptionPane.showMessageDialog(this, "Cliente criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}

	private void criarCliente(String nome, String endereco, String telefone, String email) {
		dao.criarCliente(nome, endereco, telefone, email);
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
