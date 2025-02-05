package br.com.SistemaOS.Telas.Detalhes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import br.com.SistemaOS.DAO.CentroClientesDAO;
import br.com.SistemaOS.Utils.ScreenTools;
import br.com.SistemaOS.modelo.Cliente;
import br.com.SistemaOS.modelo.OrdemServico;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetalhesOS extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField;
	private JTextField dataField;
	private JTextField clienteField;
	private JTextField foneField;
	private JTextField aparelhoField;
	private JTextField defeitoField;
	private JTextField valorField;
	private JTextField servicoField;
	private JTextField enderecoField;
	private JTextField EmailField;

	private boolean edit = false;

	private Cliente cli;

	private ScreenTools util = new ScreenTools();
	private CentroClientesDAO clienteDAO = new CentroClientesDAO();

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				DetalhesOS frame = new DetalhesOS(false);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public DetalhesOS(boolean edit) {
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

		// Título principal
		JLabel lblTitulo = new JLabel("Detalhes da Ordem de Serviço");
		util.estilizarLabel(lblTitulo, 24, true);
		lblTitulo.setBounds(10, 10, 984, 40);
		contentPane.add(lblTitulo);

		// ID
		JLabel lblId = new JLabel("ID:");
		util.estilizarLabel(lblId, 16, false);
		lblId.setBounds(75, 92, 30, 25);
		contentPane.add(lblId);

		idField = new JTextField();
		idField.setFont(new Font("Arial", Font.PLAIN, 16));
		util.estilizarField(idField, getName());
		idField.setEnabled(false);
		idField.setBounds(115, 87, 162, 30);
		idField.setEditable(false);
		contentPane.add(idField);

		// Data
		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Arial", Font.PLAIN, 16));
		util.estilizarLabel(lblData, 16, false);
		lblData.setBounds(287, 90, 48, 25);
		contentPane.add(lblData);

		dataField = new JTextField();
		util.estilizarField(dataField, "");
		dataField.setBounds(345, 87, 182, 30);
		dataField.setEnabled(false);
		dataField.setEditable(false);
		contentPane.add(dataField);

		// Aparelho
		JLabel lblAparelho = new JLabel("Aparelho:");
		util.estilizarLabel(lblAparelho, 16, false);
		lblAparelho.setBounds(26, 139, 79, 25);
		contentPane.add(lblAparelho);

		aparelhoField = new JTextField();
		aparelhoField.setBounds(115, 136, 412, 30);
		aparelhoField.setEditable(false);
		util.estilizarField(aparelhoField, "");
		contentPane.add(aparelhoField);

		// Defeito
		JLabel lblDefeito = new JLabel("Defeito:");
		util.estilizarLabel(lblDefeito, 16, false);
		lblDefeito.setBounds(537, 139, 67, 25);
		contentPane.add(lblDefeito);

		defeitoField = new JTextField();
		util.estilizarField(defeitoField, "");
		defeitoField.setBounds(603, 137, 347, 30);
		defeitoField.setEditable(false);
		contentPane.add(defeitoField);

		// Serviço
		JLabel lblServico = new JLabel("Serviço:");
		util.estilizarLabel(lblServico, 16, false);
		lblServico.setBounds(38, 186, 67, 25);
		contentPane.add(lblServico);

		servicoField = new JTextField();
		util.estilizarField(servicoField, "");
		servicoField.setBounds(115, 184, 412, 30);
		servicoField.setEditable(false);
		contentPane.add(servicoField);

		// Cliente
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 16));
		util.estilizarLabel(lblCliente, 16, false);
		lblCliente.setBounds(38, 310, 67, 25);
		contentPane.add(lblCliente);

		clienteField = new JTextField();
		util.estilizarField(clienteField, "");
		clienteField.setBounds(115, 309, 388, 30);
		clienteField.setEditable(false);
		contentPane.add(clienteField);

		// Valor
		JLabel lblValor = new JLabel("Valor:");
		util.estilizarLabel(lblValor, 16, false);
		lblValor.setBounds(545, 187, 48, 25);
		contentPane.add(lblValor);

		valorField = new JTextField();
		util.estilizarField(valorField, "");
		valorField.setBounds(603, 184, 347, 30);
		valorField.setEditable(false);
		contentPane.add(valorField);

		// Cliente----

		JLabel lblClienteTtitle = new JLabel("Cliente");
		util.estilizarLabel(lblClienteTtitle, 25, true);
		lblClienteTtitle.setBounds(10, 248, 984, 40);
		contentPane.add(lblClienteTtitle);

		// Fone
		JLabel lblFone = new JLabel("Fone:");
		util.estilizarLabel(lblFone, 16, false);
		lblFone.setBounds(537, 311, 424, 25);
		contentPane.add(lblFone);

		foneField = new JTextField();
		util.estilizarField(foneField, "");
		foneField.setBounds(595, 309, 366, 30);
		foneField.setEditable(false);
		contentPane.add(foneField);

		// Endereço
		JLabel lblEndereco = new JLabel("Endereço:");
		util.estilizarLabel(lblEndereco, 16, false);
		lblEndereco.setBounds(26, 369, 79, 25);
		contentPane.add(lblEndereco);

		enderecoField = new JTextField();
		util.estilizarField(enderecoField, "");
		enderecoField.setEditable(false);
		enderecoField.setBounds(115, 366, 388, 30);
		contentPane.add(enderecoField);

		// Email
		JLabel lblEmail = new JLabel("Email:");
		util.estilizarLabel(lblEmail, 16, false);
		lblEmail.setBounds(537, 372, 424, 25);
		contentPane.add(lblEmail);

		EmailField = new JTextField();
		util.estilizarField(EmailField, "");
		EmailField.setEditable(false);
		EmailField.setBounds(595, 370, 366, 30);
		contentPane.add(EmailField);

		// Botão de confirmação
		JButton btnOk = new JButton("Fechar");
		util.estilizarBotao(btnOk);
		btnOk.setBounds((this.getWidth() - 120) / 2, 450, 120, 40);
		contentPane.add(btnOk);

		verificacaoDeEditavel();

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setLocationRelativeTo(null);
	}

	private void verificacaoDeEditavel() {
		aparelhoField.setEnabled(edit);
		defeitoField.setEnabled(edit);
		servicoField.setEnabled(edit);
		valorField.setEditable(edit);
	}

	// Método para exibir os detalhes
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
			EmailField.setText(cli.getEmail());
		}
	}
}
