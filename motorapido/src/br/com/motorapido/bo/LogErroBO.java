package br.com.motorapido.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.ILogErroDAO;
import br.com.motorapido.entity.LogErro;

public class LogErroBO  extends MotoRapidoBO {

	private static LogErroBO instance;

	private LogErroBO() {

	}

	public static LogErroBO getInstance() {
		if (instance == null)
			instance = new LogErroBO();

		return instance;
	}
	
	public List<LogErro> pesquisarLogPorData(Date dtInicial, Date dtFinal) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		List<LogErro> lista = null;
		try {
			transaction.begin();
			ILogErroDAO logErroDAO = fabricaDAO.getPostgresLogErroDAO();
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
