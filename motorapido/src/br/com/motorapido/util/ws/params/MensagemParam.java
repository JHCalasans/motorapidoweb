package br.com.motorapido.util.ws.params;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MensagemParam implements Serializable{


	private static final long serialVersionUID = 7476867770045930454L;

	private String mensagem;
	
	private Integer codMotorista;
	
	
	public MensagemParam(){
		
	}


	public Integer getCodMotorista() {
		return codMotorista;
	}


	public void setCodMotorista(Integer codMotorista) {
		this.codMotorista = codMotorista;
	}


	public String getMensagem() {
		return mensagem;
	}


	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	

}
