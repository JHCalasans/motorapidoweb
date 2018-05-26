package br.com.motorapido.excecao;

import br.com.minhaLib.excecao.ExcecaoLib;

public class ExcecaoMotoristaPosicaoArea extends ExcecaoLib {

	private static final long serialVersionUID = 1L;

	public ExcecaoMotoristaPosicaoArea(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	public ExcecaoMotoristaPosicaoArea(String mensagem) {
		super(mensagem);
	}

	public ExcecaoMotoristaPosicaoArea(Throwable causa) {
		super(causa);
	}

}
