package br.com.SistemaOS.Telas.Detalhes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.Usuario;

import javax.swing.JPasswordField;

public class DetalhesUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	// Outras classes
	private CentroUsuariosDAO dao = new CentroUsuariosDAO();
	private ScreenTools util = new ScreenTools();

	// Inforção do Usuario
	private Integer idUser;
	private String nomeUser;
	private String foneUser;
	private String loginUser;
	private String senhaUser;
	private String perfilUser;
	private Usuario user;

	// Botões e fields
	private JLabel nomeUsuarioCard;
	private JButton btnEditar, btnCancelar, btnSalvar, btnOk, btnDeletar;
	private JTextField txtNome, txtFone, txtLogin;
	private JPasswordField pwSenha;
	private JComboBox<String> cbPerfil;

	private JPanel contentPane;

	public DetalhesUsuario(int id, String nome, String fone, String login, String senha, String perfil, Usuario usu) {
		setUpFrame();

		initComponents(id, nome, fone, login, senha, perfil, usu);
	}

	private void setUpFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 540);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(util.getLogo());

		// Painel Principal
		contentPane = new JPanel();
		contentPane.setBackground(util.getBackgroundColor());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
	}

	private void initComponents(Integer id, String nome, String fone, String login, String senha, String perfil,
			Usuario usu) {

		getUserData(id, nome, fone, login, senha, perfil, usu);

		initLabels();

		initFields();

		initBtns();

		btnDeletar.addActionListener(e -> deletarUsuario());

		btnOk.addActionListener(e -> this.dispose());

		btnSalvar.addActionListener(e -> salvarUsuario());

		btnEditar.addActionListener(e -> modoEdicao(true));

		btnCancelar.addActionListener(e -> modoEdicao(false));

		// configuração da janela
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (btnSalvar.isVisible()) {
					JOptionPane.showMessageDialog(null, "Finalize o modo de edição antes de fechar!");
				} else {
					dispose();
				}
			}
		});

	}

	private void getUserData(Integer id, String nome, String fone, String login, String senha, String perfil,
			Usuario usu) {
		this.user = usu;
		this.idUser = id;
		this.nomeUser = nome;
		this.foneUser = fone;
		this.loginUser = login;
		this.senhaUser = senha;
		this.perfilUser = perfil;
	}

	private void initBtns() {
		btnDeletar = new JButton("Excluir");
		btnDeletar.setBounds(354, 234, 100, 30);
		contentPane.add(btnDeletar);

		btnOk = new JButton("Fechar");
		btnOk.setHorizontalAlignment(SwingConstants.CENTER);
		btnOk.setBounds(440, 460, 100, 30);
		contentPane.add(btnOk);

		btnEditar = new JButton("Editar");
		btnEditar.setToolTipText("Editar informações do Usuario");
		btnEditar.setBounds(244, 234, 100, 30);
		contentPane.add(btnEditar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(354, 234, 100, 30);
		btnCancelar.setVisible(false);
		contentPane.add(btnCancelar);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(244, 234, 100, 30);
		btnSalvar.setVisible(false);
		contentPane.add(btnSalvar);
	}

	private void initLabels() {
		contentPane.add(util.criarLabel("Informações do Usuário", (this.getWidth() - 400) / 2, 0, 400, 52, 35, true));
		contentPane.add(util.criarLabel("Nome:", 48, 284, 100, 30, 20, false));
		contentPane.add(util.criarLabel("Fone:", 48, 334, 100, 30, 20, false));
		contentPane.add(util.criarLabel("Login:", 498, 284, 100, 30, 20, false));
		contentPane.add(util.criarLabel("Perfil:", 48, 390, 100, 30, 20, false));
		contentPane.add(util.criarLabel("Senha:", 498, 334, 100, 30, 20, false));
		contentPane.add(util.criarLabel("ID:", 244, 177, 50, 46, 25, false));
		contentPane.add(util.criarLabel("Usuario:", 244, 76, 521, 52, 35, false));
		contentPane.add(util.criarLabel(idUser.toString(), 284, 180, 429, 40, 25, false));
		nomeUsuarioCard = util.criarLabel(nomeUser, 244, 128, 600, 52, 35, false);
		contentPane.add(nomeUsuarioCard);

		JLabel fotoUser = new JLabel("");
		fotoUser.setIcon(util.mudarTamanhoImg("/br/com/SistemaOS/Icones/icon/homem-usuario.png", 200, 200));
		fotoUser.setHorizontalAlignment(SwingConstants.CENTER);
		fotoUser.setForeground(Color.WHITE);
		fotoUser.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 35));
		fotoUser.setBounds(10, 57, 253, 207);
		contentPane.add(fotoUser);
	}

	private void initFields() {
		txtNome = new JTextField(nomeUser);
		txtNome.setEnabled(false);
		txtNome.setBounds(148, 284, 300, 30);
		contentPane.add(txtNome);

		txtFone = new JTextField(foneUser);
		txtFone.setEnabled(false);
		txtFone.setBounds(148, 334, 300, 30);
		contentPane.add(txtFone);

		cbPerfil = new JComboBox<>(new String[] { "Admin", "User" });
		cbPerfil.setSelectedItem(formatarPerfil(perfilUser));
		cbPerfil.setEnabled(false);
		cbPerfil.setBounds(148, 390, 300, 30);
		contentPane.add(cbPerfil);

		txtLogin = new JTextField(loginUser);
		txtLogin.setEnabled(false);
		txtLogin.setBounds(598, 284, 300, 30);
		contentPane.add(txtLogin);

		pwSenha = new JPasswordField();
		pwSenha.setBounds(598, 334, 300, 30);
		pwSenha.setEnabled(false);
		pwSenha.setText(senhaUser);
		contentPane.add(pwSenha);

	}

	private void modoEdicao(boolean value) {
		if (value) {
			if (nomeUser.equalsIgnoreCase("administrador")) {
				if (user.getNome().equalsIgnoreCase("administrador")) {
					btnDeletar.setVisible(false);
					btnOk.setEnabled(false);
					btnEditar.setVisible(false);
					btnCancelar.setVisible(true);
					btnSalvar.setVisible(true);
					txtNome.setEnabled(false);
					txtFone.setEnabled(false);
					txtLogin.setEnabled(false);
					cbPerfil.setEnabled(false);
					pwSenha.setEnabled(true);
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				} else {
					JOptionPane.showMessageDialog(null, "Apenas o administrador pode alterar suas informações", "Erro",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else {
				btnDeletar.setVisible(false);
				btnOk.setEnabled(false);
				btnEditar.setVisible(false);
				btnCancelar.setVisible(true);
				btnSalvar.setVisible(true);
				txtNome.setEnabled(true);
				txtFone.setEnabled(true);
				txtLogin.setEnabled(true);
				cbPerfil.setEnabled(true);
				pwSenha.setEnabled(true);
				setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			}
		} else {
			btnDeletar.setVisible(true);
			btnOk.setEnabled(true);
			btnEditar.setVisible(true);
			btnCancelar.setVisible(false);
			btnSalvar.setVisible(false);
			txtNome.setEnabled(false);
			txtNome.setText(nomeUser);
			txtFone.setEnabled(false);
			txtFone.setText(foneUser);
			cbPerfil.setEnabled(false);
			cbPerfil.setSelectedItem(formatarPerfil(perfilUser));
			txtLogin.setEnabled(false);
			txtLogin.setText(loginUser);
			pwSenha.setEnabled(false);
			pwSenha.setText(senhaUser);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
	}

	private void deletarUsuario() {
		if (idUser.equals(1)) {
			JOptionPane.showMessageDialog(null, "Você não pode deletar este Usuário Por questão de segurança!", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir este usuário?",
				"Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirmacao == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
			dao.deleteUser(idUser);
			this.dispose();
		}
	}

	private void salvarUsuario() {
		StringBuilder camposVazios = new StringBuilder();
		ArrayList<Object> update = new ArrayList<>();

		// Verificar nome
		if (txtNome.getText().isEmpty()) {
			camposVazios.append("- Nome vazio\n");
		} else if (!nomeUser.equals(txtNome.getText())) {
			update.add(txtNome.getText());
		} else {
			update.add(null);
		}

		// Verificar telefone
		if (txtFone.getText().isEmpty()) {
			camposVazios.append("- Telefone vazio\n");
		} else if (!foneUser.equals(txtFone.getText())) {
			update.add(txtFone.getText());
		} else {
			update.add(null);
		}

		// Verificar login
		if (txtLogin.getText().isEmpty()) {
			camposVazios.append("- Login vazio\n");
		} else if (!loginUser.equals(txtLogin.getText())) {
			update.add(txtLogin.getText());
		} else {
			update.add(null);
		}

		// Verificar senha
		String novaSenha = new String(pwSenha.getPassword());
		if (novaSenha.isEmpty()) {
			camposVazios.append("- Senha vazia\n");
		} else if (!novaSenha.equals(senhaUser)) {
			update.add(novaSenha);
		} else {
			update.add(null);
		}

		// Verificar perfil
		String perfilSelecionado = cbPerfil.getSelectedItem().toString().toLowerCase();
		if (!perfilUser.equals(perfilSelecionado)) {
			if (user.getId().equals(idUser)) {
				int confirmacao = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja mudar o perfil?\nVocê perderá suas permissões.", "Confirmação",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (confirmacao == JOptionPane.YES_OPTION) {
					update.add(perfilSelecionado);
				} else {
					cbPerfil.setSelectedItem("Admin");
					return;
				}
			} else {
				update.add(perfilSelecionado);
			}
		} else {
			update.add(null);
		}

		// Exibir mensagem de erro se houver campos vazios
		if (camposVazios.length() > 0) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha os seguintes campos:\n" + camposVazios, "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Verificar se o login já existe no banco de dados
		if (update.get(2) != null) {
			if (dao.loginExistente(update.get(2))) {
				JOptionPane.showMessageDialog(null, "Este login já está em uso!", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

		}

		// Atualizar os dados no banco de dados
		if (update.stream().allMatch(Objects::isNull)) {
			JOptionPane.showMessageDialog(null, "Nenhuma alteração realizada!", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			modoEdicao(false);
		} else {
			dao.updateUser(update.get(0), update.get(1), update.get(2), update.get(3), update.get(4), idUser);

			// Atualizar os valores locais se houver alterações
			if (update.get(0) != null)
				nomeUser = update.get(0).toString();
			if (update.get(1) != null)
				foneUser = update.get(1).toString();
			if (update.get(2) != null)
				loginUser = update.get(2).toString();
			if (update.get(3) != null)
				senhaUser = update.get(3).toString();
			if (update.get(4) != null)
				perfilUser = update.get(4).toString();

			// Atualizar o nome no cartão
			nomeUsuarioCard.setText(nomeUser);

			// Notificar o usuário de sucesso e reiniciar o sistema se necessário
			if (user.getId().equals(idUser) && !perfilUser.equalsIgnoreCase("admin")) {
				JOptionPane.showMessageDialog(null,
						"Alteração de perfil realizada com sucesso.\nO sistema será reiniciado para aplicar as alterações.",
						"Sucesso", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0); // Reiniciar o sistema
			}

			JOptionPane.showMessageDialog(null, "Usuário Alterado com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
			modoEdicao(false);
		}
	}

	private String formatarPerfil(String perfil) {
		if (perfil == null || perfil.isEmpty()) {
			return perfil;
		}

		// Verifica se a primeira letra é maiúscula
		char primeiraLetra = perfil.charAt(0);
		if (!Character.isUpperCase(primeiraLetra)) {
			perfil = Character.toUpperCase(primeiraLetra) + perfil.substring(1);
		}
		return perfil;
	}

}
