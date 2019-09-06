package br.com.motorapido.bo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.entity.Motorista;

public class MotoristaAparelhoBO extends MotoRapidoBO {

	private static MotoristaAparelhoBO instance;

	private MotoristaAparelhoBO() {

	}

	public static MotoristaAparelhoBO getInstance() {
		if (instance == null)
			instance = new MotoristaAparelhoBO();

		return instance;
	}
	
	
	public Motorista enviarID(Motorista motorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaAparelhoDAO motoristaAparelhoDAO = fabricaDAO.getPostgresMotoristaAparelhoDAO();
			
			emUtil.commitTransaction(transaction);
			return motorista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar id de aparelho.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
