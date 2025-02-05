package br.com.SistemaOS.modelo;

import java.math.BigDecimal;

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

    public Integer getIdCliente() {
    	return idCliente;
    }
    
    public void setIdCliente(Integer idCliente) {
    	this.idCliente = idCliente;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public Integer getOs() {
        return os;
    }

    public void setOs(Integer os) {
        this.os = os;
    }

    public String getDataOs() {
        return dataOs;
    }

    public void setDataOs(String dataOs) {
        this.dataOs = dataOs;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getData() {
        if (dataOs != null && dataOs.contains(" ")) {
            return dataOs.split(" ")[0];
        }
        return null;
    }

    public String getHora() {
        if (dataOs != null && dataOs.contains(" ")) {
            return dataOs.split(" ")[1];
        }
        return null;
    }

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
