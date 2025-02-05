package br.com.SistemaOS.Utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ScreenTools {
    // Constantes para cores e fontes padrão
    private static final Color BACKGROUND_COLOR = new Color(45, 45, 48);
    private static final Color DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final Font DEFAULT_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Color BUTTON_BACKGROUND_COLOR = new Color(63, 182, 207);
    private static final Color FIELD_BACKGROUND_COLOR = new Color(80, 80, 80);
    private static final Color BUTTON_BORDER_COLOR = Color.GRAY;
    private static final Color FIELD_BORDER_COLOR = Color.GRAY;
    private static final String logo = "/br/com/SistemaOS/Icones/icon/Logo.png";
    
    // Método para obter a cor de fundo
    public Color getBackgroundColor() {
        return BACKGROUND_COLOR;
    }
    
    public Image getLogo() {
    	return Toolkit.getDefaultToolkit().getImage(getClass().getResource(logo));
    }

    // Método para obter a data atual
    public String getDataAtual() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    // Método para obter a hora atual
    public String getHoraAtual() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    // Método para retornar saudação com base na hora atual
    public String getSaudacao() {
        int horaAtual = Integer.parseInt(new SimpleDateFormat("HH").format(new Date()));
        if (horaAtual >= 6 && horaAtual < 12) {
            return "Bom Dia!";
        } else if (horaAtual >= 12 && horaAtual < 18) {
            return "Boa Tarde!";
        } else {
            return "Boa Noite!";
        }
    }

    // Método para estilizar o JTextField
    public void estilizarField(JTextField field, String texto) {
        field.setFont(FIELD_FONT);
        field.setBackground(FIELD_BACKGROUND_COLOR);
        field.setForeground(DEFAULT_TEXT_COLOR);
        field.setBorder(javax.swing.BorderFactory.createLineBorder(FIELD_BORDER_COLOR));
        if (!texto.isEmpty()) {
            field.setText(texto);
        }
    }

    // Método para estilizar o JButton
    public void estilizarBotao(JButton botao) {
        botao.setFont(BUTTON_FONT);
        botao.setBackground(BUTTON_BACKGROUND_COLOR);
        botao.setForeground(DEFAULT_TEXT_COLOR);
        botao.setBorder(javax.swing.BorderFactory.createLineBorder(BUTTON_BORDER_COLOR));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // Método para redimensionar a imagem
    public ImageIcon mudarTamanhoImg(String img, int largura, int altura) {
        try {
            ImageIcon imageIcon = new ImageIcon(ScreenTools.class.getResource(img));
            Image image = imageIcon.getImage();
            Image resizedImage = image.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            System.err.println("Erro ao redimensionar a imagem: " + img);
            e.printStackTrace();
            return null;
        }
    }

 // Método para criar JLabel com texto, posição, tamanho e alinhamento
    public JLabel criarLabel(String texto, int x, int y, int largura, int altura, int tamanhoFonte, boolean center) {
        JLabel label = new JLabel(texto);
        label.setForeground(DEFAULT_TEXT_COLOR);
        label.setFont(new Font(DEFAULT_FONT.getName(), DEFAULT_FONT.getStyle(), tamanhoFonte));
        label.setBounds(x, y, largura, altura);
        
        if (center) {
            label.setHorizontalAlignment(SwingConstants.CENTER); 
        } else {
            label.setHorizontalAlignment(SwingConstants.LEFT); 
        }
        return label;
    }
    
    // Método para criar JLabel com texto, posição, tamanho e alinhamento
 // Método para estilizar um JLabel já existente
    public void estilizarLabel(JLabel label, int tamanhoFonte, boolean center) {
        label.setForeground(DEFAULT_TEXT_COLOR);
        label.setFont(new Font(DEFAULT_FONT.getName(), DEFAULT_FONT.getStyle(), tamanhoFonte));

        if (center) {
            label.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            label.setHorizontalAlignment(SwingConstants.LEFT);
        }
    }

    }
