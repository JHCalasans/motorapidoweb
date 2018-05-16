package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IFuncionarioPerfilDAO;
import br.com.motorapido.entity.FuncionarioPerfil;

@PersistenceContext(unitName = "postgresPU")
public class PostgresFuncionarioPerfilDAOImpl  extends GenericDAOImpl<FuncionarioPerfil, Integer>
implements IFuncionarioPerfilDAO{


}
