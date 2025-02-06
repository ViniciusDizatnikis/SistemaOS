package br.com.SistemaOS.Telas.Gerenciamento;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import br.com.SistemaOS.DAO.CentroOSDAO;
import br.com.SistemaOS.Telas.Criar.CriarOrdemServico;
import br.com.SistemaOS.Telas.Detalhes.DetalhesOrdemServico;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.OrdemServico;
import br.com.SistemaOS.modelo.Usuario;

/**
 * Classe responsável pela tela de gerenciamento de Ordens de Serviço.
 * Exibe a lista de ordens de serviço e permite a criação de novas ordens.
 */
public class GerenciarOS extends JFrame {

    private static final long serialVersionUID = 1L;

    /** Painel principal que contém todos os componentes da interface. */
    private JPanel painelPrincipal;

    /** Campo de texto para pesquisa de ordens de serviço. */
    private JTextField campoPesquisa;

    /** Botão para criar uma nova ordem de serviço. */
    private JButton botaoCriarOS;

    /** Tabela que exibe a lista de ordens de serviço. */
    private JTable tabelaOS;

    /** Modelo da tabela que define a estrutura dos dados exibidos. */
    private DefaultTableModel modeloTabela;

    /** Utilitário para manipulação de elementos da tela. */
    private ScreenTools util = new ScreenTools();

    /** DAO para operações relacionadas às ordens de serviço. */
    private CentroOSDAO osDAO = new CentroOSDAO();

    /** Usuário que está acessando a tela. */
    private Usuario user;

    /** Indica se o usuário é um administrador. */
    private boolean isAdmin;

    /** Lista de todas as ordens de serviço cadastradas no sistema. */
    private List<OrdemServico> listaTodasOS;

    /** Lista de ordens de serviço filtradas com base na pesquisa. */
    private List<OrdemServico> osFiltradas = new ArrayList<>();

    /**
     * Construtor da tela de gerenciamento de ordens de serviço.
     * @param user Usuário que está acessando a tela.
     */
    public GerenciarOS(Usuario user) {
        this.user = user;
        this.isAdmin = user.getNome().equals("Administrador");

        setResizable(false);
        setTitle("Ordens de Serviço");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1020, 540);
        setLocationRelativeTo(null);
        setIconImage(util.getLogo());

        painelPrincipal = new JPanel();
        painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        painelPrincipal.setBackground(util.getBackgroundColor());
        setContentPane(painelPrincipal);
        painelPrincipal.setLayout(null);

        inicializarComponentes();
        carregarOS();
    }

    /**
     * Inicializa todos os componentes da tela.
     */
    private void inicializarComponentes() {
        inicializarLabels();
        inicializarTabela();
        inicializarFerramentas();
        inicializarListeners();
    }

    /**
     * Inicializa os rótulos (labels) da tela.
     */
    private void inicializarLabels() {
        painelPrincipal.add(
            util.criarLabel(
                isAdmin ? "Ordens de Serviço Da Empresa" : "Suas Ordens e Serviços", 
                (this.getWidth() - 600) / 2, 11, 600, 47, 30, true
            )
        );
    }

    /**
     * Inicializa a tabela de ordens de serviço.
     */
    private void inicializarTabela() {
        modeloTabela = new DefaultTableModel();
        tabelaOS = new JTable(modeloTabela) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane painelRolagem = new JScrollPane(tabelaOS);
        painelRolagem.setBounds(10, 127, 984, 368);
        painelPrincipal.add(painelRolagem);

        tabelaOS.setRowHeight(25);
        tabelaOS.setFillsViewportHeight(true);
        tabelaOS.getTableHeader().setReorderingAllowed(false);
        tabelaOS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaOS.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && tabelaOS.getSelectedRow() != -1) {
                    // Passar a lista filtrada se houver pesquisa, caso contrário, lista completa
                    List<OrdemServico> listaAtual = osFiltradas.isEmpty() ? listaTodasOS : osFiltradas;
                    if (!osDAO.getStatusConnection()) {
                        return;
                    }
                    inspectItem(listaAtual);
                }
            }
        });
    }

    /**
     * Exibe os detalhes da ordem de serviço selecionada.
     * @param ordensServico Lista de ordens de serviço.
     */
    private void inspectItem(List<OrdemServico> ordensServico) {
        int rowIndex = tabelaOS.getSelectedRow();
        if (rowIndex != -1) {
            OrdemServico osSelecionada = ordensServico.get(rowIndex);

            DetalhesOrdemServico detalhesFrame = new DetalhesOrdemServico(true);
            detalhesFrame.exibirDetalhes(osSelecionada);
            detalhesFrame.setVisible(true);
        }
    }

    /**
     * Inicializa os componentes auxiliares como campos de texto e botões.
     */
    private void inicializarFerramentas() {
        campoPesquisa = new JTextField("Digite o equipamento ou técnico...");
        util.estilizarField(campoPesquisa, "");
        campoPesquisa.setBounds(731, 84, 263, 32);
        painelPrincipal.add(campoPesquisa);

        botaoCriarOS = new JButton("Nova OS");
        util.estilizarBotao(botaoCriarOS);
        botaoCriarOS.setBounds(10, 93, 194, 23);
        botaoCriarOS.setFocusPainted(false);
        painelPrincipal.add(botaoCriarOS);
    }

    /**
     * Inicializa os listeners de eventos para os componentes da interface.
     */
    private void inicializarListeners() {
        botaoCriarOS.addActionListener(e -> {
            if (!osDAO.getStatusConnection()) {
                util.AvisoDeConexão();
                return;
            }
            CriarOrdemServico frame = new CriarOrdemServico(user);
            frame.setVisible(true);
        });

        campoPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campoPesquisa.getText().equals("Digite o equipamento ou técnico...")) {
                    campoPesquisa.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campoPesquisa.getText().isEmpty()) {
                    campoPesquisa.setText("Digite o equipamento ou técnico...");
                    carregarOS();
                }
            }
        });

        campoPesquisa.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarOS(campoPesquisa.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarOS(campoPesquisa.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarOS(campoPesquisa.getText().trim());
            }
        });

        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                campoPesquisa.setText("Digite o equipamento ou técnico...");
                carregarOS();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {}
        });
    }

    /**
     * Carrega as ordens de serviço de acordo com o usuário logado (admin ou técnico).
     */
    private void carregarOS() {
        if (!osDAO.getStatusConnection()) {
            util.AvisoDeConexão();
            return;
        }
        listaTodasOS = isAdmin ? osDAO.listarTodasOs() : osDAO.listarOsComId(user.getId());
        atualizarTabela(listaTodasOS);
    }

    /**
     * Atualiza a tabela com os dados das ordens de serviço.
     * @param ordensServico Lista de ordens de serviço a serem exibidas.
     */
    private void atualizarTabela(List<OrdemServico> ordensServico) {
        String[] colunas = { "ID OS", "Data", "Equipamento", "Defeito", "Serviço", "Valor", "Cliente", "Técnico" };
        Object[][] dadosTabela = new Object[ordensServico.size()][colunas.length];
        for (int i = 0; i < ordensServico.size(); i++) {
            OrdemServico os = ordensServico.get(i);
            dadosTabela[i] = new Object[] { 
                os.getOs(), os.getData(), os.getEquipamento(), os.getDefeito(),
                os.getServico(), os.getValor(), os.getCliente(), os.getTecnico()
            };
        }

        modeloTabela.setDataVector(dadosTabela, colunas);
    }

    /**
     * Filtra as ordens de serviço de acordo com o texto digitado no campo de pesquisa.
     * @param textoPesquisa Texto a ser utilizado para filtrar as ordens de serviço.
     */
    private void filtrarOS(String textoPesquisa) {
        if (textoPesquisa.trim().isEmpty()) {
            carregarOS();
            return;
        }

        osFiltradas.clear();

        for (OrdemServico os : listaTodasOS) {
            if ((os.getEquipamento() != null && os.getEquipamento().toLowerCase().contains(textoPesquisa.toLowerCase()))
                || (os.getTecnico() != null && os.getTecnico().toLowerCase().contains(textoPesquisa.toLowerCase()))
                || (os.getCliente() != null && os.getCliente().toLowerCase().contains(textoPesquisa.toLowerCase()))) {
                osFiltradas.add(os);
            }
        }

        atualizarTabela(osFiltradas);
    }
}