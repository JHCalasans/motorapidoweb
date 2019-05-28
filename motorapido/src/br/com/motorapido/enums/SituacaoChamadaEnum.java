package br.com.motorapido.enums;

import br.com.minhaLib.enums.IEnum;

public enum SituacaoChamadaEnum implements IEnum {
	
	CANCELADA(1, "CANCELADA"),
	PENDENTE(2,"PENDENTE"),
	ACEITA(3,"ACEITA"),
	EXPIRADA(4,"EXPIRADA"),
	PENDENTE_GERAL(5,"PENDENTE_GERAL"),
	FINALIZADA(6,"FINALIZADA"),
	EM_CORRIDA(6,"EM CORRIDA")
	;
	private final int codSituacao;
	private final String descricao;
	
	public int getCodSituacao() {
		return codSituacao;
	}

	private SituacaoChamadaEnum(Integer codSituacao, String descricao){
		this.codSituacao = codSituacao;
		this.descricao = descricao;
	}

	@Override
	public Integer getCodigo() {		
		return this.codSituacao;
	}

	@Override
	public String getDescricao() {		
		return this.descricao;
	}

}
