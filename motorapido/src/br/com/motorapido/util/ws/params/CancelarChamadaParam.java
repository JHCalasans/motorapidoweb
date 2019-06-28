package br.com.motorapido.util.ws.params;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.motorapido.entity.Chamada;

@XmlRootElement
public class CancelarChamadaParam implements Serializable {

	private static final long serialVersionUID = 7914852982033419739L;

	private Chamada chamada;

	private Date dataCancelamento;

	private Long codChamadaVeiculo;

	private Integer codVeiculo;

	private String latitudeAtual;

	private String longitudeAtual;

	public CancelarChamadaParam() {
	}

	public Chamada getChamada() {
		return chamada;
	}

	public void setChamada(Chamada chamada) {
		this.chamada = chamada;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Long getCodChamadaVeiculo() {
		return codChamadaVeiculo;
	}

	public void setCodChamadaVeiculo(Long codChamadaVeiculo) {
		this.codChamadaVeiculo = codChamadaVeiculo;
	}

	public Integer getCodVeiculo() {
		return codVeiculo;
	}

	public void setCodVeiculo(Integer codVeiculo) {
		this.codVeiculo = codVeiculo;
	}

	public String getLatitudeAtual() {
		return latitudeAtual;
	}

	public void setLatitudeAtual(String latitudeAtual) {
		this.latitudeAtual = latitudeAtual;
	}

	public String getLongitudeAtual() {
		return longitudeAtual;
	}

	public void setLongitudeAtual(String longitudeAtual) {
		this.longitudeAtual = longitudeAtual;
	}

}
