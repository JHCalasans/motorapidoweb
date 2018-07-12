package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.RestricaoClienteMotorista;

public interface IRestricaoClienteMotoristaDAO extends GenericDAO<RestricaoClienteMotorista, Integer>{
	
	static CriterioOrdenacao BY_DT_CRIACAO_ASC = CriterioOrdenacao.asc("dataCriacao");

}
