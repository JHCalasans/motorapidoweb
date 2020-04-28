package br.com.motorapido.util.ws.retornos;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RetornoHistoricoMotorista implements Serializable{

	
	private static final long serialVersionUID = 2111794576087352560L;
	
	private Date dataChamada;
	
	private String tipoVeiculo;
	
	private String placa;
	
	private String situacao;
	
	private String destino;
	
	private String valor;
	
	
	
	public RetornoHistoricoMotorista(){
		
	}



	public Date getDataChamada() {
		return dataChamada;
	}



	public void setDataChamada(Date dataChamada) {
		this.dataChamada = dataChamada;
	}



	public String getTipoVeiculo() {
		return tipoVeiculo;
	}



	public void setTipoVeiculo(String tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}



	public String getPlaca() {
		return placa;
	}



	public void setPlaca(String placa) {
		this.placa = placa;
	}



	public String getSituacao() {
		return situacao;
	}



	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}



	public String getDestino() {
		return destino;
	}



	public void setDestino(String destino) {
		this.destino = destino;
	}



	public String getValor() {
		return valor;
	}



	public void setValor(String valor) {
		this.valor = valor;
	}

}
