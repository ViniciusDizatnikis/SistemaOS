package br.com.SistemaOS.Utils;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class CustomDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    


    public CustomDialog(JFrame parent, String message, int durationMillis) {
        super(parent, "Mensagem", true); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 371, 142);
        setIconImage(new ScreenTools().getLogo());
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);


        JLabel lblNewLabel = new JLabel(message);
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 17));
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);


        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
        );
        contentPane.setLayout(gl_contentPane);

        setLocationRelativeTo(parent);
        new Timer(durationMillis, e -> this.dispose()).start();
    }



 
}
