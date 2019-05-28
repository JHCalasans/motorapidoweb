package br.com.motorapido.util.ws.params;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.motorapido.entity.Chamada;

@XmlRootElement
public class SelecaoChamadaParam implements Serializable{

	private static final long serialVersionUID = 7914852982033419739L;
	
	private Chamada chamada;	
	
	private Date dataDecisao;
	
	private Long codChamadaVeiculo;
	
	private Date dataRecebimento;
	
	private Date inicioCorrida;
	
	private Integer codVeiculo;
	
	public SelecaoChamadaParam() {
	}

	public Chamada getChamada() {
		return chamada;
	}

	public void setChamada(Chamada chamada) {
		this.chamada = chamada;
	}
	

	public Date getDataDecisao() {
		return dataDecisao;
	}

	public void setDataDecisao(Date dataDecisao) {
		this.dataDecisao = dataDecisao;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Long getCodChamadaVeiculo() {
		return codChamadaVeiculo;
	}

	public void setCodChamadaVeiculo(Long codChamadaVeiculo) {
		this.codChamadaVeiculo = codChamadaVeiculo;
	}

	public Date getInicioCorrida() {
		return inicioCorrida;
	}

	public void setInicioCorrida(Date inicioCorrida) {
		this.inicioCorrida = inicioCorrida;
	}

	public Integer getCodVeiculo() {
		return codVeiculo;
	}

	public void setCodVeiculo(Integer codVeiculo) {
		this.codVeiculo = codVeiculo;
	}

	
}
