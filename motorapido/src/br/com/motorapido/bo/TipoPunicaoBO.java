package br.com.motorapido.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.ITipoPunicaoDAO;
import br.com.motorapido.entity.TipoPunicao;

public class TipoPunicaoBO  extends MotoRapidoBO {

	private static TipoPunicaoBO instance;

	private TipoPunicaoBO() {

	}

	public static TipoPunicaoBO getInstance() {
		if (instance == null)
			instance = new TipoPunicaoBO();

		return instance;
	}
	
	
	public TipoPunicao salvarTipoPunicao(TipoPunicao tipoPunicao) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ITipoPunicaoDAO tipoPunicaoDAO = fabricaDAO.getPostgresTipoPunicaoDAO();
			tipoPunicao.setAtivo("S");
			tipoPunicao = tipoPunicaoDAO.save(tipoPunicao, em);
			emUtil.commitTransaction(transaction);
			return tipoPunicao;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar tipo de punição.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	
	public TipoPunicao alterarTipoPunicao(TipoPunicao tipoPunicao) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ITipoPunicaoDAO tipoPunicaoDAO = fabricaDAO.getPostgresTipoPunicaoDAO();
			tipoPunicao = tipoPunicaoDAO.save(tipoPunicao, em);
			emUtil.commitTransaction(transaction);
			return tipoPunicao;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar tipo de punição.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<TipoPunicao> obterTiposPunicoes(String desc, String ativo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ITipoPunicaoDAO tipoPunicaoDAO = fabricaDAO.getPostgresTipoPunicaoDAO();
			List<TipoPunicao> result = tipoPunicaoDAO.obterTipoPunicoes(desc, ativo, em);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter tipos de punições.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	

}
