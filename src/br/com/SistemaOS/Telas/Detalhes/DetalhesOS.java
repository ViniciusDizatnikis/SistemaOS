	package br.com.SistemaOS.Telas.Detalhes;
	
	import java.awt.Color;
	import java.awt.EventQueue;
	import java.awt.Font;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.JTextField;
	import javax.swing.JLabel;
	import javax.swing.border.EmptyBorder;

import br.com.SistemaOS.Utils.ScreenTools;

import javax.swing.SwingConstants;
	import javax.swing.JButton;
	import java.awt.Toolkit;
	
	public class DetalhesOS extends JFrame {
	
	    private static final long serialVersionUID = 1L;
	    private JPanel contentPane;
	    private JTextField idField;
	    private JTextField dataField;
	    private JTextField clienteField;
	    private JTextField foneField;
	    private JTextField aparelhoField;
	    private JTextField defeitoField;
	    private JLabel lblTitulo;
	    private JTextField valorField;
	    private JTextField servicoField;
	    private ScreenTools util;
	    
	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    DetalhesOS frame = new DetalhesOS();
	                    frame.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
	
	    public DetalhesOS() {
	    	this.util = new ScreenTools();
	        setResizable(false);
	        setIconImage(Toolkit.getDefaultToolkit().getImage(DetalhesOS.class.getResource("/br/com/SistemaOS/Icones/icon/Logo.png")));
	        setTitle("Informações");
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setBounds(100, 100, 746, 380);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        contentPane.setBackground(util.getBackgroundColor());
	        setContentPane(contentPane);
	        contentPane.setLayout(null);
	
	        // Create fields and labels
	        addFieldAndLabel("ID:", 25, 82, idField = new JTextField());
	        addFieldAndLabel("Data:", 365, 82, dataField = new JTextField());
	        addFieldAndLabel("Cliente:", 25, 134, clienteField = new JTextField());
	        addFieldAndLabel("Fone:", 365, 134, foneField = new JTextField());
	        addFieldAndLabel("Aparelho:", 25, 185, aparelhoField = new JTextField());
	        addFieldAndLabel("Defeito:", 365, 185, defeitoField = new JTextField());
	        addFieldAndLabel("Valor:", 365, 236, valorField = new JTextField());
	        addFieldAndLabel("Serviço:", 25, 236, servicoField = new JTextField());
	
	        // Title label
	        lblTitulo = new JLabel("Informações");
	        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
	        lblTitulo.setForeground(Color.WHITE);
	        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
	        lblTitulo.setBounds(230, 11, 263, 37);
	        contentPane.add(lblTitulo);
	
	        // Ok Button
	        JButton btnOk = new JButton("Ok");
	        btnOk.setBounds(323, 304, 89, 23);
	        contentPane.add(btnOk);
	        btnOk.addActionListener(e -> this.dispose());  
	
	     
	        setLocationRelativeTo(null); 
	    }
	
	    private void addFieldAndLabel(String labelText, int labelX, int labelY, JTextField field) {
	        JLabel label = new JLabel(labelText);
	        label.setForeground(Color.WHITE);
	        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        label.setBounds(labelX, labelY, 67, 20);
	        contentPane.add(label);

	        field.setBounds(labelX + 67, labelY, 263, 25);
	        field.setEditable(false);
	        field.setBackground(new Color(240, 240, 240));  
	        field.setFocusable(false);  
	        contentPane.add(field);
	    }
	    
	    public void exibirDetalhes(Object[] dados) {
	        idField.setText(dados[0].toString());
	        dataField.setText(dados[1].toString());
	        clienteField.setText(dados[2].toString());
	        foneField.setText(dados[3].toString());
	        aparelhoField.setText(dados[4].toString());
	        defeitoField.setText(dados[5].toString());
	        servicoField.setText(dados[6].toString());
	        valorField.setText(dados[7].toString());
	    }
	}
