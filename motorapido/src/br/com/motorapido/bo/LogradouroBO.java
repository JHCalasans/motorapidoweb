package br.com.motorapido.bo;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.ILogradouroDAO;
import br.com.motorapido.entity.Logradouro;

public class LogradouroBO extends MotoRapidoBO {
	
	private static LogradouroBO instance;

	private LogradouroBO() {

	}

	public static LogradouroBO getInstance() {
		if (instance == null)
			instance = new LogradouroBO();

		return instance;
	}
	

	public List<Logradouro> obterLogradourosPorEstados(String estado) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ILogradouroDAO logradouroDAO = fabricaDAO.getPostgresLogradouroDAO();
			/*Logradouro logradouro = new Logradouro();
			logradouro.setEstado(estado);
			List<Logradouro> lista = logradouroDAO.findByExample(logradouro, em);*/
			
			List<Logradouro> lista = logradouroDAO.buscarLogradourosPorEstado(estado, em);
			
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter logradouros.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	

}
