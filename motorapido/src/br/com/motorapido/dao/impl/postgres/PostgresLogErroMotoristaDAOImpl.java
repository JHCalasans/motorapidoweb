package br.com.motorapido.dao.impl.postgres;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.minhaLib.util.EntityManagerUtil;
import br.com.motorapido.dao.ILogErroMotoristaDAO;
import br.com.motorapido.entity.LogErroMotorista;
import br.com.motorapido.entity.Motorista;


@PersistenceContext(unitName = "postgresPU")
class PostgresLogErroMotoristaDAOImpl extends GenericDAOImpl<LogErroMotorista, Long> implements ILogErroMotoristaDAO {
	
	//private static final int MAX_SIZE = 32 * 1024 - 1;

	protected PostgresLogErroMotoristaDAOImpl() {
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_DTHORAERRO_ASC });
	}

	@Override
	public void logarErroMotorista(String erro, Integer codMotorista, String servico) {
		EntityManagerUtil emUtil = new EntityManagerUtil();
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
		//	FacesContext facesContext = FacesContext.getCurrentInstance();
			//if (facesContext != null) {
				LogErroMotorista logErro = new LogErroMotorista();
				logErro.setDataHoraErro(new Date());
				logErro.setErro(erro);
				logErro.setPagina(servico);
				logErro.setMotorista(new Motorista(codMotorista));
				this.save(logErro, em);
		//	}
			emUtil.commitTransaction(transaction);
		} catch (Exception ex) {
			emUtil.rollbackTransaction(transaction);
			ex.printStackTrace();
		} finally {
			emUtil.closeEntityManager(em);
		}
		
	}

	
	
	@Override
	public List<LogErroMotorista> obterPorData(Date dtInicial, Date dtFinal, EntityManager em) throws ExcecaoBancoConexao, ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtInicial", dtInicial);
		params.put("dtFinal", dtFinal);
		return findByNamedQueryAndNamedParams("LogErroMotorista.obterPorData", params, em);
		
	}
	

	
}
