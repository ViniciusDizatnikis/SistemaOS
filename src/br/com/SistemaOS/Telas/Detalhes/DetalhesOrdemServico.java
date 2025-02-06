package br.com.SistemaOS.Telas.Detalhes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.DAO.CentroOSDAO;
import br.com.SistemaOS.DAO.RelatoriosDAO;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.Cliente;
import br.com.SistemaOS.modelo.OrdemServico;

import java.awt.*;

/**
 * Classe que representa a tela de detalhes da ordem de serviço. Esta classe
 * permite visualizar e editar as informações de uma ordem de serviço, incluindo
 * dados do cliente e do serviço. Além disso, é possível excluir a ordem ou
 * imprimir uma via do relatório.
 */
public class DetalhesOrdemServico extends JFrame {

    private static final long serialVersionUID = 1L;

    /** Painel principal que contém todos os componentes da interface. */
    private JPanel contentPane;

    // Componentes
    /** Campo de texto para o ID da ordem de serviço. */
    private JTextField idField;
    /** Campo de texto para a data da ordem de serviço. */
    private JTextField dataField;
    /** Campo de texto para o nome do cliente. */
    private JTextField clienteField;
    /** Campo de texto para o telefone do cliente. */
    private JTextField foneField;
    /** Campo de texto para o aparelho relacionado à ordem de serviço. */
    private JTextField aparelhoField;
    /** Campo de texto para o defeito relatado. */
    private JTextField defeitoField;
    /** Campo de texto para o valor do serviço. */
    private JTextField valorField;
    /** Campo de texto para o serviço a ser realizado. */
    private JTextField servicoField;
    /** Campo de texto para o endereço do cliente. */
    private JTextField enderecoField;
    /** Campo de texto para o email do cliente. */
    private JTextField emailField;
    /** Botão principal para salvar ou fechar a tela. */
    private JButton btnOk;

    // Valores
    /** Armazena o nome do aparelho. */
    private String aparelho;
    /** Armazena o tipo de serviço. */
    private String servico;
    /** Armazena o defeito relatado. */
    private String defeito;
    /** Armazena o valor do serviço. */
    private String valor;

    /** Indica se a tela está em modo de edição. */
    private boolean edit;

    /** Armazena as informações do cliente associado à ordem de serviço. */
    private Cliente cli;

    // Dependências
    /** Utilitário para manipulação de elementos da tela. */
    private ScreenTools util = new ScreenTools();
    /** DAO para operações relacionadas aos clientes. */
    private CentroClientesDAO clienteDAO = new CentroClientesDAO();
    /** DAO para operações relacionadas às ordens de serviço. */
    private CentroOSDAO osDAO = new CentroOSDAO();
    /** DAO para operações relacionadas a relatórios. */
    private RelatoriosDAO relatorios = new RelatoriosDAO();

    /**
     * Construtor da classe. Inicializa a tela de detalhes da ordem de serviço.
     * 
     * @param edit Flag que indica se a ordem de serviço está em modo de edição.
     */
    public DetalhesOrdemServico(boolean edit) {
        this.edit = edit;

        // Configuração da janela
        setTitle("Detalhes da Ordem de Serviço");
        setIconImage(util.getLogo());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1020, 540);
        setResizable(false);

        // Painel de conteúdo
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(util.getBackgroundColor());
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);

        initLabels();
        initField();
        initButton();
        atualizarInfos();
        verificacaoDeEditavel();
    }

    /**
     * Inicializa o botão principal da tela. Dependendo do modo de edição, o botão
     * exibe "Salvar e sair" ou "Fechar".
     */
    private void initButton() {
        String mensagem = edit ? "Salvar e sair" : "Fechar";
        int widthBtn = edit ? 160 : 120;
        int positionBtnOk = edit ? (this.getWidth() / 2) - 80 : (this.getWidth() - widthBtn) / 2;

        btnOk = new JButton(mensagem);
        util.estilizarBotao(btnOk);
        btnOk.setBounds(positionBtnOk, 450, widthBtn, 40);
        contentPane.add(btnOk);

        if (edit) {
            JButton btnExcluir = new JButton("Excluir");
            util.estilizarBotao(btnExcluir);
            btnExcluir.setBounds((this.getWidth() / 2) - 210, 450, 120, 40);
            contentPane.add(btnExcluir);
            btnExcluir.addActionListener(e -> excluirOs());

            JButton btnImprimir = new JButton("Imprimir Via");
            util.estilizarBotao(btnImprimir);
            btnImprimir.setBounds((this.getWidth() / 2) + 90, 450, 120, 40);
            contentPane.add(btnImprimir);
            btnImprimir.addActionListener(e -> imprimirVia());
        }

        btnOk.addActionListener(e -> {
            if (edit) {
                verificarAtualizacao();
            } else {
                this.dispose();
            }
        });
    }

    /**
     * Inicializa os rótulos (labels) da tela.
     */
    private void initLabels() {
        // Título principal
        JLabel lblTitulo = new JLabel("Detalhes da Ordem de Serviço");
        util.estilizarLabel(lblTitulo, 24, true);
        lblTitulo.setBounds(0, 10, 1004, 40);
        contentPane.add(lblTitulo);

        // ID
        JLabel lblId = new JLabel("ID:");
        util.estilizarLabel(lblId, 16, false);
        lblId.setBounds(75, 92, 30, 25);
        contentPane.add(lblId);

        // Data
        JLabel lblData = new JLabel("Data:");
        lblData.setFont(new Font("Arial", Font.PLAIN, 16));
        util.estilizarLabel(lblData, 16, false);
        lblData.setBounds(287, 90, 48, 25);
        contentPane.add(lblData);

        // Aparelho
        JLabel lblAparelho = new JLabel("Aparelho:");
        util.estilizarLabel(lblAparelho, 16, false);
        lblAparelho.setBounds(26, 139, 79, 25);
        contentPane.add(lblAparelho);

        // Defeito
        JLabel lblDefeito = new JLabel("Defeito:");
        util.estilizarLabel(lblDefeito, 16, false);
        lblDefeito.setBounds(537, 139, 67, 25);
        contentPane.add(lblDefeito);

        // Serviço
        JLabel lblServico = new JLabel("Serviço:");
        util.estilizarLabel(lblServico, 16, false);
        lblServico.setBounds(38, 186, 67, 25);
        contentPane.add(lblServico);

        // Cliente
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Arial", Font.PLAIN, 16));
        util.estilizarLabel(lblCliente, 16, false);
        lblCliente.setBounds(38, 310, 67, 25);
        contentPane.add(lblCliente);

        // Valor
        JLabel lblValor = new JLabel("Valor:");
        util.estilizarLabel(lblValor, 16, false);
        lblValor.setBounds(545, 187, 48, 25);
        contentPane.add(lblValor);

        // Clientes ---

        // Titulo
        JLabel lblClienteTtitle = new JLabel("Cliente");
        util.estilizarLabel(lblClienteTtitle, 25, true);
        lblClienteTtitle.setBounds(10, 248, 984, 40);
        contentPane.add(lblClienteTtitle);

        // Fone
        JLabel lblFone = new JLabel("Fone:");
        util.estilizarLabel(lblFone, 16, false);
        lblFone.setBounds(537, 311, 424, 25);
        contentPane.add(lblFone);

        // Endereço
        JLabel lblEndereco = new JLabel("Endereço:");
        util.estilizarLabel(lblEndereco, 16, false);
        lblEndereco.setBounds(26, 369, 79, 25);
        contentPane.add(lblEndereco);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        util.estilizarLabel(lblEmail, 16, false);
        lblEmail.setBounds(537, 372, 424, 25);
        contentPane.add(lblEmail);
    }

    /**
     * Inicializa os campos de texto (fields) da tela.
     */
    private void initField() {
        // ID
        idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 16));
        util.estilizarField(idField, getName());
        idField.setEnabled(false);
        idField.setBounds(115, 87, 162, 30);
        idField.setEnabled(false);
        contentPane.add(idField);

        // Data
        dataField = new JTextField();
        util.estilizarField(dataField, "");
        dataField.setBounds(345, 87, 182, 30);
        dataField.setEnabled(false);
        contentPane.add(dataField);

        // Aparelho
        aparelhoField = new JTextField();
        aparelhoField.setBounds(115, 136, 412, 30);
        util.estilizarField(aparelhoField, "");
        contentPane.add(aparelhoField);

        // Defeito
        defeitoField = new JTextField();
        util.estilizarField(defeitoField, "");
        defeitoField.setBounds(603, 137, 358, 30);
        contentPane.add(defeitoField);

        // serviço
        servicoField = new JTextField();
        util.estilizarField(servicoField, "");
        servicoField.setBounds(115, 184, 412, 30);
        contentPane.add(servicoField);

        // Valor
        valorField = new JTextField();
        util.estilizarField(valorField, "");
        valorField.setBounds(603, 184, 358, 30);
        contentPane.add(valorField);

        // Cliente---

        // Nome
        clienteField = new JTextField();
        util.estilizarField(clienteField, "");
        clienteField.setBounds(115, 309, 388, 30);
        clienteField.setEnabled(false);
        contentPane.add(clienteField);
        // Fone
        foneField = new JTextField();
        util.estilizarField(foneField, "");
        foneField.setBounds(595, 309, 366, 30);
        foneField.setEnabled(false);
        contentPane.add(foneField);

        // Endereço
        enderecoField = new JTextField();
        util.estilizarField(enderecoField, "");
        enderecoField.setEnabled(false);
        enderecoField.setBounds(115, 366, 388, 30);
        contentPane.add(enderecoField);

        // Email
        emailField = new JTextField();
        util.estilizarField(emailField, "");
        emailField.setEnabled(false);
        emailField.setBounds(595, 370, 366, 30);
        contentPane.add(emailField);

        valorField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    String text = valorField.getText();

                    text = text.replaceAll("[^\\d]", "");

                    if (text.length() > 2) {
                        text = text.substring(0, text.length() - 2) + "." + text.substring(text.length() - 2);
                    }

                    valorField.setText(text);

                    valorField.setCaretPosition(text.length());
                });
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

    }

    /**
     * Exclui a ordem de serviço atual após confirmação do usuário.
     */
    private void excluirOs() {
        if (idField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID da OS não informado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir esta OS?", "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                osDAO.excluirOs(idField.getText());
                JOptionPane.showMessageDialog(null, "OS excluída com sucesso!");
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir a OS: " + ex.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Habilita ou desabilita os campos de edição, dependendo do modo.
     */
    private void verificacaoDeEditavel() {
        aparelhoField.setEnabled(edit);
        defeitoField.setEnabled(edit);
        servicoField.setEnabled(edit);
        valorField.setEnabled(edit);
    }

    /**
     * Imprime a via do relatório de serviço com base no ID da ordem de serviço.
     */
    private void imprimirVia() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            relatorios.getRelatorioServicoId(id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido! Certifique-se de que é um número.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza as informações da ordem de serviço com os dados mais recentes.
     */
    private void atualizarInfos() {
        aparelho = aparelhoField.getText().trim();
        defeito = defeitoField.getText().trim();
        servico = servicoField.getText().trim();
        valor = valorField.getText().trim();
    }

    /**
     * Verifica se houve alterações nos campos de edição e, em caso afirmativo,
     * salva as atualizações.
     */
    private void verificarAtualizacao() {
        boolean alterado = false;

        if (aparelhoField.getText().trim().isEmpty() || defeitoField.getText().trim().isEmpty()
                || servicoField.getText().trim().isEmpty() || valorField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!aparelho.equals(aparelhoField.getText().trim())) {
            aparelho = aparelhoField.getText().trim();
            alterado = true;
        }

        if (!defeito.equals(defeitoField.getText().trim())) {
            defeito = defeitoField.getText().trim();
            alterado = true;
        }

        if (!servico.equals(servicoField.getText().trim())) {
            servico = servicoField.getText().trim();
            alterado = true;
        }

        if (!valor.equals(valorField.getText().trim())) {
            valor = valorField.getText().trim();
            alterado = true;
        }

        if (alterado) {
            osDAO.atualizarOS(idField.getText(), aparelho, defeito, servico, valor);
            this.dispose();
        }
    }

    /**
     * Exibe os detalhes de uma ordem de serviço na interface gráfica.
     * 
     * @param os A ordem de serviço cujos detalhes devem ser exibidos.
     */
    public void exibirDetalhes(OrdemServico os) {
        idField.setText(String.valueOf(os.getOs()));
        dataField.setText(os.getData());
        clienteField.setText(os.getCliente());
        foneField.setText(os.getContato());
        aparelhoField.setText(os.getEquipamento());
        defeitoField.setText(os.getDefeito());
        servicoField.setText(os.getServico());
        valorField.setText(String.valueOf(os.getValor()));

        cli = clienteDAO.pesquisarClientePorId(os.getIdCliente());
        if (cli != null) {
            enderecoField.setText(cli.getEndereco());
            emailField.setText(cli.getEmail());
        }
    }
}