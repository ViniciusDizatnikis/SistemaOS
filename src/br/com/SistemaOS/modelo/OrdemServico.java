/**
 * Classe OrdemServico
 * Representa uma ordem de serviço no sistema, contendo informações sobre o serviço solicitado,
 * o cliente, o técnico responsável, o valor e outros detalhes relacionados.
 */
package br.com.SistemaOS.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Classe que representa uma ordem de serviço.
 */
public class OrdemServico {
    
    private Integer os;
    private String dataOs;
    private String equipamento;
    private String defeito;
    private String servico;
    private BigDecimal valor;
    private String cliente;
    private Integer idCliente;
    private String tecnico;
    private String contato;
    
    /**
  	 * Contrutor vazio
  	 */
    public OrdemServico() {
	}

    /**
     * Retorna o ID do cliente relacionado à ordem de serviço.
     * 
     * @return ID do cliente.
     */
    public Integer getIdCliente() {
        return idCliente;
    }
    
    /**
     * Define o ID do cliente relacionado à ordem de serviço.
     * 
     * @param idCliente ID do cliente.
     */
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Retorna o contato do cliente.
     * 
     * @return Contato do cliente.
     */
    public String getContato() {
        return contato;
    }

    /**
     * Define o contato do cliente.
     * 
     * @param contato Contato do cliente.
     */
    public void setContato(String contato) {
        this.contato = contato;
    }

    /**
     * Retorna o número da ordem de serviço.
     * 
     * @return Número da ordem de serviço.
     */
    public Integer getOs() {
        return os;
    }

    /**
     * Define o número da ordem de serviço.
     * 
     * @param os Número da ordem de serviço.
     */
    public void setOs(Integer os) {
        this.os = os;
    }

    /**
     * Retorna a data e hora da ordem de serviço no formato "yyyy-MM-dd HH:mm:ss".
     * 
     * @return Data e hora da ordem de serviço.
     */
    public String getDataOs() {
        return dataOs;
    }

    /**
     * Define a data e hora da ordem de serviço.
     * 
     * @param dataOs Data e hora da ordem de serviço.
     */
    public void setDataOs(String dataOs) {
        if (dataOs != null && !dataOs.isEmpty()) {
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                
                LocalDateTime dataFormatada = LocalDateTime.parse(dataOs, inputFormatter);
                this.dataOs = dataFormatada.format(outputFormatter);
            } catch (DateTimeParseException e) {
                System.err.println("Erro ao formatar data: " + dataOs);
                this.dataOs = null;
            }
        } else {
            this.dataOs = null;
        }
    }

    /**
     * Retorna o nome do equipamento relacionado à ordem de serviço.
     * 
     * @return Nome do equipamento.
     */
    public String getEquipamento() {
        return equipamento;
    }

    /**
     * Define o nome do equipamento relacionado à ordem de serviço.
     * 
     * @param equipamento Nome do equipamento.
     */
    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    /**
     * Retorna a descrição do defeito no equipamento.
     * 
     * @return Defeito no equipamento.
     */
    public String getDefeito() {
        return defeito;
    }

    /**
     * Define a descrição do defeito no equipamento.
     * 
     * @param defeito Defeito no equipamento.
     */
    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    /**
     * Retorna o tipo de serviço a ser realizado.
     * 
     * @return Tipo de serviço.
     */
    public String getServico() {
        return servico;
    }

    /**
     * Define o tipo de serviço a ser realizado.
     * 
     * @param servico Tipo de serviço.
     */
    public void setServico(String servico) {
        this.servico = servico;
    }

    /**
     * Retorna o nome do técnico responsável pela ordem de serviço.
     * 
     * @return Nome do técnico.
     */
    public String getTecnico() {
        return tecnico;
    }

    /**
     * Define o nome do técnico responsável pela ordem de serviço.
     * 
     * @param tecnico Nome do técnico.
     */
    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    /**
     * Retorna o valor do serviço.
     * 
     * @return Valor do serviço.
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define o valor do serviço.
     * 
     * @param valor Valor do serviço.
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Retorna o nome do cliente relacionado à ordem de serviço.
     * 
     * @return Nome do cliente.
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Define o nome do cliente relacionado à ordem de serviço.
     * 
     * @param cliente Nome do cliente.
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna a data da ordem de serviço, extraída da string dataOs.
     * 
     * @return Data da ordem de serviço.
     */
    public String getData() {
        if (dataOs != null && dataOs.contains(" ")) {
            return dataOs.split(" ")[0];
        }
        return null;
    }

    /**
     * Retorna a hora da ordem de serviço, extraída da string dataOs.
     * 
     * @return Hora da ordem de serviço.
     */
    public String getHora() {
        if (dataOs != null && dataOs.contains(" ")) {
            return dataOs.split(" ")[1];
        }
        return null;
    }

    /**
     * Retorna uma representação em formato de string da ordem de serviço.
     * 
     * @return Representação em string da ordem de serviço.
     */
    @Override
    public String toString() {
        return "OrdemServico{" +
                "os=" + os +
                ", dataOs='" + dataOs + '\'' +
                ", equipamento='" + equipamento + '\'' +
                ", defeito='" + defeito + '\'' +
                ", servico='" + servico + '\'' +
                ", tecnico='" + tecnico + '\'' +
                ", valor=" + valor +
                '}';
    }
}
