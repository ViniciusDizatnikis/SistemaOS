package br.com.SistemaOS.Telas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.Utils.UtilitariosTela;
import br.com.SistemaOS.modelo.Usuario;
import java.util.logging.*;

public class TelaLogin extends JFrame {

    private static final long serialVersionUID = 1L;

    // Constantes
    private static final int CAMPO_LARGURA = 300;
    private static final int CAMPO_ALTURA = 40;
    private static final Color INPUT_COLOR = new Color(80, 80, 80);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color ERROR_COLOR = new Color(231, 76, 60);
    private static final Font DEFAULT_FONT = new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16);

    // Componentes
    private JPanel contentPane;
    private JTextField txtNome;
    private JPasswordField txtPassword;
    private JLabel lblStatus;
    private Timer statusTimer;
    private JButton btnLogin;

    // Dependências
    private final CentroUsuariosDAO telaDAO = new CentroUsuariosDAO();
    private final UtilitariosTela util = new UtilitariosTela();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaLogin() {
        configurarJanela();
        inicializarComponentes();
        configurarEventos();
    }

    private void configurarJanela() {
        setTitle("Login - SOS");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/SistemaOS/Icones/icon/Logo.png")));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1013, 531);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        contentPane = criarPainelPrincipal();
        adicionarLogo();
        adicionarTitulos();
        txtNome = adicionarCampoTexto("Usuário:", 161, 191);
        txtPassword = adicionarCampoSenha("Senha:", 251, 281);
        btnLogin = adicionarBotaoLogin();
        lblStatus = adicionarLabelStatus();
        atualizarStatus();
        setContentPane(contentPane);
        getRootPane().setDefaultButton(btnLogin);
    }

    private JPanel criarPainelPrincipal() {
        JPanel painel = new JPanel();
        painel.setLayout(null);
        painel.setBackground(util.getBackgroundColor());
        
        JLabel lblByVinicius = new JLabel("by vinicius", SwingConstants.CENTER);
        lblByVinicius.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblByVinicius.setForeground(Color.LIGHT_GRAY);
        lblByVinicius.setBounds(centralizarComponente(100), getHeight() - 80, 100, 20); // Centralizando horizontalmente
        painel.add(lblByVinicius);
        return painel;
    }

    private void adicionarLogo() {
        JLabel logoImagem = new JLabel(util.mudarTamanhoImg("/br/com/SistemaOS/Icones/icon/Logo.png", 90, 90));
        logoImagem.setBounds(centralizarComponente(80), 0, 80, 80);
        contentPane.add(logoImagem);
    }

    private void adicionarTitulos() {
        adicionarTitulo("QuickFix Pro", 71, 22);
        adicionarTitulo("Sistema de Ordem e Serviço", 96, 15);
    }

    private void adicionarTitulo(String texto, int posY, int fontSize) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, fontSize));
        label.setForeground(Color.WHITE);
        label.setBounds(centralizarComponente(CAMPO_LARGURA), posY, CAMPO_LARGURA, 40);
        contentPane.add(label);
    }

    private JTextField adicionarCampoTexto(String label, int labelPosY, int campoPosY) {
        return (JTextField) adicionarCampo(label, labelPosY, campoPosY, new JTextField());
    }

    private JPasswordField adicionarCampoSenha(String label, int labelPosY, int campoPosY) {
        return (JPasswordField) adicionarCampo(label, labelPosY, campoPosY, new JPasswordField());
    }

    private JComponent adicionarCampo(String labelTexto, int labelPosY, int campoPosY, JComponent campo) {
        JLabel label = new JLabel(labelTexto, SwingConstants.LEFT);
        label.setFont(DEFAULT_FONT);
        label.setForeground(Color.WHITE);
        label.setBounds(centralizarComponente(CAMPO_LARGURA), labelPosY, CAMPO_LARGURA, 30);
        contentPane.add(label);

        campo.setFont(DEFAULT_FONT);
        campo.setBounds(centralizarComponente(CAMPO_LARGURA), campoPosY, CAMPO_LARGURA, CAMPO_ALTURA);
        campo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        campo.setBackground(INPUT_COLOR);
        campo.setForeground(Color.WHITE);
        contentPane.add(campo);

        return campo;
    }

    private JButton adicionarBotaoLogin() {
        JButton botao = new JButton("Entrar");
        botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botao.setBackground(new Color(63, 182, 207));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        botao.setBounds(centralizarComponente(CAMPO_LARGURA), 345, CAMPO_LARGURA, CAMPO_ALTURA);
        contentPane.add(botao);
        return botao;
    }

    private JLabel adicionarLabelStatus() {
        JLabel status = new JLabel("", SwingConstants.CENTER);
        status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        status.setForeground(Color.WHITE);
        status.setBounds(centralizarComponente(CAMPO_LARGURA), 420, CAMPO_LARGURA, 30);
        contentPane.add(status);
        return status;
    }

    private int centralizarComponente(int larguraItem) {
        return (getWidth() - larguraItem) / 2;
    }

    private void configurarEventos() {
        configurarEventoBotaoLogin();
        configurarTimerStatus();
    }

    private void configurarEventoBotaoLogin() {
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

    private void configurarTimerStatus() {
        statusTimer = new Timer(5000, e -> atualizarStatus());
        statusTimer.start();
    }

    private void atualizarStatus() {
        boolean conectado = telaDAO.getStatus();
        String mensagem = conectado ? "Status: Conectado ao banco de dados" : "Status: Erro ao conectar ao banco de dados";
        Color cor = conectado ? SUCCESS_COLOR : ERROR_COLOR;

        lblStatus.setText(mensagem);
        lblStatus.setForeground(cor);
    }

    private void realizarLogin() {
        String username = txtNome.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            exibirMensagem("Por favor, preencha todos os campos.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario usuario = telaDAO.login(username, password);
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

    private void abrirTelaPrincipal(Usuario usuario) {
        TelaPrincipal telaPrincipal = new TelaPrincipal(usuario);
        CustomDialog carregandoDialog = new CustomDialog(this, "Carregando, por favor aguarde...", 2000);
        carregandoDialog.setVisible(true);

        telaPrincipal.setVisible(true);
        dispose();
    }

    private void exibirMensagem(String mensagem, int messageType) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", messageType);
    }
}
