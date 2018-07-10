package br.com.motorapido.util;

import javax.faces.application.FacesMessage;

public class ObjetoMensagem {

	private FacesMessage message;
	
	private Integer codMotorista;

	public FacesMessage getMessage() {
		return message;
	}

	public void setMessage(FacesMessage message) {
		this.message = message;
	}

	public Integer getCodMotorista() {
		return codMotorista;
	}

	public void setCodMotorista(Integer codMotorista) {
		this.codMotorista = codMotorista;
	}
	
}
