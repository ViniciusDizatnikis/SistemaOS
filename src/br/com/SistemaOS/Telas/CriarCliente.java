package br.com.SistemaOS.Telas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.Utils.UtilitariosTela;

public class CriarCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private UtilitariosTela util;
    private JTextField fieldNome;
    private JTextField fieldEndereco;
    private JTextField fieldTelefone;
    private JTextField fieldEmail;
    private CentroClientesDAO dao = new CentroClientesDAO();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CriarCliente frame = new CriarCliente();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CriarCliente() {
        configurarJanela();
        adicionarComponentes();
    }

    private void configurarJanela() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1020, 540);
        setLocationRelativeTo(null);
        util = new UtilitariosTela();

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(util.getBackgroundColor());
        contentPane.setLayout(null);
        setContentPane(contentPane);
    }

    private void adicionarComponentes() {
        adicionarTitulo();
        adicionarCamposTexto();
        adicionarBotaoCriar();
        adicionarLabelAssinatura();
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
        JLabel lblNome = criarLabel("Nome:", 54, 181);
        contentPane.add(lblNome);

        fieldNome = criarCampoTexto(54, 221);
        contentPane.add(fieldNome);

        JLabel lblEndereco = criarLabel("Endereço:", 54, 281);
        contentPane.add(lblEndereco);

        fieldEndereco = criarCampoTexto(54, 321);
        contentPane.add(fieldEndereco);

        JLabel lblTelefone = criarLabel("Telefone:", 504, 181);
        contentPane.add(lblTelefone);

        fieldTelefone = criarCampoTexto(504, 221);
        contentPane.add(fieldTelefone);

        JLabel lblEmail = criarLabel("Email:", 504, 281);
        contentPane.add(lblEmail);

        fieldEmail = criarCampoTexto(504, 321);
        contentPane.add(fieldEmail);
    }

    private JLabel criarLabel(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setBounds(x, y, 150, 30);
        return label;
    }

    private JTextField criarCampoTexto(int x, int y) {
        JTextField campo = new JTextField();
        campo.setBounds(x, y, 440, 35);
        campo.setBorder(new LineBorder(Color.GRAY, 1, true));
        campo.setColumns(10);
        return campo;
    }

    private void adicionarBotaoCriar() {
        JButton btnCriar = new JButton("Criar");
        btnCriar.setBounds(460, 420, 100, 40);
        btnCriar.setBackground(new Color(100, 149, 237));
        btnCriar.setForeground(Color.WHITE);
        btnCriar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnCriar.setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
        contentPane.add(btnCriar);

        btnCriar.addActionListener(e -> validarEProcessarCadastro());
    }

    private void adicionarLabelAssinatura() {
        JLabel lblByVinicius = new JLabel("by Vinicius", SwingConstants.CENTER);
        lblByVinicius.setForeground(Color.LIGHT_GRAY);
        lblByVinicius.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblByVinicius.setBounds(0, 500, 1004, 20);
        contentPane.add(lblByVinicius);
    }

    private void validarEProcessarCadastro() {
        String nome = fieldNome.getText().trim();
        String endereco = fieldEndereco.getText().trim();
        String telefone = fieldTelefone.getText().trim();
        String email = fieldEmail.getText().trim();

        // Define endereço e email como null se estiverem vazios
        endereco = endereco.isEmpty() ? null : endereco;
        email = email.isEmpty() ? null : email;

        if (isCampoVazio(nome, telefone)) {
            JOptionPane.showMessageDialog(this, "Nome e Telefone não podem ser vazios.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
