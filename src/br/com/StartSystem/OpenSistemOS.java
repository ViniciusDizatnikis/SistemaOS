package br.com.StartSystem;
import br.com.SistemaOS.Principal.TelaLogin;

/**
 * Esta Classe Executa o Sistema.
 */
public class OpenSistemOS {

	/**
	 * função que abre a tela de login
	 * 
	 * @param args não precisa de argumentos
	 */
	public static void main(String[] args) {
		try {
			new TelaLogin().setVisible(true);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Contrutor vazio
	 */
	public OpenSistemOS() {
	}
}
