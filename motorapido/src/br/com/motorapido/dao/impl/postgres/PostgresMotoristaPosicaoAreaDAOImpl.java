package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IMotoristaPosicaoAreaDAO;
import br.com.motorapido.entity.MotoristaPosicaoArea;

@PersistenceContext(unitName = "postgresPU")
public class PostgresMotoristaPosicaoAreaDAOImpl  extends GenericDAOImpl<MotoristaPosicaoArea, Integer>
implements IMotoristaPosicaoAreaDAO{

	PostgresMotoristaPosicaoAreaDAOImpl() {
		super();
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_POS_ASC });
	}

}
