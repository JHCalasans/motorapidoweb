package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IAreaDAO;
import br.com.motorapido.entity.Area;


@PersistenceContext(unitName = "postgresPU")
public class PostgresAreaDAOImpl extends GenericDAOImpl<Area, Integer>
implements IAreaDAO{

	PostgresAreaDAOImpl() {
		super();
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_DSC_ASC });
	}
	
	
	

}
