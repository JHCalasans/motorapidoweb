package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.MotoristaPosicaoArea;

public interface IMotoristaPosicaoAreaDAO extends GenericDAO<MotoristaPosicaoArea, Integer> {
	
	static CriterioOrdenacao BY_POS_ASC = CriterioOrdenacao.asc("posicao");

}
