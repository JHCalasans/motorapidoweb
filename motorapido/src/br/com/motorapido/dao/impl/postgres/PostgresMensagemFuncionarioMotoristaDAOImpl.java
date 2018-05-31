package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IMensagemFuncionarioMotoristaDAO;
import br.com.motorapido.entity.MensagemFuncionarioMotorista;

@PersistenceContext(unitName = "postgresPU")
public class PostgresMensagemFuncionarioMotoristaDAOImpl extends GenericDAOImpl<MensagemFuncionarioMotorista, Long>
implements IMensagemFuncionarioMotoristaDAO{

	PostgresMensagemFuncionarioMotoristaDAOImpl() {
		super();
	}

}
