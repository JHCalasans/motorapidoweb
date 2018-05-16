package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.entity.GenericEntity;

@PersistenceContext(unitName = "postgresPU")
public class PostgresGenericDAOImplBO extends
		GenericDAOImpl<GenericEntity, Integer> {

}
