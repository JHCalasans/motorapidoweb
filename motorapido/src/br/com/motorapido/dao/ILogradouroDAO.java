package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.entity.Logradouro;

public interface ILogradouroDAO extends GenericDAO<Logradouro, Integer>{
	
	static CriterioOrdenacao BY_DSC_ASC = CriterioOrdenacao.asc("descricao");
	
	public List<Logradouro> buscarLogradourosPorEstado(String estado , EntityManager em) throws ExcecaoNegocio, ExcecaoBanco;
}
