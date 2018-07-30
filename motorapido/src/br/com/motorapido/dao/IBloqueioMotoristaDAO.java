package br.com.motorapido.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.BloqueioMotorista;

public interface IBloqueioMotoristaDAO extends GenericDAO<BloqueioMotorista, Integer> {

	public BloqueioMotorista obterUltimoPorMotorista(Integer codMotorista,EntityManager em) throws ExcecaoBanco;
	
	public List<BloqueioMotorista> obterBloqueiosrMotoristaRotina(Date dataFim,EntityManager em) throws ExcecaoBanco;
}
