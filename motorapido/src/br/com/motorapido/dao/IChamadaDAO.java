package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.entity.Chamada;

public interface IChamadaDAO extends GenericDAO<Chamada, Integer> {
	
	public List<Chamada> obterChamadasAbertas(EntityManager em) throws ExcecaoBanco,ExcecaoNegocio;
	
	public List<Chamada> obterChamadasFiltro(Integer codSituacao, EntityManager em) throws ExcecaoBanco,ExcecaoNegocio;
	
	public List<Chamada> obterHistoricoUsuario(Integer codUsuario, EntityManager em) throws ExcecaoBanco,ExcecaoNegocio;

}
