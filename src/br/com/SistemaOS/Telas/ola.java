package br.com.SistemaOS.Telas;

import javax.swing.*;
import java.awt.*;

public class ola extends JFrame {

    private CardLayout cardLayout;  // Layout para alternar entre as telas
    private JPanel contentPanel;    // Painel para conter as diferentes telas

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ola frame = new ola();
            frame.setVisible(true);
        });
    }

    public ola() {
        setTitle("Troca de Tela no JFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        setLocationRelativeTo(null); // Para centralizar a janela

        // Criando o CardLayout, que vai permitir a troca de telas
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        setContentPane(contentPanel);  // Definindo o painel principal do JFrame

        // Criando os diferentes painéis para as telas
        JPanel tela1 = criarTela1();
        JPanel tela2 = criarTela2();

        // Adicionando os painéis ao painel principal
        contentPanel.add(tela1, "Tela 1");
        contentPanel.add(tela2, "Tela 2");

        // Exibindo a primeira tela ao inicializar
        cardLayout.show(contentPanel, "Tela 1");
    }

    private JPanel criarTela1() {
        JPanel painel1 = new JPanel();
        painel1.setLayout(new BorderLayout());
        
        JLabel label = new JLabel("Tela 1", SwingConstants.CENTER);
        painel1.add(label, BorderLayout.CENTER);

        JButton btnTrocarTela = new JButton("Ir para a Tela 2");
        btnTrocarTela.addActionListener(e -> cardLayout.show(contentPanel, "Tela 2"));
        painel1.add(btnTrocarTela, BorderLayout.SOUTH);

        return painel1;
    }

    private JPanel criarTela2() {
        JPanel painel2 = new JPanel();
        painel2.setLayout(new BorderLayout());

        JLabel label = new JLabel("Tela 2", SwingConstants.CENTER);
        painel2.add(label, BorderLayout.CENTER);

        JButton btnTrocarTela = new JButton("Voltar para a Tela 1");
        btnTrocarTela.addActionListener(e -> cardLayout.show(contentPanel, "Tela 1"));
        painel2.add(btnTrocarTela, BorderLayout.SOUTH);

        return painel2;
    }
}
