package br.com.SistemaOS.Principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.SistemaOS.DAO.CentroOSDAO;
import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.Telas.Detalhes.DetalhesOS;
import br.com.SistemaOS.Telas.Gerenciamento.GerenciarClientes;
import br.com.SistemaOS.Telas.Gerenciamento.GerenciarOS;
import br.com.SistemaOS.Telas.Gerenciamento.GerenciarUsuarios;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.Usuario;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Console;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

public class TelaPrincipal extends JFrame {
	
    private static final long serialVersionUID = 1L;
    private ScreenTools util = new ScreenTools();
    private CentroUsuariosDAO userDao =  new CentroUsuariosDAO();
    private CentroOSDAO osDao = new CentroOSDAO();
    private Usuario user;
	private JPanel contentPane;
	private boolean isAdmin;
    
    
    public TelaPrincipal(Usuario usuInfo) {
    	this.user = usuInfo;
        this.isAdmin = usuInfo.getPerfil().equals("admin");

        
        setUpFrame();
        setUpMenuBar(isAdmin);
        setUpContentPane(usuInfo);
    }

    private void setUpFrame() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPrincipal.class.getResource("/br/com/SistemaOS/Icones/icon/Logo.png")));
        setTitle("Sistema De Ordem e Serviço");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1360, 720);
        setLocationRelativeTo(null);
    }

    private void setUpMenuBar(boolean isaAdmin) {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuCadastro = new JMenu("Cadastro");
        menuBar.add(menuCadastro);

        JMenuItem menuClientes = new JMenuItem("Clientes");
        menuClientes.setAccelerator(KeyStroke.getKeyStroke("control C"));
        menuClientes.addActionListener(e -> abrirCadastroCliente());
        menuClientes.setEnabled(isaAdmin);
        menuCadastro.add(menuClientes);

        JMenuItem menuOS = new JMenuItem("OS");
        menuOS.setAccelerator(KeyStroke.getKeyStroke("control O"));
        menuOS.addActionListener(e -> abrirCadastroOS());
        menuCadastro.add(menuOS);

        JMenuItem menuUsuarios = new JMenuItem("Usuários");
        menuUsuarios.setAccelerator(KeyStroke.getKeyStroke("control U"));
        menuUsuarios.addActionListener(e -> abrirCadastroUsuarios());
        menuUsuarios.setEnabled(isaAdmin);
        menuCadastro.add(menuUsuarios);
        
        // Menu Relatórios
        JMenu menuRelatorios = new JMenu("Relatórios");
        menuRelatorios.setEnabled(isaAdmin);
        menuBar.add(menuRelatorios);

        JMenuItem servicosItem = new JMenuItem("Serviços");
        servicosItem.setAccelerator(KeyStroke.getKeyStroke("control R"));
        servicosItem.addActionListener(e -> gerarRelatorioServicos());
        menuRelatorios.add(servicosItem);

        // Menu Ajuda
        JMenu menuAjuda = new JMenu("Ajuda");
        menuBar.add(menuAjuda);

        JMenuItem sobreItem = new JMenuItem("Sobre");
        sobreItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        sobreItem.addActionListener(e -> exibirSobre());
        menuAjuda.add(sobreItem);

        // Menu Opções
        JMenu menuOpcoes = new JMenu("Opções");
        menuBar.add(menuOpcoes);

        JMenuItem logoffItem = new JMenuItem("Logoff");
        logoffItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.ALT_MASK));
        logoffItem.addActionListener(e -> {
        	int confirmacao = JOptionPane.showConfirmDialog(
                    null, 
                    "Deseja fazer logOff?", 
                    "LogOff", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE
                );

                if (confirmacao == JOptionPane.YES_OPTION) {
                 new TelaLogin().setVisible(true);
                 this.dispose();
                }
        });
        menuOpcoes.add(logoffItem);

        JMenuItem sairItem = new JMenuItem("Sair");
        sairItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
       
        sairItem.addActionListener(e ->{ 
        	int confirmacao = JOptionPane.showConfirmDialog(
                    null, 
                    "Tem certeza de que deseja sair do Sistema?", 
                    "Sair", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE
                );

                if (confirmacao == JOptionPane.YES_OPTION) {
                    System.exit(0);                    
                }
        	
        });
        menuOpcoes.add(sairItem);
    }

    private void abrirCadastroCliente() {
       new GerenciarClientes().setVisible(true);
    }

    private void abrirCadastroOS() {
        new GerenciarOS(user).setVisible(true);
    }

    private void abrirCadastroUsuarios() {
     new GerenciarUsuarios(user).setVisible(true);
    }

    private void gerarRelatorioServicos() {
        JOptionPane.showMessageDialog(this, "Gerando relatório de serviços.", "Relatórios", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exibirSobre() {
        JOptionPane.showMessageDialog(this, "Sistema de Ordem e Serviço - Versão 5.0 \nDesenvolvido Por: ViniciusDizatnikis", "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }

   
    private void logOffAction() {
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja Fazer LogOff?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new TelaLogin().setVisible(true);
            this.dispose();
        }
    }

    private void setUpContentPane(Usuario usuInfo) {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(util.getBackgroundColor());
        contentPane.setLayout(null);
        setContentPane(contentPane);
        this.contentPane = contentPane;
        
        setUpTable(contentPane, usuInfo.getId());
        setUpUserIcon(contentPane);
        setUpUserDetails(contentPane, usuInfo.getNome());
        setUpDateAndTimePanel();
        
        JLabel lblTexto = new JLabel("Suas Ordens e Seviços");
        lblTexto.setForeground(new Color(255, 255, 255));
        lblTexto.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 61));
        lblTexto.setHorizontalAlignment(SwingConstants.LEFT);
        lblTexto.setBounds(10, 0, 927, 82);
        contentPane.add(lblTexto);
        
    }

    private void setUpTable(JPanel contentPane, int userId) {
        String[] columnNames = new String[]{"ID Venda", "Data", "Cliente", "Fone", "Equipamento", "Defeito", "Serviço", "Valor"};
        Object[][] data = osDao.getOrdensEServico(userId);
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        contentPane.add(scrollPane);
        scrollPane.setBounds(10, 92, 927, 555);

        JPopupMenu popupMenu = new JPopupMenu();
        setUpTablePopupMenu(table, popupMenu);


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e, table, popupMenu);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e, table, popupMenu);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    inspectItem(table); 
                }
            }
        });
    }

    private void setUpTablePopupMenu(JTable table, JPopupMenu popupMenu) {
        JMenuItem inspecionarItem = new JMenuItem("Inspecionar");
        inspecionarItem.addActionListener(e -> inspectItem(table));
        popupMenu.add(inspecionarItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e, table, popupMenu);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e, table, popupMenu);
            }
        });
    }

    private void showPopupMenu(MouseEvent e, JTable table, JPopupMenu popupMenu) {
        if (e.isPopupTrigger()) {
            int row = table.rowAtPoint(e.getPoint());
            if (row != -1) {
                table.setRowSelectionInterval(row, row);
            }
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void inspectItem(JTable table) {
        int rowIndex = table.getSelectedRow();
        if (rowIndex != -1) {
            Object[] rowData = new Object[table.getColumnCount()];

            for (int i = 0; i < table.getColumnCount(); i++) {
                rowData[i] = table.getValueAt(rowIndex, i);
            }

            DetalhesOS info = new DetalhesOS();
            info.exibirDetalhes(rowData);
            info.setVisible(true);
        }
    }



    private void setUpUserIcon(JPanel contentPane) {
        JLabel usuarioIcon = new JLabel();
        usuarioIcon.setHorizontalAlignment(SwingConstants.CENTER);
        usuarioIcon.setBounds(1010, 247, 267, 264);
        usuarioIcon.setIcon(util.mudarTamanhoImg("/br/com/SistemaOS/Icones/icon/homem-usuario.png", 260, 260));
        contentPane.add(usuarioIcon);
    }

    private void setUpUserDetails(JPanel contentPane, String name) {

        JLabel lblSaudacao = new JLabel("<dynamic>");
        lblSaudacao.setHorizontalAlignment(SwingConstants.CENTER);
        lblSaudacao.setForeground(Color.WHITE);
        lblSaudacao.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 34));
        lblSaudacao.setBounds(1014, 92, 263, 58);
        contentPane.add(lblSaudacao);
        
        JLabel lblUsuario = new JLabel("<dynamic>");
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 25));
        lblUsuario.setBounds(947, 161, 387, 34);
        contentPane.add(lblUsuario);
        
        lblUsuario.setText(name);

        adjustLabelSizeDynamic(lblUsuario, 387);
        
        Timer timer = new Timer(1000, e -> {
            lblSaudacao.setText(util.getSaudacao());
        });
        timer.start();
    }

    private void setUpDateAndTimePanel() {
        JPanel desktopPanel_1 = new JPanel();
        desktopPanel_1.setBackground(Color.GRAY);
        desktopPanel_1.setBounds(947, 576, 387, 71);
        contentPane.add(desktopPanel_1);
        desktopPanel_1.setLayout(null);
        
        JLabel lblText = new JLabel("Data:");
        lblText.setBounds(5, 5, 189, 26);
        lblText.setHorizontalAlignment(SwingConstants.CENTER);
        lblText.setForeground(Color.WHITE);
        lblText.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 24));
        desktopPanel_1.add(lblText);
        
        JLabel lblHora = new JLabel("Hora:");
        lblHora.setHorizontalAlignment(SwingConstants.CENTER);
        lblHora.setForeground(Color.WHITE);
        lblHora.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 24));
        lblHora.setBounds(193, 5, 184, 26);
        desktopPanel_1.add(lblHora);
        
        JLabel dataSource = new JLabel("<Dynamic>");
        dataSource.setHorizontalAlignment(SwingConstants.CENTER);
        dataSource.setForeground(Color.WHITE);
        dataSource.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
        dataSource.setBounds(5, 34, 189, 26);
        desktopPanel_1.add(dataSource);
        
        JLabel horaSource = new JLabel("<Dynamic>");
        horaSource.setHorizontalAlignment(SwingConstants.CENTER);
        horaSource.setForeground(Color.WHITE);
        horaSource.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
        horaSource.setBounds(193, 34, 189, 26);
        desktopPanel_1.add(horaSource);

        Timer timerClock = new Timer(1000, e -> {
        	dataSource.setText(util.getDataAtual());
        	horaSource.setText(util.getHoraAtual());
        });
        timerClock.start();
    }
    
    
    private void adjustLabelSizeDynamic(JLabel label, int width) {
        String text = label.getText();
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int lineHeight = metrics.getHeight();

        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int lineCount = 0;

        for (String word : words) {
            if (metrics.stringWidth(line + word) < width) {
                line.append(word).append(" ");
            } else {
                lineCount++;
                line = new StringBuilder(word + " ");
            }
        }

        if (line.length() > 0) {
            lineCount++;
        }

        label.setBounds(label.getX(), label.getY(), width, lineHeight * lineCount);
    }
}
