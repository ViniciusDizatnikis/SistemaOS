package br.com.SistemaOS.Telas.Criar;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.Utils.ScreenTools;

/**
 * A classe {@code CriarCliente} é responsável pela interface gráfica onde o
 * usuário pode criar um novo cliente, fornecendo informações como nome,
 * endereço, telefone e email.
 * 
 * @author Vinicius Dizatnikis
 */
public class CriarCliente extends JFrame {

    private static final long serialVersionUID = 1L;

    /** Painel principal que contém todos os componentes da interface. */
    private JPanel contentPane;

    /** Utilitário para manipulação de elementos da tela. */
    private ScreenTools util = new ScreenTools();

    /** DAO para operações relacionadas aos clientes. */
    private CentroClientesDAO dao = new CentroClientesDAO();

    /** Campo de texto para o nome do cliente. */
    private JTextField fieldNome;

    /** Campo de texto para o endereço do cliente. */
    private JTextField fieldEndereco;

    /** Campo de texto para o telefone do cliente. */
    private JTextField fieldFone;

    /** Campo de texto para o email do cliente. */
    private JTextField fieldEmail;

    /**
     * Construtor da classe {@code CriarCliente}. Configura a interface gráfica e os
     * elementos da tela para a criação de um cliente.
     */
    public CriarCliente() {
        configurarFrame();
        adicionarTitulo();
        adicionarCamposTexto();
        adicionarBotaoCriar();
    }

    /**
     * Configura o frame principal da janela, incluindo propriedades como título,
     * ícone, tamanho e comportamento de fechamento.
     */
    private void configurarFrame() {
        setResizable(false);
        setTitle("Criar Cliente");
        setIconImage(util.getLogo());
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

    /**
     * Adiciona o título na tela para indicar a ação que está sendo realizada.
     */
    private void adicionarTitulo() {
        JLabel lblCriarCliente = new JLabel("Criar Cliente");
        lblCriarCliente.setHorizontalAlignment(SwingConstants.CENTER);
        lblCriarCliente.setForeground(Color.WHITE);
        lblCriarCliente.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 36));
        lblCriarCliente.setBounds(0, 20, 1004, 50);
        contentPane.add(lblCriarCliente);
    }

    /**
     * Adiciona os campos de texto para que o usuário insira as informações do
     * cliente. Os campos incluem Nome, Endereço, Telefone e Email.
     */
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

        fieldFone = new JTextField();
        util.estilizarField(fieldFone, "");
        fieldFone.setBounds(504, 221, 440, 35);
        contentPane.add(fieldFone);

        fieldFone.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Não implementado
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                util.formatarCelular(fieldFone);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Não implementado
            }
        });

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

    /**
     * Adiciona o botão para criar o cliente. Quando clicado, valida os campos e
     * cria o cliente.
     */
    private void adicionarBotaoCriar() {
        JButton btnCriar = new JButton("Criar");
        util.estilizarBotao(btnCriar);
        btnCriar.setBounds(460, 420, 100, 40);
        contentPane.add(btnCriar);
        btnCriar.addActionListener(e -> validarEProcessarCadastro());
    }

    /**
     * Valida os campos preenchidos e processa o cadastro do cliente. Exibe uma
     * mensagem de erro caso algum campo obrigatório esteja vazio.
     */
    private void validarEProcessarCadastro() {
        String nome = fieldNome.getText().trim();
        String endereco = fieldEndereco.getText().trim();
        String telefone = fieldFone.getText().trim();
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

    /**
     * Cria um novo cliente utilizando os dados fornecidos e salva no banco de
     * dados.
     * 
     * @param nome     Nome do cliente.
     * @param endereco Endereço do cliente.
     * @param telefone Telefone do cliente.
     * @param email    Email do cliente.
     */
    private void criarCliente(String nome, String endereco, String telefone, String email) {
        dao.criarCliente(nome, endereco, telefone, email);
    }

    /**
     * Verifica se algum dos campos fornecidos está vazio.
     * 
     * @param campos Campos a serem verificados.
     * @return {@code true} se algum campo estiver vazio, {@code false} caso
     *         contrário.
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