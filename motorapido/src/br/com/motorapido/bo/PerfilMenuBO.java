package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IPerfilMenuDAO;
import br.com.motorapido.entity.Menu;
import br.com.motorapido.entity.Perfil;
import br.com.motorapido.entity.PerfilMenu;

public class PerfilMenuBO extends MotoRapidoBO {
	
	private static PerfilMenuBO instance;

	private PerfilMenuBO() {

	}

	public static PerfilMenuBO getInstance() {
		if (instance == null)
			instance = new PerfilMenuBO();

		return instance;
	}
	
	
	public List<PerfilMenu> obterMenusPorPerfil(Integer codPerfil) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			if(!transaction.isActive())
				transaction.begin();
			
			IPerfilMenuDAO perfilMenuDAO = fabricaDAO.getPostgresPerfilMenuDAO();
			PerfilMenu perfilMenu = new PerfilMenu();
		
			perfilMenu.setPerfil(new Perfil());
			perfilMenu.getPerfil().setCodigo(codPerfil);
			List<PerfilMenu> lista = perfilMenuDAO.findByExample(perfilMenu, em);
			
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter menus do perfil.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public PerfilMenu salvarPerfilMenu(Perfil perfil, List<Menu> menus ) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			if(!transaction.isActive())
				transaction.begin();
			
			IPerfilMenuDAO perfilMenuDAO = fabricaDAO.getPostgresPerfilMenuDAO();
			PerfilMenu perfilMenu = null;
			for(Menu menu : menus){
				perfilMenu = new PerfilMenu();
				perfilMenu.setPerfil(perfil);
				perfilMenu.setMenu(menu);
				perfilMenu = perfilMenuDAO.save(perfilMenu, em);
			}		
			
			emUtil.commitTransaction(transaction);
			return perfilMenu;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar salvar menu(s) para o perfil.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public void removerPerfilMenu(List<PerfilMenu> perfisMenus) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			if(!transaction.isActive())
				transaction.begin();
			
			IPerfilMenuDAO perfilMenuDAO = fabricaDAO.getPostgresPerfilMenuDAO();
			perfilMenuDAO.deleteLista(perfisMenus, em);		
			
			emUtil.commitTransaction(transaction);
			
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar remover menu(s) para o perfil.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	

}
