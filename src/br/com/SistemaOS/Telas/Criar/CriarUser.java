package br.com.SistemaOS.Telas.Criar;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.Utils.ScreenTools;

public class CriarUser extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField fieldNome;
    private JLabel lblPerfil;
    private JTextField fieldFone;
    private JTextField fieldLogin;
    private JTextField fieldSenha;
    private CentroUsuariosDAO dao = new CentroUsuariosDAO();
    private ScreenTools util = new ScreenTools();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CriarUser frame = new CriarUser();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CriarUser() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1020, 540);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(util.getBackgroundColor()); 
        contentPane.setLayout(null); 
        setContentPane(contentPane);

        JLabel lblCriarUsuario = util.criarLabel("Criar Usuário", 0, 20, 1004, 50, 36, true);
        contentPane.add(lblCriarUsuario);

        JLabel lblNome = util.criarLabel("Nome:", 50, 100, 122, 30, 20, false);
        contentPane.add(lblNome);

        fieldNome = new JTextField();
        util.estilizarField(fieldNome, "");
        fieldNome.setBounds(50, 140, 490, 35);
        contentPane.add(fieldNome);

        lblPerfil = util.criarLabel("Perfil:", 550, 100, 122, 30, 20, false);
        contentPane.add(lblPerfil);

        JComboBox<String> cbPerfil = new JComboBox<>(new String[] { "User", "Admin" });
        cbPerfil.setBounds(550, 140, 200, 35);
        cbPerfil.setBorder(new LineBorder(Color.GRAY, 1, true));
        contentPane.add(cbPerfil);

        JLabel lblTelefone = util.criarLabel("Telefone:", 50, 200, 146, 30, 20, false);
        contentPane.add(lblTelefone);

        fieldFone = new JTextField();
        util.estilizarField(fieldFone, "");
        fieldFone.setBounds(50, 240, 490, 35);
        contentPane.add(fieldFone);

        JLabel lblLogin = util.criarLabel("Login:", 50, 300, 95, 30, 20, false);
        contentPane.add(lblLogin);

        fieldLogin = new JTextField();
        util.estilizarField(fieldLogin, "");
        fieldLogin.setBounds(50, 340, 490, 35);
        contentPane.add(fieldLogin);

        JLabel lblSenha = util.criarLabel("Senha:", 550, 300, 146, 30, 20, false);
        contentPane.add(lblSenha);

        fieldSenha = new JTextField();
        util.estilizarField(fieldSenha, "");
        fieldSenha.setBounds(550, 340, 400, 35);
        contentPane.add(fieldSenha);

        JButton btnCriar = new JButton("Criar");
        util.estilizarBotao(btnCriar);
        btnCriar.setBounds(460, 420, 100, 40);
        contentPane.add(btnCriar);

        btnCriar.addActionListener(e -> {
            String nome = fieldNome.getText().trim();
            String fone = fieldFone.getText().trim();
            String login = fieldLogin.getText().trim();
            String senha = fieldSenha.getText().trim();
            String perfil = cbPerfil.getSelectedItem().toString().toLowerCase();
            
            if (isCampoVazio(nome, fone, login, senha)) {
            	JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            	return;
            }

            if (dao.loginExistente(login)) {
            	 JOptionPane.showMessageDialog(null, "O login já está em uso!", "Erro", JOptionPane.ERROR_MESSAGE);
            	 return;
            }

            criarUsuario(nome, fone, login, senha, perfil);
            JOptionPane.showMessageDialog(this, "Usuário criado com sucesso!");
            this.dispose();
        });
    }

    public void criarUsuario(String nome, String fone, String login, String senha, String perfil) {
        dao.criarUsuario(nome, fone, login, senha, perfil);
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
