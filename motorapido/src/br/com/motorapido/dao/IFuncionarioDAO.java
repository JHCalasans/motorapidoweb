package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.Funcionario;

public interface IFuncionarioDAO extends GenericDAO<Funcionario, Integer> {
	
	static CriterioOrdenacao BY_NOME_ASC = CriterioOrdenacao.asc("nome");
	
	public List<Funcionario> obterporLoginSenha(String login, String senha,EntityManager em) throws ExcecaoBanco;
	
	public List<Funcionario> obterporFuncionarios(String nome, String cpf,EntityManager em) throws ExcecaoBanco;
	
	public List<Funcionario> obterTodos(EntityManager em) throws ExcecaoBanco;
	
	public Funcionario obterPorCodigo(Integer codFuncionario, EntityManager em) throws ExcecaoBanco;

}
