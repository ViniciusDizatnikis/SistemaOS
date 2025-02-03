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

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.DAO.CentroOSDAO;
import br.com.SistemaOS.Telas.Criar.CriarCliente;
import br.com.SistemaOS.Telas.Detalhes.DetalhesCliente;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.Cliente;
import br.com.SistemaOS.modelo.Usuario;

public class GerenciarOS extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPrincipal;

    // Campo de Pesquisa
    private JTextField campoPesquisa;

    // Botões
    private JButton botaoCriarCliente;

    // Tabela de Clientes
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;

    // Utilitários
    private ScreenTools screenTools = new ScreenTools();
    private CentroOSDAO dao = new CentroOSDAO();

    // Usuário
    private Usuario usuarioLogado;
    
    private ScreenTools util = new ScreenTools();
    

    // Lista de clientes para otimização
    private List<Cliente> listaTodosClientes;

    public GerenciarOS(Usuario user) {
    	this.usuarioLogado = user;
        setResizable(false);
        setTitle("Serviços");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1020, 540);
        setLocationRelativeTo(null);

        painelPrincipal = new JPanel();
        painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        painelPrincipal.setBackground(screenTools.getBackgroundColor());
        setContentPane(painelPrincipal);
        painelPrincipal.setLayout(null);

        inicializarComponentes();

        // Carrega os dados na inicialização
        carregarOS();
    }

    private void inicializarComponentes() {
        inicializarLabels();
        inicializarTabela();
        inicializarFerramentas();
        inicializarListeners();
    }

    private void inicializarLabels() {
        painelPrincipal.add(screenTools.criarLabel("Seus Serviços", (this.getWidth()-200)/2, 11, 200, 47, 30, true));
    }

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
                    int linhaSelecionada = tabelaClientes.getSelectedRow();
                    Cliente clienteSelecionado = listaTodosClientes.get(linhaSelecionada);
                    DetalhesCliente telaDetalhes = new DetalhesCliente(
                        clienteSelecionado.getId(),
                        clienteSelecionado.getNome(),
                        clienteSelecionado.getFone(),
                        clienteSelecionado.getEndereco(),
                        clienteSelecionado.getEmail()
                    );
                    telaDetalhes.setVisible(true);
                }
            }
        });
    }

    private void inicializarFerramentas() {
    	
        campoPesquisa = new JTextField("Digite o nome do cliente...");
        util.estilizarField(campoPesquisa, "");
        campoPesquisa.setBounds(731, 84, 263, 32);
        painelPrincipal.add(campoPesquisa);

        botaoCriarCliente = new JButton("Novo Serviço");
        util.estilizarBotao(botaoCriarCliente);
        botaoCriarCliente.setBounds(10, 93, 194, 23);
        botaoCriarCliente.setFocusPainted(false);
        painelPrincipal.add(botaoCriarCliente);
    }

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
                    carregarOS();
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
                carregarOS();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
            }
        });
    }

    private void carregarOS() {
    	
    }

    private void atualizarTabela(List<Cliente> clientes) {
        String[] colunas = { "ID", "Nome", "Endereço", "Telefone", "Email" };
        Object[][] dadosTabela = new Object[clientes.size()][colunas.length];

        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            dadosTabela[i] = new Object[] {
                cliente.getId(),
                cliente.getNome(),
                cliente.getEndereco(),
                cliente.getFone(),
                cliente.getEmail()
            };
        }

        modeloTabela.setDataVector(dadosTabela, colunas);
    }

    private void filtrarClientes(String textoPesquisa) {
        if (listaTodosClientes.equals("Digite o nome do cliente...") || textoPesquisa.trim().isEmpty()) carregarOS();

        List<Cliente> clientesFiltrados = new ArrayList<>();

        for (Cliente cliente : listaTodosClientes) {
            if (cliente.getNome().toLowerCase().contains(textoPesquisa.toLowerCase()) ||
                cliente.getEndereco().toLowerCase().contains(textoPesquisa.toLowerCase()) ||
                cliente.getFone().toLowerCase().contains(textoPesquisa.toLowerCase()) ||
                cliente.getEmail().toLowerCase().contains(textoPesquisa.toLowerCase())) {
                
                clientesFiltrados.add(cliente);
            }
        }

        atualizarTabela(clientesFiltrados); 
    }

    public static void main(String[] args) {
        GerenciarClientes telaClientes = new GerenciarClientes();
        telaClientes.setVisible(true);
    }
}
