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

	private Cliente cli;

	private ScreenTools util = new ScreenTools();
	private CentroClientesDAO clienteDAO = new CentroClientesDAO();

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				DetalhesOS frame = new DetalhesOS();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public DetalhesOS() {
		// Configuração da janela
		setTitle("Detalhes da Ordem de Serviço");
		setIconImage(util.getLogo()); // Adapte o caminho da imagem
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 540);
		setResizable(false);

		// Painel de conteúdo
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setBackground(Color.WHITE); // Cor de fundo
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Título principal
		JLabel lblTitulo = new JLabel("Detalhes da Ordem de Serviço");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(0, 10, 1020, 40);
		contentPane.add(lblTitulo);

		// ID
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Arial", Font.PLAIN, 16));
		lblId.setBounds(29, 61, 100, 25);
		contentPane.add(lblId);

		idField = new JTextField();
		idField.setFont(new Font("Arial", Font.PLAIN, 16));
		idField.setBounds(139, 61, 250, 30);
		idField.setEditable(false); // Somente leitura
		contentPane.add(idField);

		// Data
		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Arial", Font.PLAIN, 16));
		lblData.setBounds(587, 61, 100, 25);
		contentPane.add(lblData);

		dataField = new JTextField();
		dataField.setFont(new Font("Arial", Font.PLAIN, 16));
		dataField.setBounds(697, 61, 250, 30);
		dataField.setEditable(false); // Somente leitura
		contentPane.add(dataField);

		// Cliente
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCliente.setBounds(29, 274, 100, 25);
		contentPane.add(lblCliente);

		clienteField = new JTextField();
		clienteField.setFont(new Font("Arial", Font.PLAIN, 16));
		clienteField.setBounds(139, 274, 250, 30);
		clienteField.setEditable(false); // Somente leitura
		contentPane.add(clienteField);

		// Fone
		JLabel lblFone = new JLabel("Fone:");
		lblFone.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFone.setBounds(587, 274, 100, 25);
		contentPane.add(lblFone);

		foneField = new JTextField();
		foneField.setFont(new Font("Arial", Font.PLAIN, 16));
		foneField.setBounds(697, 274, 250, 30);
		foneField.setEditable(false); // Somente leitura
		contentPane.add(foneField);

		// Aparelho
		JLabel lblAparelho = new JLabel("Aparelho:");
		lblAparelho.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAparelho.setBounds(29, 110, 100, 25);
		contentPane.add(lblAparelho);

		aparelhoField = new JTextField();
		aparelhoField.setFont(new Font("Arial", Font.PLAIN, 16));
		aparelhoField.setBounds(139, 110, 250, 30);
		aparelhoField.setEditable(false); // Somente leitura
		contentPane.add(aparelhoField);

		// Defeito
		JLabel lblDefeito = new JLabel("Defeito:");
		lblDefeito.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDefeito.setBounds(587, 106, 100, 25);
		contentPane.add(lblDefeito);

		defeitoField = new JTextField();
		defeitoField.setFont(new Font("Arial", Font.PLAIN, 16));
		defeitoField.setBounds(697, 106, 250, 30);
		defeitoField.setEditable(false); // Somente leitura
		contentPane.add(defeitoField);

		// Serviço
		JLabel lblServico = new JLabel("Serviço:");
		lblServico.setFont(new Font("Arial", Font.PLAIN, 16));
		lblServico.setBounds(29, 160, 100, 25);
		contentPane.add(lblServico);

		servicoField = new JTextField();
		servicoField.setFont(new Font("Arial", Font.PLAIN, 16));
		servicoField.setBounds(139, 160, 250, 30);
		servicoField.setEditable(false); // Somente leitura
		contentPane.add(servicoField);

		// Valor
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Arial", Font.PLAIN, 16));
		lblValor.setBounds(587, 151, 100, 25);
		contentPane.add(lblValor);

		valorField = new JTextField();
		valorField.setFont(new Font("Arial", Font.PLAIN, 16));
		valorField.setBounds(697, 151, 250, 30);
		valorField.setEditable(false); // Somente leitura
		contentPane.add(valorField);

		// Botão de confirmação
		JButton btnOk = new JButton("Fechar");
		btnOk.setFont(new Font("Arial", Font.PLAIN, 14));
		btnOk.setBounds(449, 461, 120, 40);
		contentPane.add(btnOk);

		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEndereco.setBounds(29, 333, 100, 25);
		contentPane.add(lblEndereco);

		enderecoField = new JTextField();
		enderecoField.setFont(new Font("Arial", Font.PLAIN, 16));
		enderecoField.setEditable(false);
		enderecoField.setBounds(139, 333, 250, 30);
		contentPane.add(enderecoField);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEmail.setBounds(587, 338, 100, 25);
		contentPane.add(lblEmail);
		
		EmailField = new JTextField();
		EmailField.setFont(new Font("Arial", Font.PLAIN, 16));
		EmailField.setEditable(false);
		EmailField.setBounds(697, 338, 250, 30);
		contentPane.add(EmailField);
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Fechar a janela
			}
		});

		setLocationRelativeTo(null); // Centralizar a janela
	}

	// Método para exibir os detalhes
	public void exibirDetalhes(OrdemServico os) {
		// Exibir detalhes na interface gráfica
		idField.setText(String.valueOf(os.getOs()));
		dataField.setText(os.getData());
		clienteField.setText(os.getCliente());
		foneField.setText(os.getContato());
		aparelhoField.setText(os.getEquipamento());
		defeitoField.setText(os.getDefeito());
		servicoField.setText(os.getServico());
		valorField.setText(String.valueOf(os.getValor()));

		// Carregar os dados do cliente
		cli = clienteDAO.pesquisarClientePorId(os.getIdCliente());
		if (cli != null) {
			enderecoField.setText(cli.getEndereco());
			EmailField.setText(cli.getEmail());
		}

		// Imprimir os valores no console
		System.out.println("ID: " + os.getOs());
		System.out.println("Data: " + os.getData());
		System.out.println("Cliente: " + os.getCliente());
		System.out.println("Contato: " + os.getContato());
		System.out.println("Equipamento: " + os.getEquipamento());
		System.out.println("Defeito: " + os.getDefeito());
		System.out.println("Serviço: " + os.getServico());
		System.out.println("Valor: " + os.getValor());
		System.out.println("IdCliente: " + os.getIdCliente());
		System.out.println("Endereço do cliente: " + (cli != null ? cli.getEndereco() : "N/A"));
		System.out.println("Email do cliente: " + (cli != null ? cli.getEmail() : "N/A"));
	}
}
