
package br.com.SistemaOS.modelo;

/**
 * Classe Cliente
 * Representa um cliente do sistema, contendo informações como ID, nome, endereço, telefone e e-mail.
 */
public class Cliente {
	/**
	 * Id do Cliente
	 */
    private Integer id;
    /**
	 * Informações do Cliente
	 */
    private String nome, endereco, fone, email;

    /**
     * Retorna o ID do cliente.
     * 
     * @return O ID do cliente.
     */
    public Integer getId() {
        return id;
    }
    
    /**
	 * Contrutor vazio
	 */
    public Cliente() {
	}

    /**
     * Define o ID do cliente.
     * 
     * @param id O ID do cliente.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome do cliente.
     * 
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     * 
     * @param nome O nome do cliente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o endereço do cliente.
     * 
     * @return O endereço do cliente.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço do cliente.
     * 
     * @param endereco O endereço do cliente.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o telefone do cliente.
     * 
     * @return O telefone do cliente.
     */
    public String getFone() {
        return fone;
    }

    /**
     * Define o telefone do cliente.
     * 
     * @param fone O telefone do cliente.
     */
    public void setFone(String fone) {
        this.fone = fone;
    }

    /**
     * Retorna o e-mail do cliente.
     * 
     * @return O e-mail do cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail do cliente.
     * 
     * @param email O e-mail do cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna uma representação em forma de string do cliente.
     * Neste caso, retorna o nome do cliente.
     * 
     * @return O nome do cliente.
     */
    @Override
    public String toString() {
        return this.getNome();
    }
}