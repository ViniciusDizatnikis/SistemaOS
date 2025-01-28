package br.com.SistemaOS.Principal;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;

public class Sobre extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Sobre frame = new Sobre();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Sobre() {
        setTitle("Sobre");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 851, 476);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(40, 40, 40));  // Fundo mais escuro
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Título com fonte moderna e destaque
        JLabel lblTitulo = new JLabel("Sistema OS - Sobre");
        lblTitulo.setForeground(new Color(255, 255, 255));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Fonte moderna
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(150, 50, 550, 50);
        contentPane.add(lblTitulo);

        // Texto explicativo sobre o sistema
        JLabel lblTexto = new JLabel("<html><p style='color:white; font-size: 18px; text-align:center;'>Este é um sistema de gerenciamento de ordens de serviço.<br>Desenvolvido para facilitar a administração e controle de tarefas de manutenção.<br>Com uma interface moderna e funcionalidades práticas para o usuário.</p></html>");
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblTexto.setForeground(new Color(255, 255, 255));
        lblTexto.setBounds(100, 120, 650, 177);
        contentPane.add(lblTexto);

        // Ícone (opcional, adicione a imagem no diretório do projeto)
        JLabel lblIcon = new JLabel("");
        lblIcon.setIcon(carregarIcone("/br/com/SistemaOS/Icones/icon/homem-usuario.png", 100, 100)); // Adicione o caminho correto da imagem
        lblIcon.setBounds(625, 293, 100, 100);
        contentPane.add(lblIcon);

        // Informações adicionais sobre a equipe
        JLabel lblEquipe = new JLabel("<html><p style='color:white; font-size: 16px;'>Desenvolvido por: Vinicius Dizatnikis<br>Equipe de Desenvolvimento: Sistema OS</p></html>");
        lblEquipe.setForeground(new Color(255, 255, 255));
        lblEquipe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblEquipe.setBounds(50, 293, 560, 107);
        contentPane.add(lblEquipe);
    }
    
    
    
    private ImageIcon carregarIcone(String caminho, int largura, int altura) {
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(caminho));
            Image resizedImage = originalIcon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar ícone: " + caminho, "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
