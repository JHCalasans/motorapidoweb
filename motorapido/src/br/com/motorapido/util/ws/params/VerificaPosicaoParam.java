package br.com.motorapido.util.ws.params;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VerificaPosicaoParam implements Serializable{

	private static final long serialVersionUID = -2403512034197794279L;
	
	private String latitude;
	
	private String longitude;
	
	private Integer codMotorista;
	
	private Integer codUltimaArea;
	
	
	public VerificaPosicaoParam(){
		
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public Integer getCodMotorista() {
		return codMotorista;
	}


	public void setCodMotorista(Integer codMotorista) {
		this.codMotorista = codMotorista;
	}


	public Integer getCodUltimaArea() {
		return codUltimaArea;
	}


	public void setCodUltimaArea(Integer codUltimaArea) {
		this.codUltimaArea = codUltimaArea;
	}
	

}
