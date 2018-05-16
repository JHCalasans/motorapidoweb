package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IEnderecoClienteDAO;
import br.com.motorapido.entity.EnderecoCliente;

@PersistenceContext(unitName = "postgresPU")
public class PostgresEnderecoClienteDAOImpl  extends GenericDAOImpl<EnderecoCliente, Integer>
implements IEnderecoClienteDAO{

}
