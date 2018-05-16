package br.com.motorapido.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.ITipoVeiculoDAO;
import br.com.motorapido.entity.TipoVeiculo;

public class TipoVeiculoBO  extends MotoRapidoBO {
	
	private static TipoVeiculoBO instance;

	private TipoVeiculoBO() {

	}

	public static TipoVeiculoBO getInstance() {
		if (instance == null)
			instance = new TipoVeiculoBO();

		return instance;
	}
	
	public List<TipoVeiculo> obterTiposVeiculosAtivos() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ITipoVeiculoDAO tipoVeiculoDAO = fabricaDAO.getPostgresTipoVeiculoDAO();
			TipoVeiculo tipoVeiculo = new TipoVeiculo();
			tipoVeiculo.setAtivo("S");
			List<TipoVeiculo> result = tipoVeiculoDAO.findByExample(tipoVeiculo, em, tipoVeiculoDAO.BY_DSC_ASC);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter tipos de veículos ativos.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	

}
