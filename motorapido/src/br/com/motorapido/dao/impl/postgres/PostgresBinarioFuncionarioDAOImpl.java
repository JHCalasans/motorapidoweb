package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IBinarioFuncionarioDAO;
import br.com.motorapido.entity.BinarioFuncionario;

@PersistenceContext(unitName = "postgresPU")
public class PostgresBinarioFuncionarioDAOImpl extends GenericDAOImpl<BinarioFuncionario, Long>
implements IBinarioFuncionarioDAO{

	

}
