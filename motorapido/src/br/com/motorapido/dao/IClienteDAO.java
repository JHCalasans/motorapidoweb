package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.Cliente;

public interface IClienteDAO extends GenericDAO<Cliente, Integer>{
	
	static CriterioOrdenacao BY_NOME_ASC = CriterioOrdenacao.asc("nome");
	
	public List<Cliente> obterporLoginSenha(String login, String senha,EntityManager em) throws ExcecaoBanco;
	
}
