package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IBairroDAO;
import br.com.motorapido.entity.Bairro;

@PersistenceContext(unitName = "postgresPU")
public class PostgresBairroDAOImpl extends GenericDAOImpl<Bairro, Integer> implements IBairroDAO{

}
