package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IBinarioMotoristaDAO;
import br.com.motorapido.entity.BinarioMotorista;

@PersistenceContext(unitName = "postgresPU")
public class PostgresBinarioMotoristaDAOImpl extends GenericDAOImpl<BinarioMotorista, Long>
implements IBinarioMotoristaDAO{

	

}
