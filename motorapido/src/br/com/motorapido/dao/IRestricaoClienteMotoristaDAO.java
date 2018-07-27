package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.RestricaoClienteMotorista;

public interface IRestricaoClienteMotoristaDAO extends GenericDAO<RestricaoClienteMotorista, Integer>{
	
	static CriterioOrdenacao BY_DT_CRIACAO_ASC = CriterioOrdenacao.asc("dataCriacao");
	
	public List<RestricaoClienteMotorista> obterRestricoesCliente(Integer codCliente, EntityManager em) throws ExcecaoBanco;

}
