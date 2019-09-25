package br.com.motorapido.dao;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.motorapido.entity.LogErro;



public interface ILogErroDAO extends GenericDAO<LogErro, Long> {
	public static CriterioOrdenacao BY_DTHORAERRO_ASC = CriterioOrdenacao
			.asc("dataHoraErro");

	public void logarErro(String erro);
	
	public List<LogErro> obterPorData(Date dtInicial, Date dtFinal, EntityManager em)throws ExcecaoBancoConexao, ExcecaoBanco;
	

}
