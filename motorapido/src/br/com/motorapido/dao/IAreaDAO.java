package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.Area;

public interface IAreaDAO extends GenericDAO<Area, Integer> {
	
	static CriterioOrdenacao BY_DSC_ASC = CriterioOrdenacao.asc("descricao");

}
