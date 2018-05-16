package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IParametroDAO;
import br.com.motorapido.entity.Parametro;


@PersistenceContext(unitName = "postgresPU")
class PostgresParametroDAOImpl extends GenericDAOImpl<Parametro, Long> implements
		IParametroDAO {

	PostgresParametroDAOImpl() {
		super();
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_DSCHAVE_ASC });
	}

	
}
