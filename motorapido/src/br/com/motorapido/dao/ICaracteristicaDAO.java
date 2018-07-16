package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.Caracteristica;

public interface ICaracteristicaDAO extends GenericDAO<Caracteristica, Integer>{
	
	static CriterioOrdenacao BY_DSC_ASC = CriterioOrdenacao.asc("descricao");
	
	List<Caracteristica> obterCaracteristicas(String desc, String ativo, EntityManager em) throws ExcecaoBanco;
	
	
}
