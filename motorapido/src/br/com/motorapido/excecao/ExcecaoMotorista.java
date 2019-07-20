package br.com.motorapido.excecao;

import br.com.minhaLib.excecao.ExcecaoLib;

public class ExcecaoMotorista extends ExcecaoLib {

	private static final long serialVersionUID = 1L;

	public ExcecaoMotorista(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	public ExcecaoMotorista(String mensagem) {
		super(mensagem);
	}

	public ExcecaoMotorista(Throwable causa) {
		super(causa);
	}

}
