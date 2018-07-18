package br.com.motorapido.bo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.util.FuncoesUtil;

public class FuncaoBO extends MotoRapidoBO {

	private static FuncaoBO instance;

	private FuncaoBO() {

	}

	public static FuncaoBO getInstance() {
		if (instance == null)
			instance = new FuncaoBO();

		return instance;
	}
	
	
	
	
	public String getParam(ParametroEnum parametro) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			String param = FuncoesUtil.getParam(parametro.getCodigo(), em);		
			emUtil.commitTransaction(transaction);
			return param;
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter parâmetro.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	

}
