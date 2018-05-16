package br.com.motorapido.entity;

import br.com.minhaLib.dao.Entidade;

public class GenericEntity extends Entidade {

	private static final long serialVersionUID = 6751345311550906104L;

	private Integer identificador;

	@Override
	public Integer getIdentificador() {
		return identificador;
	}

}
