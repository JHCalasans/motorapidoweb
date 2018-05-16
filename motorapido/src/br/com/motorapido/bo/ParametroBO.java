package br.com.motorapido.bo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.util.FuncoesUtil;

public class ParametroBO extends MotoRapidoBO {

	private static ParametroBO instance;

	private ParametroBO() {

	}

	public static ParametroBO getInstance() {
		if (instance == null)
			instance = new ParametroBO();

		return instance;
	}
	
	public String getParam(String chave) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			String result = FuncoesUtil.getParam(chave, em);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter parâmetro " + chave, e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}


}
