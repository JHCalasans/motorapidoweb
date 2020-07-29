package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.EnderecoCliente;

public interface IEnderecoClienteDAO extends GenericDAO<EnderecoCliente, Integer>{
	
	static CriterioOrdenacao BY_COD_ASC = CriterioOrdenacao.asc("codigo");

}
