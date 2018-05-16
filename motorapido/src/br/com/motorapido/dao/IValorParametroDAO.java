package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.ValorParametro;


public interface IValorParametroDAO extends GenericDAO<ValorParametro, Integer> {

	public static CriterioOrdenacao BY_VLPARAMETRO_ASC = CriterioOrdenacao
			.asc("valor");

	

}
