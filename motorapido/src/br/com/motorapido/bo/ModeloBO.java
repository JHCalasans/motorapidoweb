package br.com.motorapido.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IModeloDAO;
import br.com.motorapido.entity.Modelo;

public class ModeloBO  extends MotoRapidoBO {
	
	private static ModeloBO instance;

	private ModeloBO() {

	}

	public static ModeloBO getInstance() {
		if (instance == null)
			instance = new ModeloBO();

		return instance;
	}
	
	public List<Modelo> obterModelosAtivos() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IModeloDAO modeloDAO = fabricaDAO.getPostgresModeloDAO();
			Modelo modelo = new Modelo();
			modelo.setFlgAtivo("S");
			List<Modelo> result = modeloDAO.findByExample(modelo, em, modeloDAO.BY_DSC_ASC);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter modelos ativos.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	public List<Modelo> obterModeloPorFabricanteETipoVeiculo(Integer codTipoVeiculo, Integer codFabricante) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IModeloDAO modeloDAO = fabricaDAO.getPostgresModeloDAO();
			List<Modelo> result = modeloDAO.obterPorFabricanteETipo(codFabricante, codTipoVeiculo, em);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter modelos.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	

}
