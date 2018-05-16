package br.com.motorapido.enums;

import java.io.Serializable;

import br.com.minhaLib.enums.IEnum;

public enum SituacaoChamadaEnum implements IEnum {
	
	CANCELADA(1, "CANCELADA"),
	PENDENTE(2,"PENDENTE"),
	REJEITADA(3,"REJEITADA"),
	ACEITA(4,"ACEITA"),
	EXPIRADA(5,"EXPIRADA")
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
	public Serializable getCodigo() {		
		return this.codSituacao;
	}

	@Override
	public String getDescricao() {		
		return this.descricao;
	}

}
