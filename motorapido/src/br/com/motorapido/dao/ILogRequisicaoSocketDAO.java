package br.com.motorapido.dao;


import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.LogRequisicaoSocket;



public interface ILogRequisicaoSocketDAO extends GenericDAO<LogRequisicaoSocket, Long> {
	public static CriterioOrdenacao BY_DTREQUISICAO_ASC = CriterioOrdenacao
			.asc("dataRequisicao");

	

}
