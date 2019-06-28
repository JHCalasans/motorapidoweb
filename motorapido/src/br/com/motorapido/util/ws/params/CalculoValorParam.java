package br.com.motorapido.util.ws.params;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CalculoValorParam implements Serializable{

	private static final long serialVersionUID = 717431948961077881L;
	
	private String latitudeDestino;
	
	private String longitudeDestino;
	
	private String latitudeOrigem;
	
	private String longitudeOrigem;
	
	public CalculoValorParam(){}

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
	
	

}
