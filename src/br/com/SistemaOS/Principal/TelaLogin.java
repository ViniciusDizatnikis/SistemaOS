package br.com.SistemaOS.Principal;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.Utils.ScreenTools;
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
    private final CentroUsuariosDAO dao = new CentroUsuariosDAO();
    private final ScreenTools util = new ScreenTools();

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
        setIconImage(util.getLogo());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1013, 531);
        setLocationRelativeTo(null);
    }

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
        btn.setBounds(centralizarComponente(CAMPO_LARGURA), 345, CAMPO_LARGURA, CAMPO_ALTURA);

        JLabel lblByVinicius = new JLabel("by vinicius", SwingConstants.CENTER);
        lblByVinicius.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblByVinicius.setForeground(Color.LIGHT_GRAY);
        lblByVinicius.setBounds(centralizarComponente(100), getHeight() - 80, 100, 20); 

        setContentPane(contentPane);
        getRootPane().setDefaultButton(btnLogin);
        atualizarStatus();
    }

    private JLabel createLabel(String texto, int posY, int fontSize) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, fontSize));
        label.setForeground(Color.WHITE);
        label.setBounds(centralizarComponente(CAMPO_LARGURA), posY, CAMPO_LARGURA, 40);
        return label;
    }

    private JTextField criarCampoTexto(String label, int labelPosY, int campoPosY) {
        JLabel lblUsuario = new JLabel(label, SwingConstants.LEFT);
        lblUsuario.setFont(DEFAULT_FONT);
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(centralizarComponente(CAMPO_LARGURA), labelPosY, CAMPO_LARGURA, 30);
        contentPane.add(lblUsuario);

        JTextField txtCampo = new JTextField();
        util.estilizarField(txtCampo, "");
        txtCampo.setBounds(centralizarComponente(CAMPO_LARGURA), campoPosY, CAMPO_LARGURA, CAMPO_ALTURA);
        txtCampo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return txtCampo;
    }

    private JPasswordField criarCampoSenha(String label, int labelPosY, int campoPosY) {
        JLabel lblSenha = new JLabel(label, SwingConstants.LEFT);
        lblSenha.setFont(DEFAULT_FONT);
        lblSenha.setForeground(Color.WHITE);
        lblSenha.setBounds(centralizarComponente(CAMPO_LARGURA), labelPosY, CAMPO_LARGURA, 30);
        contentPane.add(lblSenha);

        JPasswordField txtCampo = new JPasswordField();
        txtCampo.setFont(DEFAULT_FONT);
        txtCampo.setBounds(centralizarComponente(CAMPO_LARGURA), campoPosY, CAMPO_LARGURA, CAMPO_ALTURA);
        txtCampo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtCampo.setBackground(INPUT_COLOR);
        txtCampo.setForeground(Color.WHITE);
        return txtCampo;
    }

    private JButton criarBotaoLogin() {
        JButton btn = new JButton("Entrar");
        util.estilizarBotao(btn);
        btn.setBounds(centralizarComponente(CAMPO_LARGURA), 345, CAMPO_LARGURA, CAMPO_ALTURA);
        return btn;
    }

    private JLabel criarLabelStatus() {
        JLabel status = new JLabel("", SwingConstants.CENTER);
        status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        status.setForeground(Color.WHITE);
        status.setBounds(centralizarComponente(CAMPO_LARGURA), 420, CAMPO_LARGURA, 30);
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
        statusTimer = new Timer(4000, e -> atualizarStatus());
        statusTimer.start();
    }

    private void atualizarStatus() {
        boolean conectado = dao.getStatus();
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






