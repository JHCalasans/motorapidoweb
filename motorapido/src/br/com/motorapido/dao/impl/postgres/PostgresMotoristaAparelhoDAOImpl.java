package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.entity.MotoristaAparelho;

@PersistenceContext(unitName = "postgresPU")
public class PostgresMotoristaAparelhoDAOImpl  extends GenericDAOImpl<MotoristaAparelho, Integer>
implements IMotoristaAparelhoDAO{



}
