package br.com.motorapido.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IPerfilDAO;
import br.com.motorapido.entity.Perfil;

public class PerfilBO extends MotoRapidoBO {

	private static PerfilBO instance;

	private PerfilBO() {

	}

	public static PerfilBO getInstance() {
		if (instance == null)
			instance = new PerfilBO();

		return instance;
	}

	@SuppressWarnings("static-access")
	public List<Perfil> obterPerfisAtivos() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IPerfilDAO perfilDAO = fabricaDAO.getPostgresPerfilDAO();
			Perfil perfil = new Perfil();
			//perfil.setAcessaSistema(false);
			perfil.setAtivo("S");
			List<Perfil> result = perfilDAO.findByExample(perfil, em, perfilDAO.BY_DSC_ASC);
			//perfil.setAcessaSistema(true);
			//result.addAll(perfilDAO.findByExample(perfil, em, perfilDAO.BY_DSC_ASC));
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter perfis ativos.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	public List<Perfil> obterPerfis(String desc, String ativo, String acesso) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IPerfilDAO perfilDAO = fabricaDAO.getPostgresPerfilDAO();
			List<Perfil> result = perfilDAO.obterPerfis(desc, ativo, acesso, em);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter perfis.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Perfil salvarPerfil(Perfil perfil) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IPerfilDAO perfilDAO = fabricaDAO.getPostgresPerfilDAO();
			perfil.setAtivo("S");
			perfil = perfilDAO.save(perfil, em);
			emUtil.commitTransaction(transaction);
			return perfil;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar perfil.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Perfil alterarPerfil(Perfil perfil) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IPerfilDAO perfilDAO = fabricaDAO.getPostgresPerfilDAO();
			perfil = perfilDAO.save(perfil, em);
			emUtil.commitTransaction(transaction);
			return perfil;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar perfil.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}


}
