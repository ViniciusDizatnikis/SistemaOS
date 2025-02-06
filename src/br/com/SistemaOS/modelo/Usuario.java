package br.com.SistemaOS.modelo;

/**
 * Classe que representa um usuário do sistema.
 */
public class Usuario {
    private Integer id;
    private String nome;
    private String fone;
    private String login;
    private String senha;
    private String perfil;
    
    /**
     * Retorna o ID do usuário.
     * 
     * @return ID do usuário.
     */
    public Integer getId() {
        return id;
    }
    
    /**
  	 * Contrutor vazio
  	 */
    public Usuario() {
	}
    
    /**
     * Define o ID do usuário.
     * 
     * @param id ID do usuário.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Retorna o nome do usuário.
     * 
     * @return Nome do usuário.
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Define o nome do usuário.
     * 
     * @param nome Nome do usuário.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Retorna o telefone do usuário.
     * 
     * @return Telefone do usuário.
     */
    public String getFone() {
        return fone;
    }
    
    /**
     * Define o telefone do usuário.
     * 
     * @param fone Telefone do usuário.
     */
    public void setFone(String fone) {
        this.fone = fone;
    }
    
    /**
     * Retorna o login do usuário.
     * 
     * @return Login do usuário.
     */
    public String getLogin() {
        return login;
    }
    
    /**
     * Define o login do usuário.
     * 
     * @param login Login do usuário.
     */
    public void setLogin(String login) {
        this.login = login;
    }
    
    /**
     * Retorna a senha do usuário.
     * 
     * @return Senha do usuário.
     */
    public String getSenha() {
        return senha;
    }
    
    /**
     * Define a senha do usuário.
     * 
     * @param senha Senha do usuário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    /**
     * Retorna o perfil do usuário.
     * 
     * @return Perfil do usuário.
     */
    public String getPerfil() {
        return perfil;
    }
    
    /**
     * Define o perfil do usuário.
     * 
     * @param perfil Perfil do usuário.
     */
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
