package br.com.motorapido.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IMenuDAO;
import br.com.motorapido.entity.Menu;

public class MenuBO extends MotoRapidoBO {
	
	private static MenuBO instance;

	private MenuBO() {

	}

	public static MenuBO getInstance() {
		if (instance == null)
			instance = new MenuBO();

		return instance;
	}
	
	
	public List<Menu> obterMenusNaoVinculadosAoPerfil(Integer codPerfil) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			if(!transaction.isActive())
				transaction.begin();
			
			IMenuDAO menuDAO = fabricaDAO.getPostgresMenuDAO();
			List<Menu> result = menuDAO.obterMenusNaoVinculadosAoPerfil(codPerfil, em);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter menus não vinculados do perfil.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	

	
}
