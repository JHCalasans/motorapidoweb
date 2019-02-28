package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.UsuarioAparelho;

public interface IUsuarioAparelhoDAO extends GenericDAO<UsuarioAparelho, Integer> {
	
	public List<UsuarioAparelho> obterAparelhoUsuario(String idPush, Integer codUsuario,EntityManager em) throws ExcecaoBanco;

}
