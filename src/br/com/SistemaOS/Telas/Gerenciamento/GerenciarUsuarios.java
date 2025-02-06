package br.com.SistemaOS.Telas.Gerenciamento;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.Telas.Criar.CriarUser;
import br.com.SistemaOS.Telas.Detalhes.DetalhesUsuario;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.Usuario;

/**
 * Tela para gerenciamento de usuários. Permite visualizar, pesquisar e criar
 * novos usuários.
 */
public class GerenciarUsuarios extends JFrame {

    private static final long serialVersionUID = 1L;

    /** Painel principal que contém todos os componentes da interface. */
    private JPanel contentPane;

    /** Campo de texto para pesquisa de usuários. */
    private JTextField campoPesquisa;

    /** Botão para criar um novo usuário. */
    private JButton btnCriarUsuario;

    /** Tabela que exibe a lista de usuários. */
    private JTable table;

    /** Modelo da tabela que define a estrutura dos dados exibidos. */
    private DefaultTableModel tableModel;

    /** Utilitário para manipulação de elementos da tela. */
    private ScreenTools util = new ScreenTools();

    /** DAO para operações relacionadas aos usuários. */
    private CentroUsuariosDAO dao = new CentroUsuariosDAO();

    /** Usuário que está acessando a tela. */
    private Usuario user;

    /** Lista de todos os usuários cadastrados no sistema. */
    private List<Usuario> allUsers;

    /** Lista de usuários filtrados com base na pesquisa. */
    private List<Usuario> filteredUsers = new ArrayList<>();

    /**
     * Constrói a tela de gerenciamento de usuários.
     * 
     * @param usu Usuário logado
     */
    public GerenciarUsuarios(Usuario usu) {
        setResizable(false);
        setTitle("Usuarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1020, 540);
        setLocationRelativeTo(null);
        setIconImage(util.getLogo());

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(util.getBackgroundColor());
        setContentPane(contentPane);
        contentPane.setLayout(null);

        this.user = usu;

        // Inicializa os componentes da tela
        initComponents();

        // Carrega os usuários na tela
        loadUsers();
    }

    /**
     * Inicializa os componentes da tela.
     */
    private void initComponents() {
        initLabels();
        initTable();
        initTools();
        initListeners();
    }

    /**
     * Inicializa os rótulos da interface.
     */
    private void initLabels() {
        contentPane.add(util.criarLabel("Usuários", (this.getWidth() - 150) / 2, 11, 150, 47, 30, true));
    }

    /**
     * Inicializa a tabela de usuários.
     */
    private void initTable() {
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 127, 984, 368);
        contentPane.add(scrollPane);

        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ação ao clicar duas vezes em um usuário
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    Usuario selectedUser;
                    int selectedRow = table.getSelectedRow();

                    // Seleciona o usuário com base no texto de pesquisa
                    if (campoPesquisa.getText().equals("Digite o nome..") || campoPesquisa.getText().trim().isEmpty()) {
                        selectedUser = allUsers.get(selectedRow);
                    } else {
                        selectedUser = filteredUsers.get(selectedRow);
                    }

                    if (!dao.getStatusConnection()) {
                        return;
                    }

                    // Exibe os detalhes do usuário
                    DetalhesUsuario userFrame = new DetalhesUsuario(selectedUser.getId(), selectedUser.getNome(),
                            selectedUser.getFone(), selectedUser.getLogin(), selectedUser.getSenha(),
                            selectedUser.getPerfil(), user);
                    userFrame.setVisible(true);
                }
            }
        });
    }

    /**
     * Inicializa os componentes de ferramentas (campos de pesquisa e botões).
     */
    private void initTools() {
        // Campo de pesquisa
        campoPesquisa = new JTextField("Digite o nome..");
        campoPesquisa.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 12));
        campoPesquisa.setBounds(731, 84, 263, 32);
        contentPane.add(campoPesquisa);

        // Botão de criação de usuário
        btnCriarUsuario = new JButton("Criar Usuario");
        btnCriarUsuario.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCriarUsuario.setBounds(10, 93, 160, 23);
        btnCriarUsuario.setBackground(new Color(63, 182, 207));
        btnCriarUsuario.setForeground(Color.WHITE);
        btnCriarUsuario.setFocusPainted(false);
        contentPane.add(btnCriarUsuario);
    }

    /**
     * Inicializa os listeners (ações de interação do usuário).
     */
    private void initListeners() {
        // Botão de criação de usuário (hover)
        btnCriarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnCriarUsuario.setBackground(new Color(33, 116, 133));
                btnCriarUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnCriarUsuario.setBackground(new Color(63, 182, 207));
                btnCriarUsuario.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Ação ao clicar no botão de criar usuário
        btnCriarUsuario.addActionListener(e -> {
            if (!dao.getStatusConnection()) {
                util.AvisoDeConexão();
                return;
            }
            CriarUser frame = new CriarUser();
            frame.setVisible(true);
        });

        // Ação ao ganhar foco no campo de pesquisa
        campoPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campoPesquisa.getText().equals("Digite o nome..")) {
                    campoPesquisa.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campoPesquisa.getText().isEmpty()) {
                    campoPesquisa.setText("Digite o nome..");
                    loadUsers();
                }
            }
        });

        // Ação ao digitar no campo de pesquisa
        campoPesquisa.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterUsers(campoPesquisa.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterUsers(campoPesquisa.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterUsers(campoPesquisa.getText().trim());
            }
        });

        // Ação ao ganhar foco na janela
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                campoPesquisa.setText("Digite o nome..");
                loadUsers();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
            }
        });
    }

    /**
     * Carrega a lista de usuários do banco de dados e atualiza a tabela.
     */
    private void loadUsers() {
        if (!dao.getStatusConnection()) {
            util.AvisoDeConexão();
            return;
        }
        allUsers = dao.getUsuarios();
        updateTable(allUsers);
    }

    /**
     * Atualiza os dados na tabela de usuários.
     * 
     * @param data Lista de usuários a ser exibida
     */
    private void updateTable(List<Usuario> data) {
        String[] columnNames = { "ID", "Nome", "Fone", "Login", "Senha", "Perfil" };
        Object[][] tableData = new Object[data.size()][columnNames.length];

        for (int i = 0; i < data.size(); i++) {
            Usuario usuario = data.get(i);
            tableData[i] = new Object[] { usuario.getId(), usuario.getNome(), usuario.getFone(), usuario.getLogin(),
                    "******", usuario.getPerfil() };
        }

        tableModel.setDataVector(tableData, columnNames);
    }

    /**
     * Filtra os usuários com base no texto de pesquisa.
     * 
     * @param searchText Texto de pesquisa
     */
    private void filterUsers(String searchText) {
        if (allUsers == null)
            return;

        filteredUsers.clear();
        for (Usuario usuario : allUsers) {
            if (usuario.getNome().toLowerCase().contains(searchText.toLowerCase())) {
                filteredUsers.add(usuario);
            }
        }

        updateTable(filteredUsers);
    }
}