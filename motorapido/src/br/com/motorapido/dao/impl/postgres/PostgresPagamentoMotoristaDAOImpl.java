package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IPagamentoMotoristaDAO;
import br.com.motorapido.entity.PagamentoMotorista;

@PersistenceContext(unitName = "postgresPU")
public class PostgresPagamentoMotoristaDAOImpl extends GenericDAOImpl<PagamentoMotorista, Long>
implements IPagamentoMotoristaDAO{

	

}
