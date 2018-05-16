package br.com.motorapido.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IFabricanteDAO;
import br.com.motorapido.dao.ITipoVeiculoDAO;
import br.com.motorapido.entity.Fabricante;
import br.com.motorapido.entity.TipoVeiculo;

public class FabricanteBO  extends MotoRapidoBO {
	
	private static FabricanteBO instance;

	private FabricanteBO() {

	}

	public static FabricanteBO getInstance() {
		if (instance == null)
			instance = new FabricanteBO();

		return instance;
	}
	
	public List<Fabricante> obterFabricantesAtivos() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IFabricanteDAO fabricanteDAO = fabricaDAO.getPostgresFabricanteDAO();
			Fabricante fabricante = new Fabricante();
			fabricante.setFlgAtivo("S");
			List<Fabricante> result = fabricanteDAO.findByExample(fabricante, em, fabricanteDAO.BY_DSC_ASC);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter fabricantes ativos.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<Fabricante> obterFabricantesPorTipoVeiculo(Integer codTipoVeiculo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IFabricanteDAO fabricanteDAO = fabricaDAO.getPostgresFabricanteDAO();
			List<Fabricante> result = fabricanteDAO.obterPorTipoVeiculo(codTipoVeiculo, em);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter fabricantes.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
