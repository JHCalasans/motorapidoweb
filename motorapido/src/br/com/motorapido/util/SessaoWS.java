package br.com.motorapido.util;

import javax.websocket.Session;

public class SessaoWS {

	private Integer codMotorista;
	
	private Session sessao;
	
	public SessaoWS(){}

	public Integer getCodMotorista() {
		return codMotorista;
	}

	public void setCodMotorista(Integer codMotorista) {
		this.codMotorista = codMotorista;
	}

	public Session getSessao() {
		return sessao;
	}

	public void setSessao(Session sessao) {
		this.sessao = sessao;
	}
}
