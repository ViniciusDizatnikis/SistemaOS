package br.com.SistemaOS.Utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class UtilitariosTela {
    private static final Color BACKGROUND_COLOR = new Color(45, 45, 48);
    private static final Color DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final Font DEFAULT_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public Color getBackgroundColor() {
        return BACKGROUND_COLOR;
    }


    public String getDataAtual() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }


    public String getHoraAtual() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

   
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

    
    public void estilizarField(JTextField field, String texto) {
        field.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
        field.setBackground(new Color(80, 80, 80));
        field.setForeground(Color.WHITE);
        field.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
        if(!texto.isEmpty()) {        	
        	field.setText(texto);
        }
    }

    public void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botao.setBackground(new Color(63, 182, 207));
        botao.setForeground(Color.WHITE);
        botao.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
  
    public ImageIcon mudarTamanhoImg(String img, int largura, int altura) {
        try {
            ImageIcon imageIcon = new ImageIcon(UtilitariosTela.class.getResource(img));
            Image image = imageIcon.getImage();
            Image resizedImage = image.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            System.err.println("Erro ao redimensionar a imagem: " + img);
            e.printStackTrace();
            return null;
        }
    }

    public JLabel criarLabel(String texto, int x, int y, int largura, int altura, int tamanhoFonte) {
        JLabel label = new JLabel(texto);
        label.setForeground(DEFAULT_TEXT_COLOR);
        label.setFont(new Font(DEFAULT_FONT.getName(), DEFAULT_FONT.getStyle(), tamanhoFonte));
        label.setBounds(x, y, largura, altura);
        return label;
    }
}
