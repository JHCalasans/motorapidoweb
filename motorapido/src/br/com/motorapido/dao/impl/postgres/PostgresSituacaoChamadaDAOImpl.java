package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.ISituacaoChamadaDAO;
import br.com.motorapido.entity.SituacaoChamada;


@PersistenceContext(unitName = "postgresPU")
public class PostgresSituacaoChamadaDAOImpl extends GenericDAOImpl<SituacaoChamada, Integer>
implements ISituacaoChamadaDAO{
	
	PostgresSituacaoChamadaDAOImpl() {
		super();
	}
	

}
