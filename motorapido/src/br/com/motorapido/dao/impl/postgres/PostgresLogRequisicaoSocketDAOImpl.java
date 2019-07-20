package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.ILogRequisicaoSocketDAO;
import br.com.motorapido.entity.LogRequisicaoSocket;


@PersistenceContext(unitName = "postgresPU")
class PostgresLogRequisicaoSocketDAOImpl extends GenericDAOImpl<LogRequisicaoSocket, Long> 
implements ILogRequisicaoSocketDAO {
	
	

	protected PostgresLogRequisicaoSocketDAOImpl() {
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_DTREQUISICAO_ASC});
	}



	

	

	
}
