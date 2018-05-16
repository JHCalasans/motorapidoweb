package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.Menu;

public interface IMenuDAO extends GenericDAO<Menu, Integer>{
	
	static CriterioOrdenacao BY_NOME_ASC = CriterioOrdenacao.asc("nome");

	List<Menu> obterMenusNaoVinculadosAoPerfil (Integer codPerfil, EntityManager em) throws ExcecaoBanco;
}
