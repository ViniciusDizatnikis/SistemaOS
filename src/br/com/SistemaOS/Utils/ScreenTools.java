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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Classe utilitária para estilização e formatação de componentes gráficos na
 * interface do sistema. Contém métodos para personalizar botões, campos de
 * texto, labels, além de funcionalidades auxiliares, como formatação de datas,
 * horas e números de celular.
 * 
 * @author Vinicius Dizatnikis
 * @version 1.1
 */
public class ScreenTools {

	/**
	 * Largura padrão para campos de texto (JTextField) na interface gráfica.
	 */
	public static final int CAMPO_LARGURA = 300;

	/**
	 * Altura padrão para campos de texto (JTextField) na interface gráfica.
	 */
	public static final int CAMPO_ALTURA = 40;

	/**
	 * Cor usada para indicar o estado de entrada de dados (INPUT).
	 */
	public static final Color INPUT_COLOR = new Color(80, 80, 80);

	/**
	 * Cor usada para indicar sucesso em operações.
	 */
	public static final Color SUCCESS_COLOR = new Color(46, 204, 113);

	/**
	 * Cor usada para indicar erro em operações.
	 */
	public static final Color ERROR_COLOR = new Color(231, 76, 60);

	/**
	 * Cor de fundo padrão da interface.
	 */
	private static final Color BACKGROUND_COLOR = new Color(45, 45, 48);

	/**
	 * Cor padrão do texto na interface.
	 */
	private static final Color DEFAULT_TEXT_COLOR = Color.WHITE;

	/**
	 * Cor de fundo padrão para botões.
	 */
	private static final Color BUTTON_BACKGROUND_COLOR = new Color(63, 182, 207);

	/**
	 * Cor de fundo padrão para campos de texto.
	 */
	private static final Color FIELD_BACKGROUND_COLOR = new Color(80, 80, 80);

	/**
	 * Cor da borda padrão para botões.
	 */
	private static final Color BUTTON_BORDER_COLOR = Color.GRAY;

	/**
	 * Cor da borda padrão para campos de texto.
	 */
	private static final Color FIELD_BORDER_COLOR = Color.GRAY;

	/**
	 * Fonte padrão para textos na interface.
	 */
	public static final Font DEFAULT_FONT = new Font("Segoe UI", Font.BOLD, 16);

	/**
	 * Fonte padrão para campos de texto.
	 */
	public static final Font FIELD_FONT = new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16);

	/**
	 * Fonte padrão para botões.
	 */
	public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);

	/**
	 * Caminho do arquivo de imagem do logotipo do sistema.
	 */
	private static final String LOGO_PATH = "/br/com/SistemaOS/Icones/icon/Logo.png";

	/**
	 * Construtor padrão da classe ScreenTools. Esta classe não deve ser instanciada
	 * diretamente, pois contém apenas métodos utilitários estáticos.
	 */
	public ScreenTools() {
	}

	/**
	 * Retorna a cor de fundo padrão da interface.
	 *
	 * @return A cor de fundo padrão (Color).
	 */
	public Color getBackgroundColor() {
		return BACKGROUND_COLOR;
	}

	/**
	 * Carrega e retorna a imagem do logotipo do sistema.
	 *
	 * @return A imagem do logotipo (Image).
	 */
	public Image getLogo() {
		return Toolkit.getDefaultToolkit().getImage(getClass().getResource(LOGO_PATH));
	}

	/**
	 * Retorna a data atual no formato "dd/MM/yyyy".
	 *
	 * @return A data atual formatada (String).
	 */
	public String getDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	/**
	 * Retorna a hora atual no formato "HH:mm".
	 *
	 * @return A hora atual formatada (String).
	 */
	public String getHoraAtual() {
		return new SimpleDateFormat("HH:mm").format(new Date());
	}

	/**
	 * Retorna uma saudação com base na hora atual: - "Bom Dia!" entre 6h e 11h59. -
	 * "Boa Tarde!" entre 12h e 17h59. - "Boa Noite!" entre 18h e 5h59.
	 *
	 * @return A saudação correspondente à hora atual (String).
	 */
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

	/**
	 * Estiliza um campo de texto (JTextField) com as configurações padrão.
	 *
	 * @param field O campo de texto a ser estilizado (JTextField).
	 * @param texto O texto inicial do campo (String). Se vazio, o texto não é
	 *              alterado.
	 */
	public void estilizarField(JTextField field, String texto) {
		field.setFont(FIELD_FONT);
		field.setBackground(FIELD_BACKGROUND_COLOR);
		field.setForeground(DEFAULT_TEXT_COLOR);
		field.setBorder(javax.swing.BorderFactory.createLineBorder(FIELD_BORDER_COLOR));
		if (!texto.isEmpty()) {
			field.setText(texto);
		}
	}

	/**
	 * Estiliza um botão (JButton) com as configurações padrão.
	 *
	 * @param botao O botão a ser estilizado (JButton).
	 */
	public void estilizarBotao(JButton botao) {
		botao.setFont(BUTTON_FONT);
		botao.setBackground(BUTTON_BACKGROUND_COLOR);
		botao.setForeground(DEFAULT_TEXT_COLOR);
		botao.setBorder(javax.swing.BorderFactory.createLineBorder(BUTTON_BORDER_COLOR));
		botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Exibe um aviso de conexão em uma caixa de diálogo.
	 */
	public void AvisoDeConexão() {
		JOptionPane.showMessageDialog(null, "Verifique sua Conexão", "Aviso", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Formata o número de celular digitado em um campo de texto (JTextField). O
	 * formato aplicado é: (XX) XXXXX-XXXX.
	 *
	 * @param field O campo de texto que contém o número de celular (JTextField).
	 */
	public void formatarCelular(JTextField field) {
		String texto = field.getText().replaceAll("[^\\d]", "");
		final String textoFormatado;

		if (texto.length() <= 2) {
			textoFormatado = texto; // Apenas DDD
		} else if (texto.length() <= 6) {
			textoFormatado = "(" + texto.substring(0, 2) + ") " + texto.substring(2);
		} else if (texto.length() <= 10) {
			textoFormatado = "(" + texto.substring(0, 2) + ") " + texto.substring(2, 7) + "-" + texto.substring(7);
		} else {
			textoFormatado = "(" + texto.substring(0, 2) + ") " + texto.substring(2, 7) + "-" + texto.substring(7, 11);
		}

		SwingUtilities.invokeLater(() -> {
			if (!field.getText().equals(textoFormatado)) {
				field.setText(textoFormatado);
			}
		});
	}

	/**
	 * Redimensiona uma imagem para o tamanho especificado.
	 *
	 * @param img     O caminho da imagem a ser redimensionada (String).
	 * @param largura A largura desejada para a imagem (int).
	 * @param altura  A altura desejada para a imagem (int).
	 * @return Um ImageIcon redimensionado, ou {@code null} em caso de erro.
	 */
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

	/**
	 * Cria um JLabel personalizado com as configurações especificadas.
	 *
	 * @param texto        O texto exibido no JLabel (String).
	 * @param x            A posição X do JLabel (int).
	 * @param y            A posição Y do JLabel (int).
	 * @param largura      A largura do JLabel (int).
	 * @param altura       A altura do JLabel (int).
	 * @param tamanhoFonte O tamanho da fonte do texto (int).
	 * @param center       Define se o texto deve ser centralizado (true) ou
	 *                     alinhado à esquerda (false).
	 * @return Um JLabel configurado com as propriedades especificadas.
	 */
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

	/**
	 * Estiliza um JLabel existente com as configurações especificadas.
	 *
	 * @param label        O JLabel a ser estilizado (JLabel).
	 * @param tamanhoFonte O tamanho da fonte do texto (int).
	 * @param center       Define se o texto deve ser centralizado (true) ou
	 *                     alinhado à esquerda (false).
	 */
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