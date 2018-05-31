package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IMensagemMotoristaFuncionarioDAO;
import br.com.motorapido.entity.MensagemMotoristaFuncionario;

@PersistenceContext(unitName = "postgresPU")
public class PostgresMensagemMotoristaFuncionarioDAOImpl extends GenericDAOImpl<MensagemMotoristaFuncionario, Integer>
implements IMensagemMotoristaFuncionarioDAO{

	PostgresMensagemMotoristaFuncionarioDAOImpl() {
		super();
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_DTCRIACAO_ASC });
	}

}
