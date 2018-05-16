package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IValorParametroDAO;
import br.com.motorapido.entity.ValorParametro;


@PersistenceContext(unitName = "postgresPU")
class PostgresValorParametroDAOImpl extends
		GenericDAOImpl<ValorParametro, Integer> implements
		IValorParametroDAO {

	PostgresValorParametroDAOImpl() {
		super();
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_VLPARAMETRO_ASC });
		
	}

	
}
