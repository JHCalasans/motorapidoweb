package br.com.motorapido.enums;

import java.io.Serializable;

import br.com.minhaLib.enums.IEnum;

public enum SituacaoMotoristaAreaEnum implements IEnum {
	
	EM_CORRIDA(-1, "EM CORRIDA")
	;
	private final int codSituacao;
	private final String descricao;
	
	public int getCodSituacao() {
		return codSituacao;
	}

	private SituacaoMotoristaAreaEnum(Integer codSituacao, String descricao){
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
