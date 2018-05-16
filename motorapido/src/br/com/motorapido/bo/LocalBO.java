package br.com.motorapido.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.criterion.MatchMode;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.ILocalDAO;
import br.com.motorapido.entity.Local;

public class LocalBO  extends MotoRapidoBO {

	private static LocalBO instance;

	private LocalBO() {

	}

	public static LocalBO getInstance() {
		if (instance == null)
			instance = new LocalBO();

		return instance;
	}
	
	public Local obterLocalById(Integer codlocal) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ILocalDAO localDAO = fabricaDAO.getPostgresLocalDAO();
			Local local = localDAO.findById(codlocal, em);
			emUtil.commitTransaction(transaction);
			return local;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter local.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Local salvarLocal(Local local) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ILocalDAO localDAO = fabricaDAO.getPostgresLocalDAO();
			local = localDAO.save(local, em);
			emUtil.commitTransaction(transaction);
			return local;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar local.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public void excluirLocal(Local local) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ILocalDAO localDAO = fabricaDAO.getPostgresLocalDAO();
			localDAO.delete(local, em);
			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar remover local.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	@SuppressWarnings("static-access")
	public List<Local> obterLocal(String nome) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		List<Local> lista = null;
		try {
			transaction.begin();
			ILocalDAO localDAO = fabricaDAO.getPostgresLocalDAO();
			Local local = new Local();
			local.setNome(nome == null ? nome : nome.equals("") ? null : nome.equals(" ") ? null : nome);
			lista= localDAO.findByExample(local, MatchMode.ANYWHERE ,em,localDAO.BY_NOME_ASC);		
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter locais.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
