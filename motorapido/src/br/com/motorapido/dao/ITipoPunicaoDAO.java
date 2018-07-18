package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.TipoPunicao;

public interface ITipoPunicaoDAO extends GenericDAO<TipoPunicao, Integer>{
	
	static CriterioOrdenacao BY_DSC_ASC = CriterioOrdenacao.asc("descricao");
	
	List<TipoPunicao> obterTipoPunicoes(String desc, String ativo, EntityManager em) throws ExcecaoBanco;
	
	
}
