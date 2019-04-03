package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IBinarioUsuarioDAO;
import br.com.motorapido.entity.BinarioUsuario;

@PersistenceContext(unitName = "postgresPU")
public class PostgresBinarioUsuarioDAOImpl extends GenericDAOImpl<BinarioUsuario, Long>
implements IBinarioUsuarioDAO{

	

}
