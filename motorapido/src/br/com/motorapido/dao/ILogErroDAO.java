package br.com.motorapido.dao;


import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.LogErro;



public interface ILogErroDAO extends GenericDAO<LogErro, Integer> {
	public static CriterioOrdenacao BY_DTHORAERRO_ASC = CriterioOrdenacao
			.asc("dataHoraErro");

	public void logarErro(String erro);
	

}
