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
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.OrdemServico;
import br.com.SistemaOS.modelo.Usuario;

public class GerenciarOS extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel painelPrincipal;

	private JTextField campoPesquisa;
	private JButton botaoCriarOS;

	private JTable tabelaOS;
	private DefaultTableModel modeloTabela;

	private ScreenTools screenTools = new ScreenTools();
	private CentroOSDAO osDAO = new CentroOSDAO();

	private Usuario usuarioLogado;
	private List<OrdemServico> listaTodasOS;
	private List<OrdemServico> osFiltradas = new ArrayList<>();

	public GerenciarOS(Usuario user) {
		this.usuarioLogado = user;
		setResizable(false);
		setTitle("Ordens de Serviço");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 540);
		setLocationRelativeTo(null);
		setIconImage(screenTools.getLogo());

		painelPrincipal = new JPanel();
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setBackground(screenTools.getBackgroundColor());
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		inicializarComponentes();
		carregarOS();
	}

	private void inicializarComponentes() {
		inicializarLabels();
		inicializarTabela();
		inicializarFerramentas();
		inicializarListeners();
	}

	private void inicializarLabels() {
		painelPrincipal
				.add(screenTools.criarLabel("Ordens de Serviço", (this.getWidth() - 200) / 2, 11, 200, 47, 30, true));
	}

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
					JOptionPane.showMessageDialog(null, "Detalhes da OS ainda não implementados.");
				}
			}
		});
	}

	private void inicializarFerramentas() {
		campoPesquisa = new JTextField("Digite o equipamento ou técnico...");
		screenTools.estilizarField(campoPesquisa, "");
		campoPesquisa.setBounds(731, 84, 263, 32);
		painelPrincipal.add(campoPesquisa);

		botaoCriarOS = new JButton("Nova OS");
		screenTools.estilizarBotao(botaoCriarOS);
		botaoCriarOS.setBounds(10, 93, 194, 23);
		botaoCriarOS.setFocusPainted(false);
		painelPrincipal.add(botaoCriarOS);
	}

	private void inicializarListeners() {
		botaoCriarOS.addActionListener(e -> {
			CriarOrdemServico frame = new CriarOrdemServico(usuarioLogado);
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
			public void windowLostFocus(WindowEvent e) {
			}
		});
	}

	private void carregarOS() {
		listaTodasOS = osDAO.listarTodasOS(usuarioLogado.getId());
		atualizarTabela(listaTodasOS);
	}

	private void atualizarTabela(List<OrdemServico> ordensServico) {
		String[] colunas = { "ID OS", "Data", "Equipamento", "Defeito", "Valor", "Cliente", "Técnico" };
		Object[][] dadosTabela = new Object[ordensServico.size()][colunas.length];

		for (int i = 0; i < ordensServico.size(); i++) {
			OrdemServico os = ordensServico.get(i);
			dadosTabela[i] = new Object[] { os.getOs(), os.getData(), os.getEquipamento(), os.getDefeito(),
					os.getValor(), os.getCliente(), os.getTecnico() };
		}

		modeloTabela.setDataVector(dadosTabela, colunas);
	}

	private void filtrarOS(String textoPesquisa) {
		if (textoPesquisa.trim().isEmpty()) {
			carregarOS();
			return;
		}


		for (OrdemServico os : listaTodasOS) {
			if ((os.getEquipamento() != null && os.getEquipamento().toLowerCase().contains(textoPesquisa.toLowerCase()))
					|| (os.getTecnico() != null
							&& os.getTecnico().toLowerCase().contains(textoPesquisa.toLowerCase()))
					|| (os.getCliente() != null
							&& os.getCliente().toLowerCase().contains(textoPesquisa.toLowerCase()))) {
				osFiltradas.add(os);
			}
		}

		atualizarTabela(osFiltradas);
	}
}
