package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.Motorista;

public interface IMotoristaDAO extends GenericDAO<Motorista, Integer> {

	public List<Motorista> obterMotoristas(String nome, String cpf, EntityManager em) throws ExcecaoBanco;

	public List<Motorista> obterTodos(EntityManager em) throws ExcecaoBanco;

	public Motorista obterPorCodigo(Integer codigo, EntityManager em) throws ExcecaoBanco;

}
