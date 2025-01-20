package br.com.SistemaOS.Telas;

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

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.Utils.UtilitariosTela;
import br.com.SistemaOS.modelo.Usuario;

public class DetalhesCliente extends JFrame {

    // Outras classes
    private CentroClientesDAO dao = new CentroClientesDAO();
    private UtilitariosTela util;

    // Informações do Cliente
    private Integer idCliente;
    private String nomeCliente;
    private String foneCliente;
    private String enderecoCliente;
    private String emailCliente;
    // Botões e campos
    private JButton btnEditar, btnCancelar, btnSalvar, btnOk, btnDeletar;
    private JTextField txtNome, txtFone, txtEndereco, txtEmail;

    private JPanel contentPane;

    public DetalhesCliente(int id, String nome, String fone, String endereco, String email, Usuario usuario) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1020, 540);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel Principal
        contentPane = new JPanel();
        contentPane.setBackground(util.getBackgroundColor());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        initComponents(id, nome, fone, endereco, email, usuario);
    }

    private void initComponents(Integer id, String nome, String fone, String endereco, String email, Usuario usuario) {
        getClienteData(id, nome, fone, endereco, email, usuario);

        initLabels();
        initFields();
        initBtns();

        // Botão Excluir Cliente
        btnDeletar.addActionListener(e -> deletarCliente());

        // Botão Fechar Janela
        btnOk.addActionListener(e -> this.dispose());

        // Botão Salvar Alterações
        btnSalvar.addActionListener(e -> salvarCliente());

        // Botão Editar Cliente
        btnEditar.addActionListener(e -> modoEdicao(true));

        // Botão Cancelar Edição
        btnCancelar.addActionListener(e -> modoEdicao(false));

        // Configuração da janela
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

    private void getClienteData(Integer id, String nome, String fone, String endereco, String email, Usuario usuario) {
        this.idCliente = id;
        this.nomeCliente = nome;
        this.foneCliente = fone;
        this.enderecoCliente = endereco;
        this.emailCliente = email;
        this.util = new UtilitariosTela();
    }

    private void initBtns() {
        btnDeletar = new JButton("Excluir");
        btnDeletar.setBounds(414, 90, 100, 30);
        contentPane.add(btnDeletar);

        btnOk = new JButton("Fechar");
        btnOk.setHorizontalAlignment(SwingConstants.CENTER);
        btnOk.setBounds(440, 460, 100, 30);
        contentPane.add(btnOk);

        btnEditar = new JButton("Editar");
        btnEditar.setToolTipText("Editar informações do Cliente");
        btnEditar.setBounds(304, 90, 100, 30);
        contentPane.add(btnEditar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(384, 154, 100, 30);
        btnCancelar.setVisible(false);
        contentPane.add(btnCancelar);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(274, 154, 100, 30);
        btnSalvar.setVisible(false);
        contentPane.add(btnSalvar);
    }

    private void initLabels() {
        contentPane.add(util.criarLabel("Informações do Cliente", 315, 0, 521, 52, 35));
        contentPane.add(util.criarLabel("Nome:", 48, 284, 100, 30, 20));
        contentPane.add(util.criarLabel("Fone:", 48, 334, 100, 30, 20));
        contentPane.add(util.criarLabel("Endereço:", 48, 384, 100, 30, 20));
        contentPane.add(util.criarLabel("Email:", 48, 434, 100, 30, 20));
    }

    private void initFields() {
        txtNome = new JTextField(nomeCliente);
        txtNome.setEnabled(false);
        txtNome.setBounds(148, 284, 300, 30);
        contentPane.add(txtNome);

        txtFone = new JTextField(foneCliente);
        txtFone.setEnabled(false);
        txtFone.setBounds(148, 334, 300, 30);
        contentPane.add(txtFone);

        txtEndereco = new JTextField(enderecoCliente);
        txtEndereco.setEnabled(false);
        txtEndereco.setBounds(148, 384, 300, 30);
        contentPane.add(txtEndereco);

        txtEmail = new JTextField(emailCliente);
        txtEmail.setEnabled(false);
        txtEmail.setBounds(148, 434, 300, 30);
        contentPane.add(txtEmail);
    }

    private void modoEdicao(boolean value) {
        btnEditar.setVisible(!value);
        btnSalvar.setVisible(value);
        btnCancelar.setVisible(value);
        btnDeletar.setVisible(!value);

        txtNome.setEnabled(value);
        txtFone.setEnabled(value);
        txtEndereco.setEnabled(value);
        txtEmail.setEnabled(value);
    }

    private void deletarCliente() {
        int confirmacao = JOptionPane.showConfirmDialog(
            null,
            "Tem certeza de que deseja excluir este cliente?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
           // dao.deletarCliente(idCliente);
            JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
    }

    private void salvarCliente() {
        ArrayList<Object> update = new ArrayList<>();

        // Validações e atualizações
        update.add(!txtNome.getText().equals(nomeCliente) ? txtNome.getText() : null);
        update.add(!txtFone.getText().equals(foneCliente) ? txtFone.getText() : null);
        update.add(!txtEndereco.getText().equals(enderecoCliente) ? txtEndereco.getText() : null);
        update.add(!txtEmail.getText().equals(emailCliente) ? txtEmail.getText() : null);

        if (update.stream().allMatch(Objects::isNull)) {
            JOptionPane.showMessageDialog(null, "Nenhuma alteração realizada!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //dao.atualizarCliente(idCliente, update);
            JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
    }
}
