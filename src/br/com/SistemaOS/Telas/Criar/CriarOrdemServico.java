package br.com.SistemaOS.Telas.Criar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.math.BigDecimal;
import java.util.List;

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.DAO.CentroOSDAO;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.Cliente;
import br.com.SistemaOS.modelo.Usuario;

public class CriarOrdemServico extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fieldEquipamento, fieldDefeito, fieldServico, fieldValor, fieldIdUser;
	private JLabel lblEquipamento, lblDefeito, lblServico, lblValor, lblIdCliente, lblIdUser;
	private CentroOSDAO dao = new CentroOSDAO();
	private CentroClientesDAO clienteDao = new CentroClientesDAO();
	private ScreenTools util = new ScreenTools();

	private Usuario user;
	private JComboBox<Object> comboBoxClientes;

	public CriarOrdemServico(Usuario user) {
		this.user = user;

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 540);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(util.getBackgroundColor());
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblCriarOrdem = util.criarLabel("Criar Ordem de Serviço", 0, 20, 1004, 50, 36, true);
		contentPane.add(lblCriarOrdem);

		// Equipamento
		lblEquipamento = util.criarLabel("Equipamento:", 50, 100, 150, 30, 20, false);
		contentPane.add(lblEquipamento);

		fieldEquipamento = new JTextField();
		util.estilizarField(fieldEquipamento, "");
		fieldEquipamento.setBounds(50, 140, 490, 35);
		contentPane.add(fieldEquipamento);

		// Defeito
		lblDefeito = util.criarLabel("Defeito:", 550, 100, 122, 30, 20, false);
		contentPane.add(lblDefeito);

		fieldDefeito = new JTextField();
		util.estilizarField(fieldDefeito, "");
		fieldDefeito.setBounds(550, 140, 400, 35);
		contentPane.add(fieldDefeito);

		// Serviço
		lblServico = util.criarLabel("Serviço:", 50, 200, 146, 30, 20, false);
		contentPane.add(lblServico);

		fieldServico = new JTextField();
		util.estilizarField(fieldServico, "");
		fieldServico.setBounds(50, 240, 490, 35);
		contentPane.add(fieldServico);

		// Valor
		lblValor = util.criarLabel("Valor (R$):", 550, 200, 146, 30, 20, false);
		contentPane.add(lblValor);

		fieldValor = new JTextField();
		util.estilizarField(fieldValor, "");
		fieldValor.setBounds(550, 240, 400, 35);
		contentPane.add(fieldValor);

		// Cliente ComboBox
		lblIdCliente = util.criarLabel("Cliente:", 50, 300, 122, 30, 20, false);
		contentPane.add(lblIdCliente);

		// ComboBox para selecionar o cliente
		comboBoxClientes = new JComboBox<>();
		comboBoxClientes.addItem("Selecione o cliente"); 
		List<Cliente> clientes = clienteDao.getClientes(); 
		for (Cliente cliente : clientes) {
		    comboBoxClientes.addItem(cliente);  
		}
		comboBoxClientes.setBounds(50, 340, 490, 35);
		contentPane.add(comboBoxClientes);


		// ID Usuário (somente leitura)
		lblIdUser = util.criarLabel("Tecnico:", 550, 300, 122, 30, 20, false);
		contentPane.add(lblIdUser);

		fieldIdUser = new JTextField();
		util.estilizarField(fieldIdUser, user.getNome());
		fieldIdUser.setEnabled(false);
		fieldIdUser.setBounds(550, 340, 400, 35);
		fieldIdUser.setEditable(false);
		contentPane.add(fieldIdUser);

		// Botão Criar Ordem
		JButton btnCriar = new JButton("Criar Ordem");
		util.estilizarBotao(btnCriar);
		btnCriar.setBounds(460, 420, 100, 40); 
		contentPane.add(btnCriar);

		btnCriar.addActionListener(e -> {
			String equipamento = fieldEquipamento.getText().trim();
			String defeito = fieldDefeito.getText().trim();
			String servico = fieldServico.getText().trim();
			String valor = fieldValor.getText().trim();

			BigDecimal valorDecimal = null;
			try {
				valorDecimal = new BigDecimal(valor);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Por favor, insira um valor válido.");
				return; 
			}

			Cliente clienteSelecionado = (Cliente) comboBoxClientes.getSelectedItem();
			Integer idCliente = clienteSelecionado.getId();

			if (isCampoVazio(equipamento, defeito, servico, valor, idCliente)) {
				JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
				return;
			}

			criarOrdemServico(equipamento, defeito, servico, valorDecimal, idCliente, user);
			JOptionPane.showMessageDialog(this, "Ordem de Serviço criada com sucesso!");
			this.dispose();
		});
	}

	public void criarOrdemServico(String equipamento, String defeito, String servico, BigDecimal valor,
			Integer idCliente, Usuario user) {
		dao.criarOS(equipamento, defeito, servico, valor, idCliente, user);
	}

	private boolean isCampoVazio(String equipamento, String defeito, String servico, String valor, Integer idCliente) {
		if (equipamento == null || equipamento.trim().isEmpty()) {
			return true;
		}
		if (defeito == null || defeito.trim().isEmpty()) {
			return true;
		}
		if (servico == null || servico.trim().isEmpty()) {
			return true;
		}
		if (valor == null || valor.trim().isEmpty()) {
			return true;
		}
		if (idCliente == null || idCliente < 1) {
			return true;
		}

		return false;
	}

}
