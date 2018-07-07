package br.com.motorapido.bo;

public class MensagemFuncionarioMotoristaBO extends MotoRapidoBO {

	private static MensagemFuncionarioMotoristaBO instance;

	private MensagemFuncionarioMotoristaBO() {

	}

	public static MensagemFuncionarioMotoristaBO getInstance() {
		if (instance == null)
			instance = new MensagemFuncionarioMotoristaBO();

		return instance;
	}

	

}
