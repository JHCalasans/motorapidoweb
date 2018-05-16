package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.Local;

public interface ILocalDAO extends GenericDAO<Local, Integer>{
	
	static CriterioOrdenacao BY_NOME_ASC = CriterioOrdenacao.asc("nome");

}
