package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.Cliente;

public interface IClienteDAO extends GenericDAO<Cliente, Integer>{
	
	static CriterioOrdenacao BY_NOME_ASC = CriterioOrdenacao.asc("nome");
}
