package br.com.motorapido.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IValorParametroDAO;
import br.com.motorapido.entity.Parametro;
import br.com.motorapido.entity.ValorParametro;
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
	
	
	public ValorParametro saveValorParametro(ValorParametro valorParametro) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			
			IValorParametroDAO valorParametroDAO = fabricaDAO.getPostgresValorParametroDAO();
			valorParametro = valorParametroDAO.save(valorParametro, em);
			 
			emUtil.commitTransaction(transaction);
			return valorParametro;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar salvar valor do parametro.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	/*public List<ValorParametro> obterParametros(Parametro parametro) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			
			IValorParametroDAO valorParametroDAO = fabricaDAO.getPostgresValorParametroDAO();
			List<ValorParametro> result = valorParametroDAO.get
			 
			emUtil.commitTransaction(transaction);
			return valorParametro;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar salvar obter parametros.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}*/



}
