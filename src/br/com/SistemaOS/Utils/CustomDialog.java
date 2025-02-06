package br.com.SistemaOS.Utils;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Exibe uma mensagem temporária em forma de diálogo customizado.
 * Pode ser utilizado para notificações rápidas durante carregamentos ou ações do usuário.
 * 
 * @author Vinicius Dizatnikis
 * @version 1.1
 */
public class CustomDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    /**
     * Painel Principal
     */
    private JPanel contentPane;

    /**
     * Cria um diálogo customizado com mensagem temporária.
     * 
     * @param parent         Janela principal onde o diálogo será exibido.
     * @param message        Mensagem a ser exibida.
     * @param durationMillis Duração do diálogo em milissegundos.
     */
    public CustomDialog(JFrame parent, String message, int durationMillis) {
        super(parent, "Mensagem", true); 
        if (durationMillis <= 0) {
            throw new IllegalArgumentException("A duração deve ser positiva.");
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 371, 142);
        setIconImage(new ScreenTools().getLogo()); // Ícone do sistema

        // Painel de conteúdo com borda personalizada
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(240, 240, 240)); // Cor de fundo clara
        setContentPane(contentPane);

        // Configuração do texto da mensagem
        JLabel lblMensagem = new JLabel(message);
        lblMensagem.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 17));
        lblMensagem.setForeground(Color.DARK_GRAY);
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);

        // Layout organizado para exibir a mensagem centralizada
        GroupLayout layout = new GroupLayout(contentPane);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblMensagem, GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblMensagem, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
        );
        contentPane.setLayout(layout);

        setLocationRelativeTo(parent); // Centraliza em relação ao pai

        // Timer para fechar o diálogo após o tempo especificado
        new Timer(durationMillis, e -> dispose()).start();
    }
}
