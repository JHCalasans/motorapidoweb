package br.com.motorapido.util.ws.retornos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.motorapido.entity.Area;

@XmlRootElement
public class RetornoVerificaPosicao implements Serializable {


	private static final long serialVersionUID = 1323849480712571368L;
	
	private Area areaAtual;
	private Integer posicaoNaArea;
	
	
	public RetornoVerificaPosicao(){
		
	}


	public Area getAreaAtual() {
		return areaAtual;
	}


	public void setAreaAtual(Area areaAtual) {
		this.areaAtual = areaAtual;
	}


	public Integer getPosicaoNaArea() {
		return posicaoNaArea;
	}


	public void setPosicaoNaArea(Integer posicaoNaArea) {
		this.posicaoNaArea = posicaoNaArea;
	}
	
	

}
