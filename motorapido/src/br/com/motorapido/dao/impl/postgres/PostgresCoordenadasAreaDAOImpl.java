package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.ICoordenadasAreaDAO;
import br.com.motorapido.entity.CoordenadasArea;


@PersistenceContext(unitName = "postgresPU")
public class PostgresCoordenadasAreaDAOImpl extends GenericDAOImpl<CoordenadasArea, Integer>
implements ICoordenadasAreaDAO{

	PostgresCoordenadasAreaDAOImpl() {
		super();
	}
	
	
	

}
