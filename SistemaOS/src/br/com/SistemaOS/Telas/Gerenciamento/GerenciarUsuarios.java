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

public class GerenciarUsuarios extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Pesquisa
    private JTextField fieldSearch;

    // Botões
    private JButton btnCriarUsuario;

    // Tabela de Usuários
    private JTable table;
    private DefaultTableModel tableModel;

    // Classes
    private ScreenTools util = new ScreenTools();
    private CentroUsuariosDAO dao = new CentroUsuariosDAO();

    //user
    private Usuario user;
    
    // Lista de usuários
    private List<Usuario> allUsers;

    public GerenciarUsuarios(Usuario usu) {
        setResizable(false);
        setTitle("Usuarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1020, 540);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(util.getBackgroundColor());
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        this.user = usu;

        initComponents();

        loadUsers();
    }

    private void initComponents() {
        initLabels();
        initTable();
        initTools();
        initListeners();
    }

    private void initLabels() {
        contentPane.add(util.criarLabel("Usuários", (this.getWidth()-150)/2, 11, 150, 47, 30, true));
    }

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

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    Usuario selectedUser = allUsers.get(selectedRow);
                    DetalhesUsuario userFrame = new DetalhesUsuario(
                    		selectedUser.getId(), 
                    		selectedUser.getNome(), 
                    		selectedUser.getFone(), 
                    		selectedUser.getLogin(), 
                    		selectedUser.getSenha(), 
                    		selectedUser.getPerfil(),
                    		user);
                    userFrame.setVisible(true);
                }
            }
        });
    }

    private void initTools() {
    	
    	//Pesquisa
        fieldSearch = new JTextField("Digite o nome..");
        fieldSearch.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 12));
        fieldSearch.setBounds(731, 84, 263, 32);
        contentPane.add(fieldSearch);

        //btnCriarUsuario
        btnCriarUsuario = new JButton("Criar Usuario");
        btnCriarUsuario.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCriarUsuario.setBounds(10, 93, 160, 23);
        btnCriarUsuario.setBackground(new Color(63, 182, 207));
        btnCriarUsuario.setForeground(Color.WHITE);
        btnCriarUsuario.setFocusPainted(false);
        contentPane.add(btnCriarUsuario);
    }

    private void initListeners() {
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

        btnCriarUsuario.addActionListener(e -> {
            CriarUser frame = new CriarUser();
            frame.setVisible(true);
        });

        fieldSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (fieldSearch.getText().equals("Digite o nome..")) {
                    fieldSearch.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (fieldSearch.getText().isEmpty()) {
                    fieldSearch.setText("Digite o nome..");
                }
            }
        });

        fieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterUsers(fieldSearch.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterUsers(fieldSearch.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterUsers(fieldSearch.getText().trim());
            }
        });
        
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            	loadUsers();
            }

			@Override
			public void windowLostFocus(WindowEvent e) {			
			}
        });
    }

    private void loadUsers() {
        allUsers = dao.getUsuarios(); 
        updateTable(allUsers); 
    }

    private void updateTable(List<Usuario> data) {
        String[] columnNames = { "ID", "Nome", "Fone", "Login", "Senha", "Perfil" };
        Object[][] tableData = new Object[data.size()][columnNames.length];

        for (int i = 0; i < data.size(); i++) {
            Usuario usuario = data.get(i);
            tableData[i] = new Object[] {
                usuario.getId(),
                usuario.getNome(),
                usuario.getFone(),
                usuario.getLogin(),
                "******",
                usuario.getPerfil()
            };
        }

        tableModel.setDataVector(tableData, columnNames);
    }

    private void filterUsers(String searchText) {
        if (allUsers == null) return;

        List<Usuario> filteredData = new ArrayList<>();
        for (Usuario usuario : allUsers) {
            if (usuario.getNome().toLowerCase().contains(searchText.toLowerCase())) {
                filteredData.add(usuario);
            }
        }

        updateTable(filteredData); 
    }
}
