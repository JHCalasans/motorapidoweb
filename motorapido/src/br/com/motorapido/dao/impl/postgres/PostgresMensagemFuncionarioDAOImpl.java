package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IMensagemFuncionarioDAO;
import br.com.motorapido.entity.MensagemFuncionario;

@PersistenceContext(unitName = "postgresPU")
public class PostgresMensagemFuncionarioDAOImpl extends GenericDAOImpl<MensagemFuncionario, Integer>
implements IMensagemFuncionarioDAO{

	PostgresMensagemFuncionarioDAOImpl() {
		super();
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_DTCRIACAO_ASC });
	}

}
