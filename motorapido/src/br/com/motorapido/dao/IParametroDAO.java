package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.Parametro;

public interface IParametroDAO extends GenericDAO<Parametro, Long> {

	public static CriterioOrdenacao BY_DSCHAVE_ASC = CriterioOrdenacao.asc("chave");



}
