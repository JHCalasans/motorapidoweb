package br.com.motorapido.dao;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.motorapido.entity.LogErroMotorista;



public interface ILogErroMotoristaDAO extends GenericDAO<LogErroMotorista, Long> {
	public static CriterioOrdenacao BY_DTHORAERRO_ASC = CriterioOrdenacao
			.asc("dataHora");

	
	public void logarErroMotorista(String erro, Integer codMotorista, String servico);
	
	public List<LogErroMotorista> obterPorData(Date dtInicial, Date dtFinal, EntityManager em)throws ExcecaoBancoConexao, ExcecaoBanco;
	

}
