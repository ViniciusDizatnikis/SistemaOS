package br.com.SistemaOS.Principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import br.com.SistemaOS.DAO.CentroOSDAO;
import br.com.SistemaOS.DAO.CentroUsuariosDAO;
import br.com.SistemaOS.Telas.Detalhes.DetalhesOS;
import br.com.SistemaOS.Telas.Gerenciamento.GerenciarClientes;
import br.com.SistemaOS.Telas.Gerenciamento.GerenciarOS;
import br.com.SistemaOS.Telas.Gerenciamento.GerenciarUsuarios;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.OrdemServico;
import br.com.SistemaOS.modelo.Usuario;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private ScreenTools util = new ScreenTools();
    private CentroUsuariosDAO userDao = new CentroUsuariosDAO();
    private CentroOSDAO osDao = new CentroOSDAO();
    private Usuario user;
    private JPanel contentPane;
    private boolean isAdmin;
    private JLabel status;
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color ERROR_COLOR = new Color(231, 76, 60);
    
    private DefaultTableModel model;

    public TelaPrincipal(Usuario usuInfo) {
        this.user = usuInfo;
        this.isAdmin = usuInfo.getPerfil().equals("admin");

        setUpFrame();
        setUpMenuBar(isAdmin);
        setUpContentPane(usuInfo);
    }

    private void setUpFrame() {
        setIconImage(Toolkit.getDefaultToolkit()
                .getImage(TelaPrincipal.class.getResource("/br/com/SistemaOS/Icones/icon/Logo.png")));
        setTitle("Sistema De Ordem e Serviço");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1360, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        // Atualizar a tabela quando a janela ganhar o foco
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                carregarDados(model); // Atualizar dados da tabela
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
            }
        });
    }

    private void setUpMenuBar(boolean isAdmin) {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuCadastro = new JMenu("Cadastro");
        menuBar.add(menuCadastro);

        JMenuItem menuClientes = new JMenuItem("Clientes");
        menuClientes.setAccelerator(KeyStroke.getKeyStroke("control C"));
        menuClientes.addActionListener(e -> abrirCadastroCliente());
        menuClientes.setEnabled(isAdmin);
        menuCadastro.add(menuClientes);

        JMenuItem menuOS = new JMenuItem("OS");
        menuOS.setAccelerator(KeyStroke.getKeyStroke("control O"));
        menuOS.addActionListener(e -> abrirCadastroOS());
        menuCadastro.add(menuOS);

        JMenuItem menuUsuarios = new JMenuItem("Usuários");
        menuUsuarios.setAccelerator(KeyStroke.getKeyStroke("control U"));
        menuUsuarios.addActionListener(e -> abrirCadastroUsuarios());
        menuUsuarios.setEnabled(isAdmin);
        menuCadastro.add(menuUsuarios);

        JMenu menuRelatorios = new JMenu("Relatórios");
        menuRelatorios.setEnabled(isAdmin);
        menuBar.add(menuRelatorios);

        JMenuItem servicosItem = new JMenuItem("Serviços");
        servicosItem.setAccelerator(KeyStroke.getKeyStroke("control R"));
        servicosItem.addActionListener(e -> gerarRelatorioServicos());
        menuRelatorios.add(servicosItem);

        JMenu menuAjuda = new JMenu("Ajuda");
        menuBar.add(menuAjuda);

        JMenuItem sobreItem = new JMenuItem("Sobre");
        sobreItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        sobreItem.addActionListener(e -> exibirSobre());
        menuAjuda.add(sobreItem);

        JMenu menuOpcoes = new JMenu("Opções");
        menuBar.add(menuOpcoes);

        JMenuItem logoffItem = new JMenuItem("Logoff");
        logoffItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.ALT_MASK));
        logoffItem.addActionListener(e -> logOffAction());
        menuOpcoes.add(logoffItem);

        JMenuItem sairItem = new JMenuItem("Sair");
        sairItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        sairItem.addActionListener(e -> sairAction());
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
        JOptionPane.showMessageDialog(this, "Gerando relatório de serviços.", "Relatórios",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void exibirSobre() {
        JOptionPane.showMessageDialog(this,
                "Sistema de Ordem e Serviço - Versão 5.0 \nDesenvolvido Por: ViniciusDizatnikis", "Sobre",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void logOffAction() {
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja fazer logoff?", "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            new TelaLogin().setVisible(true);
            this.dispose();
        }
    }

    private void sairAction() {
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza de que deseja sair do sistema?", "Sair",
                JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void setUpContentPane(Usuario usuInfo) {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(util.getBackgroundColor());
        contentPane.setLayout(null);
        setContentPane(contentPane);

        setUpTable();
        setUpUserIcon(contentPane);
        setUpUserDetails(contentPane);
        setUpDateAndTimePanel();

        JLabel lblTexto = new JLabel("Suas Ordens e Serviços");
        lblTexto.setForeground(Color.WHITE);
        lblTexto.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 61));
        lblTexto.setHorizontalAlignment(SwingConstants.LEFT);
        lblTexto.setBounds(10, 0, 927, 82);
        contentPane.add(lblTexto);

        status = new JLabel("<dynamic>");
        status.setHorizontalAlignment(SwingConstants.CENTER);
        status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        status.setForeground(Color.WHITE);
        status.setBounds(947, 623, 387, 25);
        contentPane.add(status);
    }

    private void atualizarStatus() {
        boolean conectado = userDao.getStatus();
        String mensagem = conectado ? "Status: Conectado ao banco de dados"
                : "Status: Erro ao conectar ao banco de dados";
        Color cor = conectado ? SUCCESS_COLOR : ERROR_COLOR;

        status.setText(mensagem);
        status.setForeground(cor);
    }

    private void setUpTable() {
        String[] columnNames = new String[] { "ID Venda", "Data", "Equipamento", "Defeito", "Serviço", "Valor",
                "Cliente", "Técnico(a)" };

        model = new DefaultTableModel(columnNames, 0);
        carregarDados(model);

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
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    inspectItem(table);
                }
            }
        });
    }

    private void carregarDados(DefaultTableModel model) {
        // Limpa os dados antigos antes de carregar os novos
        model.setRowCount(0);

        List<OrdemServico> data = osDao.listarTodasOS(user.getId());

        for (OrdemServico os : data) {
            model.addRow(new Object[] {
                os.getOs(), 
                os.getData(), 
                os.getEquipamento(), 
                os.getDefeito(), 
                os.getServico(),
                os.getValor(),
                os.getCliente(),
                os.getTecnico()
            });
        }
    }

    private void setUpTablePopupMenu(JTable table, JPopupMenu popupMenu) {
        JMenuItem inspecionarItem = new JMenuItem("Inspecionar");
        inspecionarItem.addActionListener(e -> inspectItem(table));
        popupMenu.add(inspecionarItem);
    }

    private void inspectItem(JTable table) {
        int rowIndex = table.getSelectedRow();
        if (rowIndex != -1) {
            OrdemServico os = osDao.listarTodasOS(user.getId()).get(rowIndex);
            DetalhesOS info = new DetalhesOS();
            info.exibirDetalhes(os);
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

    private void setUpUserDetails(JPanel contentPane) {
        String splitName = user.getNome();
        if (user.getNome().contains(" ")) {
            splitName = splitName.split(" ")[0];
        }
        JLabel lblUsuario = new JLabel(splitName);
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 25));
        lblUsuario.setBounds(947, 161, 387, 34);
        contentPane.add(lblUsuario);
    }

    private void setUpDateAndTimePanel() {
        JPanel desktopPanel_1 = new JPanel();
        desktopPanel_1.setBackground(Color.GRAY);
        desktopPanel_1.setBounds(947, 550, 387, 71);
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

        JLabel lblSaudacao = new JLabel("<dynamic>");
        lblSaudacao.setHorizontalAlignment(SwingConstants.CENTER);
        lblSaudacao.setForeground(Color.WHITE);
        lblSaudacao.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 32));
        lblSaudacao.setBounds(947, 99, 387, 51);
        contentPane.add(lblSaudacao);

        Timer timerClock = new Timer(1000, e -> {
            lblSaudacao.setText(util.getSaudacao());
            dataSource.setText(util.getDataAtual());
            horaSource.setText(util.getHoraAtual());
            atualizarStatus();
        });
        timerClock.start();
    }
}
