package br.com.motorapido.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.ILogErroMotoristaDAO;
import br.com.motorapido.entity.LogErroMotorista;

public class LogErroMotoristaBO  extends MotoRapidoBO {

	private static LogErroMotoristaBO instance;

	private LogErroMotoristaBO() {

	}

	public static LogErroMotoristaBO getInstance() {
		if (instance == null)
			instance = new LogErroMotoristaBO();

		return instance;
	}
	
	public List<LogErroMotorista> pesquisarLogPorData(Date dtInicial, Date dtFinal) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		List<LogErroMotorista> lista = null;
		try {
			transaction.begin();
			ILogErroMotoristaDAO logErroDAO = fabricaDAO.getPostgresLogErroMotoristaDAO();
			lista = logErroDAO.obterPorData(dtInicial, dtFinal, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter logs de erros.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
