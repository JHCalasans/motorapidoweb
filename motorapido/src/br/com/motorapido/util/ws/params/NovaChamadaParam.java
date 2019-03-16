package br.com.motorapido.util.ws.params;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NovaChamadaParam implements Serializable{


	private static final long serialVersionUID = -5267594845047759442L;
	
	private Integer codUsuario;

	private String cepOrigem;

	private String bairroOrigem;

	private String cidadeOrigem;

	private String logradouroOrigem;

	private String numeroOrigem;

	private String complementoOrigem;

	private String latitudeOrigem;

	private String longitudeOrigem;

	private String cepDestino;

	private String bairroDestino;

	private String cidadeDestino;

	private String logradouroDestino;

	private String numeroDestino;

	private String complementoDestino;

	private String latitudeDestino;

	private String longitudeDestino;
	
	private String observacao;
	
	
	public NovaChamadaParam(){
		
	}


	public Integer getCodUsuario() {
		return codUsuario;
	}


	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}


	public String getCepOrigem() {
		return cepOrigem;
	}


	public void setCepOrigem(String cepOrigem) {
		this.cepOrigem = cepOrigem;
	}


	public String getBairroOrigem() {
		return bairroOrigem;
	}


	public void setBairroOrigem(String bairroOrigem) {
		this.bairroOrigem = bairroOrigem;
	}


	public String getCidadeOrigem() {
		return cidadeOrigem;
	}


	public void setCidadeOrigem(String cidadeOrigem) {
		this.cidadeOrigem = cidadeOrigem;
	}


	public String getLogradouroOrigem() {
		return logradouroOrigem;
	}


	public void setLogradouroOrigem(String logradouroOrigem) {
		this.logradouroOrigem = logradouroOrigem;
	}


	public String getNumeroOrigem() {
		return numeroOrigem;
	}


	public void setNumeroOrigem(String numeroOrigem) {
		this.numeroOrigem = numeroOrigem;
	}


	public String getComplementoOrigem() {
		return complementoOrigem;
	}


	public void setComplementoOrigem(String complementoOrigem) {
		this.complementoOrigem = complementoOrigem;
	}


	public String getLatitudeOrigem() {
		return latitudeOrigem;
	}


	public void setLatitudeOrigem(String latitudeOrigem) {
		this.latitudeOrigem = latitudeOrigem;
	}


	public String getLongitudeOrigem() {
		return longitudeOrigem;
	}


	public void setLongitudeOrigem(String longitudeOrigem) {
		this.longitudeOrigem = longitudeOrigem;
	}


	public String getCepDestino() {
		return cepDestino;
	}


	public void setCepDestino(String cepDestino) {
		this.cepDestino = cepDestino;
	}


	public String getBairroDestino() {
		return bairroDestino;
	}


	public void setBairroDestino(String bairroDestino) {
		this.bairroDestino = bairroDestino;
	}


	public String getCidadeDestino() {
		return cidadeDestino;
	}


	public void setCidadeDestino(String cidadeDestino) {
		this.cidadeDestino = cidadeDestino;
	}


	public String getLogradouroDestino() {
		return logradouroDestino;
	}


	public void setLogradouroDestino(String logradouroDestino) {
		this.logradouroDestino = logradouroDestino;
	}


	public String getNumeroDestino() {
		return numeroDestino;
	}


	public void setNumeroDestino(String numeroDestino) {
		this.numeroDestino = numeroDestino;
	}


	public String getComplementoDestino() {
		return complementoDestino;
	}


	public void setComplementoDestino(String complementoDestino) {
		this.complementoDestino = complementoDestino;
	}


	public String getLatitudeDestino() {
		return latitudeDestino;
	}


	public void setLatitudeDestino(String latitudeDestino) {
		this.latitudeDestino = latitudeDestino;
	}


	public String getLongitudeDestino() {
		return longitudeDestino;
	}


	public void setLongitudeDestino(String longitudeDestino) {
		this.longitudeDestino = longitudeDestino;
	}


	public String getObservacao() {
		return observacao;
	}


	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
