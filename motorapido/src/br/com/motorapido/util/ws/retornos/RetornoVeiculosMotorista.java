package br.com.motorapido.util.ws.retornos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RetornoVeiculosMotorista implements Serializable{

	
	private static final long serialVersionUID = 2111794576087352560L;	
	
	private String tipoVeiculo;
	
	private String placa;

	private String modelo;
	
	private Integer codVeiculo;
	
	public RetornoVeiculosMotorista(){
		
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




	public String getModelo() {
		return modelo;
	}




	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getCodVeiculo() {
		return codVeiculo;
	}

	public void setCodVeiculo(Integer codVeiculo) {
		this.codVeiculo = codVeiculo;
	}



}
