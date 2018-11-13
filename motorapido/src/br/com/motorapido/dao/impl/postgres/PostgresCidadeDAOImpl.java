package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.ICidadeDAO;
import br.com.motorapido.entity.Cidade;

@PersistenceContext(unitName = "postgresPU")
public class PostgresCidadeDAOImpl extends GenericDAOImpl<Cidade, Integer> implements ICidadeDAO{

}
