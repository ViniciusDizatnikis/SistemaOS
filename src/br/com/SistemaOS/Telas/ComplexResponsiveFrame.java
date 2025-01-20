package br.com.SistemaOS.Telas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ComplexResponsiveFrame {
    public static void main(String[] args) {
        // Criando o JFrame
        JFrame frame = new JFrame("JFrame Complexo e Responsivo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        // Layout principal do JFrame
        frame.setLayout(new BorderLayout());

        // Painel de topo com um título
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Interface Responsiva", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // Painel do lado esquerdo com alguns botões
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Opções"));
        JButton button1 = new JButton("Botão 1");
        JButton button2 = new JButton("Botão 2");
        JButton button3 = new JButton("Botão 3");
        leftPanel.add(button1);
        leftPanel.add(button2);
        leftPanel.add(button3);
        frame.add(leftPanel, BorderLayout.WEST);

        // Painel central com um campo de texto e um botão
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel inputLabel = new JLabel("Digite algo:");
        JTextField textField = new JTextField(20);
        JButton submitButton = new JButton("Enviar");
        
        // Configurando o layout GridBagLayout para responsividade
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(inputLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(submitButton, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Painel de rodapé com informações
        JPanel bottomPanel = new JPanel();
        JLabel footerLabel = new JLabel("Rodapé com informações", SwingConstants.CENTER);
        bottomPanel.add(footerLabel);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Listener para redimensionar a janela
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Atualizando os componentes conforme o tamanho da janela
                int width = frame.getWidth();
                int height = frame.getHeight();
                
                // Ajustando a largura do JTextField
                textField.setColumns(width / 40);
                
                // Atualizando o título no topo com o tamanho atual
                titleLabel.setText("Largura: " + width + " | Altura: " + height);
                
                // Revalidando os layouts após o redimensionamento
                topPanel.revalidate();
                leftPanel.revalidate();
                centerPanel.revalidate();
                bottomPanel.revalidate();
            }
        });

        // Tornando o frame visível
        frame.setVisible(true);
    }
}
