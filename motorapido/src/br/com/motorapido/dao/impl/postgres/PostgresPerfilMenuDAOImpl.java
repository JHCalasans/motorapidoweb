package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IPerfilMenuDAO;
import br.com.motorapido.entity.PerfilMenu;


@PersistenceContext(unitName = "postgresPU")
public class PostgresPerfilMenuDAOImpl extends GenericDAOImpl<PerfilMenu, Integer>
implements IPerfilMenuDAO{

}
