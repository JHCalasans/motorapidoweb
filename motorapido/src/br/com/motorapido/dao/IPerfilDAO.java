package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.Perfil;

public interface IPerfilDAO extends GenericDAO<Perfil, Integer> {
	
	static CriterioOrdenacao BY_DSC_ASC = CriterioOrdenacao.asc("descricao");
	
	List<Perfil> obterPerfis(String desc, String ativo, String acesso, EntityManager em) throws ExcecaoBanco;
	

}
