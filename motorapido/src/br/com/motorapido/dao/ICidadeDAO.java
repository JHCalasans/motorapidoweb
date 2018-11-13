package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.Cidade;

public interface ICidadeDAO extends GenericDAO<Cidade, Integer>{
	
	static CriterioOrdenacao BY_NOME_ASC = CriterioOrdenacao.asc("cidade");
}
