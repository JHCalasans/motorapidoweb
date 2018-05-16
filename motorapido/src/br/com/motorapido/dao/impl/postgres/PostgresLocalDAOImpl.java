package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.ILocalDAO;
import br.com.motorapido.entity.Local;

@PersistenceContext(unitName = "postgresPU")
public class PostgresLocalDAOImpl extends GenericDAOImpl<Local, Integer> implements ILocalDAO{

}
