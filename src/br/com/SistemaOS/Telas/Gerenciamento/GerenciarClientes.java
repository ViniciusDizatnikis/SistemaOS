package br.com.SistemaOS.Telas.Gerenciamento;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.Telas.Criar.CriarCliente;
import br.com.SistemaOS.Telas.Detalhes.DetalhesCliente;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.Cliente;

/**
 * Tela para gerenciar os clientes do sistema.
 */
public class GerenciarClientes extends JFrame {

    private static final long serialVersionUID = 1L;

    /** Painel principal que contém todos os componentes da interface. */
    private JPanel painelPrincipal;

    /** Campo de texto para pesquisa de clientes. */
    private JTextField campoPesquisa;

    /** Botão para adicionar um novo cliente. */
    private JButton botaoCriarCliente;

    /** Tabela que exibe a lista de clientes. */
    private JTable tabelaClientes;
    /** Modelo da tabela que define a estrutura dos dados exibidos. */
    private DefaultTableModel modeloTabela;

    /** Utilitário para manipulação de elementos da tela. */
    private ScreenTools util = new ScreenTools();
    /** DAO para operações relacionadas aos clientes. */
    private CentroClientesDAO clienteDAO = new CentroClientesDAO();

    /** Lista de todos os clientes cadastrados no sistema. */
    private List<Cliente> listaTodosClientes;
    /** Lista de clientes filtrados com base na pesquisa. */
    private List<Cliente> clientesFiltrados = new ArrayList<>();

    /**
     * Construtor da tela GerenciarClientes.
     */
    public GerenciarClientes() {
        setResizable(false);
        setTitle("Clientes");
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
        carregarClientes();
    }

    /**
     * Inicializa os componentes da tela.
     */
    private void inicializarComponentes() {
        inicializarLabels();
        inicializarTabela();
        inicializarFerramentas();
        inicializarListeners();
    }

    /**
     * Inicializa os labels da tela.
     */
    private void inicializarLabels() {
        painelPrincipal.add(util.criarLabel("Clientes", 429, 11, 139, 47, 30, true));
    }

    /**
     * Inicializa a tabela de clientes.
     */
    private void inicializarTabela() {
        modeloTabela = new DefaultTableModel();
        tabelaClientes = new JTable(modeloTabela) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane painelRolagem = new JScrollPane(tabelaClientes);
        painelRolagem.setBounds(10, 127, 984, 368);
        painelPrincipal.add(painelRolagem);

        tabelaClientes.setRowHeight(25);
        tabelaClientes.setFillsViewportHeight(true);
        tabelaClientes.getTableHeader().setReorderingAllowed(false);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && tabelaClientes.getSelectedRow() != -1) {
                    Cliente clienteSelecionado;
                    int linhaSelecionada = tabelaClientes.getSelectedRow();

                    if (campoPesquisa.getText().equals("Digite o nome do cliente...") || campoPesquisa.getText().trim().isEmpty()) {
                        clienteSelecionado = listaTodosClientes.get(linhaSelecionada);
                    } else {
                        clienteSelecionado = clientesFiltrados.get(linhaSelecionada);
                    }

                    if (!clienteDAO.getStatusConnection()) {
                        util.AvisoDeConexão();
                        return;
                    }
                    DetalhesCliente telaDetalhes = new DetalhesCliente(clienteSelecionado.getId(),
                            clienteSelecionado.getNome(), clienteSelecionado.getFone(),
                            clienteSelecionado.getEndereco(), clienteSelecionado.getEmail());
                    telaDetalhes.setVisible(true);
                }
            }
        });
    }

    /**
     * Inicializa os campos de entrada e botões da tela.
     */
    private void inicializarFerramentas() {
        campoPesquisa = new JTextField("Digite o nome do cliente...");
        util.estilizarField(campoPesquisa, "");
        campoPesquisa.setBounds(731, 84, 263, 32);
        painelPrincipal.add(campoPesquisa);

        botaoCriarCliente = new JButton("Adicionar Cliente");
        util.estilizarBotao(botaoCriarCliente);
        botaoCriarCliente.setBounds(10, 93, 194, 23);
        botaoCriarCliente.setFocusPainted(false);
        painelPrincipal.add(botaoCriarCliente);
    }

    /**
     * Inicializa os listeners para os componentes da tela.
     */
    private void inicializarListeners() {
        botaoCriarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botaoCriarCliente.setBackground(new Color(33, 116, 133));
                botaoCriarCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                botaoCriarCliente.setBackground(new Color(63, 182, 207));
                botaoCriarCliente.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        botaoCriarCliente.addActionListener(e -> {
            if (!clienteDAO.getStatusConnection()) {
                util.AvisoDeConexão();
                return;
            }
            CriarCliente frame = new CriarCliente();
            frame.setVisible(true);
        });

        campoPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campoPesquisa.getText().equals("Digite o nome do cliente...")) {
                    campoPesquisa.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campoPesquisa.getText().isEmpty()) {
                    campoPesquisa.setText("Digite o nome do cliente...");
                    carregarClientes();
                }
            }
        });

        campoPesquisa.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarClientes(campoPesquisa.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarClientes(campoPesquisa.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarClientes(campoPesquisa.getText().trim());
            }
        });

        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                campoPesquisa.setText("Digite o nome do cliente...");
                carregarClientes();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
            }
        });
    }

    /**
     * Carrega todos os clientes do banco de dados.
     */
    private void carregarClientes() {
        if (!clienteDAO.getStatusConnection()) {
            util.AvisoDeConexão();
            return;
        }
        listaTodosClientes = clienteDAO.getClientes();
        atualizarTabela(listaTodosClientes);
    }

    /**
     * Atualiza a tabela com a lista de clientes.
     *
     * @param clientes A lista de clientes a ser exibida.
     */
    private void atualizarTabela(List<Cliente> clientes) {
        String[] colunas = { "ID", "Nome", "Endereço", "Telefone", "Email" };
        Object[][] dadosTabela = new Object[clientes.size()][colunas.length];

        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            dadosTabela[i] = new Object[] { cliente.getId(), cliente.getNome(),
                    cliente.getEndereco().isEmpty() ? "Não Informado" : cliente.getEndereco(), cliente.getFone(),
                    cliente.getEmail().isEmpty() ? "Não Informado" : cliente.getEmail() };
        }

        modeloTabela.setDataVector(dadosTabela, colunas);
    }

    /**
     * Filtra os clientes com base no texto de pesquisa.
     *
     * @param textoPesquisa O texto usado para filtrar os clientes.
     */
    private void filtrarClientes(String textoPesquisa) {
        clientesFiltrados.clear();

        if (textoPesquisa.trim().isEmpty() || textoPesquisa.equals("Digite o nome do cliente...")) {
            carregarClientes();
        } else {
            for (Cliente cliente : listaTodosClientes) {
                if (cliente.getNome().toLowerCase().contains(textoPesquisa.toLowerCase())
                        || cliente.getEndereco().toLowerCase().contains(textoPesquisa.toLowerCase())
                        || cliente.getFone().toLowerCase().contains(textoPesquisa.toLowerCase())
                        || cliente.getEmail().toLowerCase().contains(textoPesquisa.toLowerCase())) {
                    clientesFiltrados.add(cliente);
                }
            }

            atualizarTabela(clientesFiltrados);
        }
    }
}