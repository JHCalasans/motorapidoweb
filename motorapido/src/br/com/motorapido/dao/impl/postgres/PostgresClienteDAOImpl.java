package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IClienteDAO;
import br.com.motorapido.entity.Cliente;

@PersistenceContext(unitName = "postgresPU")
public class PostgresClienteDAOImpl  extends GenericDAOImpl<Cliente, Integer>
implements IClienteDAO{

}
