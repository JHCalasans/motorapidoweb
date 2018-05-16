package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.entity.Modelo;

public interface IModeloDAO extends GenericDAO<Modelo, Integer>{
	
	static CriterioOrdenacao BY_DSC_ASC = CriterioOrdenacao.asc("descricao");
	
	public List<Modelo> obterPorFabricanteETipo(Integer codFabricante,Integer codTipoVeiculo, EntityManager em) throws ExcecaoBanco,ExcecaoNegocio;

}
