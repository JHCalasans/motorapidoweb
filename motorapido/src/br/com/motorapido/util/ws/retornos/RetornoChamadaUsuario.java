package br.com.motorapido.util.ws.retornos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RetornoChamadaUsuario implements Serializable{

	
	private static final long serialVersionUID = 2111794576087352560L;
	
	private Date dataChamada;
	
	private String destino;
	
	private String origem;
	
	private BigDecimal valorPrevisto;	
	
	private Long codChamada;
	
	private String placaVeiculo;
	
	private String corVeiculo;
	
	private String modeloVeiculo;
	
	private String nomeMotorista;
	
	public RetornoChamadaUsuario(){
		
	}



	public Date getDataChamada() {
		return dataChamada;
	}



	public void setDataChamada(Date dataChamada) {
		this.dataChamada = dataChamada;
	}



	public String getDestino() {
		return destino;
	}



	public void setDestino(String destino) {
		this.destino = destino;
	}



	public String getOrigem() {
		return origem;
	}



	public void setOrigem(String origem) {
		this.origem = origem;
	}





	public Long getCodChamada() {
		return codChamada;
	}



	public void setCodChamada(Long codChamada) {
		this.codChamada = codChamada;
	}



	public String getPlacaVeiculo() {
		return placaVeiculo;
	}



	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}



	public String getCorVeiculo() {
		return corVeiculo;
	}



	public void setCorVeiculo(String corVeiculo) {
		this.corVeiculo = corVeiculo;
	}



	public String getModeloVeiculo() {
		return modeloVeiculo;
	}



	public void setModeloVeiculo(String modeloVeiculo) {
		this.modeloVeiculo = modeloVeiculo;
	}



	public String getNomeMotorista() {
		return nomeMotorista;
	}



	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}



	public BigDecimal getValorPrevisto() {
		return valorPrevisto;
	}



	public void setValorPrevisto(BigDecimal valorPrevisto) {
		this.valorPrevisto = valorPrevisto;
	}

}
