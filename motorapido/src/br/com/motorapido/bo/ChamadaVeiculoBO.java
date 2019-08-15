package br.com.motorapido.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IChamadaVeiculoDAO;
import br.com.motorapido.entity.ChamadaVeiculo;

public class ChamadaVeiculoBO extends MotoRapidoBO {

	private static ChamadaVeiculoBO instance;

	private ChamadaVeiculoBO() {

	}

	public static ChamadaVeiculoBO getInstance() {
		if (instance == null)
			instance = new ChamadaVeiculoBO();

		return instance;
	}

	public List<ChamadaVeiculo> obterChamadaVeiculoPorChamada(Long codChamada) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IChamadaVeiculoDAO chamadaVeiculoDAO = fabricaDAO.getPostgresChamadaVeiculoDAO();
			List<ChamadaVeiculo> lista = chamadaVeiculoDAO.obterChamadaVeiculoPorChamada(codChamada, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter histórico da chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	

}
