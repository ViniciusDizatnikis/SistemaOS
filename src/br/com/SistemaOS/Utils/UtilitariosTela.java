package br.com.SistemaOS.Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class UtilitariosTela {
	private static final Color BACKGROUND_COLOR = new Color(45, 45, 48);
	
	
	
    public static Color getBackgroundColor() {
		return BACKGROUND_COLOR;
	}

	public static String getDataAtual() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public static String getHoraAtual() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    // Método para obter a saudação com base na hora
    public static String getSaudacao() {
        int horaAtual = Integer.parseInt(new SimpleDateFormat("HH").format(new Date()));
        if (horaAtual >= 6 && horaAtual < 12) {
            return "Bom Dia!";
        } else if (horaAtual >= 12 && horaAtual < 18) {
            return "Boa Tarde!";
        } else {
            return "Boa Noite!";
        }
    }

    public static ImageIcon mudarTamanhoImg(String img, int x, int y) {
        ImageIcon resizedImage = null;
        try {
            ImageIcon imageIcon = new ImageIcon(UtilitariosTela.class.getResource(img));
            Image image = imageIcon.getImage();
            // Redimensiona a imagem
            Image resizedImageTemp = image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
            resizedImage = new ImageIcon(resizedImageTemp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resizedImage;
    }
    
    public static JLabel criarLabel(String texto, int x, int y, int largura, int altura, int tamanhoFonte) {
	    JLabel label = new JLabel(texto);
	    label.setForeground(Color.WHITE);
	    label.setFont(new Font("Segoe UI", Font.BOLD, tamanhoFonte));
	    label.setBounds(x, y, largura, altura);
	    return label;
	}
}
