package br.com.SistemaOS.Telas.Detalhes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.com.SistemaOS.Utils.UtilitariosTela;
import br.com.SistemaOS.modelo.Usuario;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class DetalhesCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private final UtilitariosTela util = new UtilitariosTela();
    private JTextField enderecoField;
    private JTextField nomeField;
    private JTextField foneField;
    private JTextField emailField;
    
    
    //dados do cliente
    private Integer id;
    private String nome;
    private String endereco;
    private String fone;
    private String email;
    

    public DetalhesCliente(Integer id, String nome, String fone, String endereco, String email) {
    	
    	setUpInfoClient(id, nome, fone, endereco, email);
        configurarFrame();
        adicionarComponentes();
    }
    
    private void setUpInfoClient(Integer id, String nome, String fone, String endereco, String email) {
    	this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.fone = fone;
		this.email = email;
    }

    private void configurarFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1020, 540);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(util.getBackgroundColor());
        contentPane.setLayout(null); 

        setContentPane(contentPane);
    }	


    private void adicionarComponentes() {
        
        JLabel lblTitle = new JLabel("Informações do Cliente");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 35));
        lblTitle.setBounds(315, 11, 391, 47);
        contentPane.add(lblTitle);
        
        
     	 JLabel fotoUser = new JLabel("");
         fotoUser.setIcon(util.mudarTamanhoImg("/br/com/SistemaOS/Icones/icon/homem-usuario.png", 200, 200));
         fotoUser.setHorizontalAlignment(SwingConstants.CENTER);
         fotoUser.setForeground(Color.WHITE);
         fotoUser.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 35));
         fotoUser.setBounds(10, 57, 229, 207);
         contentPane.add(fotoUser);
         
         JLabel lblCliente = new JLabel("Cliente:");
         lblCliente.setForeground(Color.WHITE);
         lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 33));
         lblCliente.setBounds(235, 92, 145, 47);
         contentPane.add(lblCliente);
         
         JLabel lblName = new JLabel("<dynamic>");
         lblName.setForeground(Color.WHITE);
         lblName.setFont(new Font("Segoe UI", Font.BOLD, 30));
         lblName.setBounds(235, 136, 759, 47);
         lblName.setText(nome);
         contentPane.add(lblName);
         
         JLabel lblId = new JLabel("Id:");
         lblId.setForeground(Color.WHITE);
         lblId.setFont(new Font("Segoe UI", Font.BOLD, 33));
         lblId.setBounds(235, 185, 45, 47);
         contentPane.add(lblId);
         
         JLabel Id = new JLabel("<dynamic>");
         Id.setForeground(Color.WHITE);
         Id.setFont(new Font("Segoe UI", Font.BOLD, 27));
         Id.setBounds(278, 187, 402, 47);
         Id.setText(id.toString());
         contentPane.add(Id);
         
         
         JLabel lblNome = new JLabel("Nome:");
         lblNome.setForeground(Color.WHITE);
         lblNome.setFont(new Font("Segoe UI", Font.BOLD, 25));
         lblNome.setBounds(87, 297, 89, 35);
         contentPane.add(lblNome);
         
         JLabel lblEndereo = new JLabel("Endereço:");
         lblEndereo.setForeground(Color.WHITE);
         lblEndereo.setFont(new Font("Segoe UI", Font.BOLD, 25));
         lblEndereo.setBounds(54, 372, 122, 35);
         contentPane.add(lblEndereo);
         
         JLabel lblFone = new JLabel("Fone:");
         lblFone.setForeground(Color.WHITE);
         lblFone.setFont(new Font("Segoe UI", Font.BOLD, 25));
         lblFone.setBounds(519, 297, 64, 35);
         contentPane.add(lblFone);
         
         JLabel lblEmail = new JLabel("Email:");
         lblEmail.setForeground(Color.WHITE);
         lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 25));
         lblEmail.setBounds(519, 372, 75, 35);
         contentPane.add(lblEmail);
         
         enderecoField = new JTextField();
         enderecoField.setBounds(175, 372, 322, 35);
         enderecoField.setText(endereco);
         contentPane.add(enderecoField);
         enderecoField.setColumns(10);
         
         nomeField = new JTextField();
         nomeField.setColumns(10);
         nomeField.setBounds(175, 297, 322, 35);
         nomeField.setText(nome);
         contentPane.add(nomeField);
         
         foneField = new JTextField();
         foneField.setColumns(10);
         foneField.setBounds(593, 297, 322, 35);
         foneField.setText(fone);
         contentPane.add(foneField);
         
         emailField = new JTextField();
         emailField.setColumns(10);
         emailField.setBounds(593, 372, 322, 35);
         emailField.setText(email);
         contentPane.add(emailField);
         
         JButton btnNewButton = new JButton("Fechar");
         btnNewButton.setBounds(463, 456, 89, 23);
         contentPane.add(btnNewButton);
         
         btnNewButton.addActionListener(e ->{
        	this.dispose(); 
         });
    }
    
}
