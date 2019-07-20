package br.com.motorapido.dao;


import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.LogErroMotorista;



public interface ILogErroMotoristaDAO extends GenericDAO<LogErroMotorista, Long> {
	public static CriterioOrdenacao BY_DTHORAERRO_ASC = CriterioOrdenacao
			.asc("dataHora");

	
	public void logarErroMotorista(String erro, Integer codMotorista, String servico);
	

}
