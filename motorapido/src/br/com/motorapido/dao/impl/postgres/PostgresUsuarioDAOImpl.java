package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IUsuarioDAO;
import br.com.motorapido.entity.Usuario;

@PersistenceContext(unitName = "postgresPU")
public class PostgresUsuarioDAOImpl  extends GenericDAOImpl<Usuario, Integer>
implements IUsuarioDAO{



	

}
