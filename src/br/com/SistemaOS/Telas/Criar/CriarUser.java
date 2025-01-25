package br.com.SistemaOS.Telas.Criar;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.Utils.UtilitariosTela;

public class CriarUser extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private UtilitariosTela util;
    private JTextField fieldNome;
    private JLabel lblPerfil;
    private JTextField fieldFone;
    private JTextField fieldLogin;
    private JTextField fieldSenha;
    private CentroUsuariosDAO dao = new CentroUsuariosDAO();

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
        this.util = new UtilitariosTela();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1020, 540);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(util.getBackgroundColor()); 
        contentPane.setLayout(null); 
        setContentPane(contentPane);

        JLabel lblCriarUsuario = new JLabel("Criar Usuário");
        lblCriarUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblCriarUsuario.setForeground(new Color(255, 255, 255));
        lblCriarUsuario.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 36));
        lblCriarUsuario.setBounds(0, 20, 1004, 50); 
        contentPane.add(lblCriarUsuario);

        // Label e campo de Nome
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setForeground(Color.WHITE);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblNome.setBounds(50, 100, 122, 30);
        contentPane.add(lblNome);

        fieldNome = new JTextField();
        fieldNome.setBounds(50, 140, 490, 35);
        fieldNome.setBorder(new LineBorder(Color.GRAY, 1, true)); 
        contentPane.add(fieldNome);
        fieldNome.setColumns(10);

        // Label e ComboBox de Perfil
        lblPerfil = new JLabel("Perfil:");
        lblPerfil.setForeground(Color.WHITE);
        lblPerfil.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPerfil.setBounds(550, 100, 122, 30);
        contentPane.add(lblPerfil);

        JComboBox<String> cbPerfil = new JComboBox<>(new String[] { "User", "Admin" });
        cbPerfil.setBounds(550, 140, 200, 35);
        cbPerfil.setBorder(new LineBorder(Color.GRAY, 1, true));
        contentPane.add(cbPerfil);

        // Label e campo de Telefone
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setForeground(Color.WHITE);
        lblTelefone.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTelefone.setBounds(50, 200, 146, 30);
        contentPane.add(lblTelefone);

        fieldFone = new JTextField();
        fieldFone.setBounds(50, 240, 490, 35);
        fieldFone.setBorder(new LineBorder(Color.GRAY, 1, true));
        contentPane.add(fieldFone);
        fieldFone.setColumns(10);

        // Label e campo de Login
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setForeground(Color.WHITE);
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLogin.setBounds(50, 300, 95, 30);
        contentPane.add(lblLogin);

        fieldLogin = new JTextField();
        fieldLogin.setBounds(50, 340, 490, 35);
        fieldLogin.setBorder(new LineBorder(Color.GRAY, 1, true));
        contentPane.add(fieldLogin);
        fieldLogin.setColumns(10);

        // Label e campo de Senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(Color.WHITE);
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblSenha.setBounds(550, 300, 146, 30);
        contentPane.add(lblSenha);

        fieldSenha = new JTextField();
        fieldSenha.setBounds(550, 340, 400, 35);
        fieldSenha.setBorder(new LineBorder(Color.GRAY, 1, true));
        contentPane.add(fieldSenha);
        fieldSenha.setColumns(10);

        // Botão Criar estilizado
        JButton btnCriar = new JButton("Criar");
        btnCriar.setBounds(460, 420, 100, 40);
        btnCriar.setBackground(new Color(100, 149, 237));
        btnCriar.setForeground(Color.WHITE);
        btnCriar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnCriar.setBorder(new LineBorder(new Color(30, 144, 255), 2, true)); 
        contentPane.add(btnCriar);

        // Lógica para o botão Criar
        btnCriar.addActionListener(e -> {
            String nome = fieldNome.getText().trim();
            String fone = fieldFone.getText().trim();
            String login = fieldLogin.getText().trim();
            String senha = fieldSenha.getText().trim();
            String perfil = cbPerfil.getSelectedItem().toString().toLowerCase();
            
            System.out.println(perfil);
            
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

    // Método para criar o usuário
    public void criarUsuario(String nome, String fone, String login, String senha, String perfil) {
        dao.criarUsuario(nome, fone, login, senha, perfil);
    }

    // Função para verificar se algum campo é nulo ou vazio
    private boolean isCampoVazio(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true; // Retorna true se encontrar um campo vazio ou nulo
            }
        }
        return false; // Retorna false se todos os campos forem preenchidos
    }
}
