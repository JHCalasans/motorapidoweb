package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IRestricaoClienteMotoristaDAO;
import br.com.motorapido.entity.Cliente;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.RestricaoClienteMotorista;

public class RestricaoClienteMotoristaBO  extends MotoRapidoBO {

	private static RestricaoClienteMotoristaBO instance;

	private RestricaoClienteMotoristaBO() {

	}

	public static RestricaoClienteMotoristaBO getInstance() {
		if (instance == null)
			instance = new RestricaoClienteMotoristaBO();

		return instance;
	}
	
	
	public List<RestricaoClienteMotorista> obterRestricoesPorCliente(Cliente cliente) throws ExcecaoNegocio{
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			if(!transaction.isActive())
				transaction.begin();
			
			IRestricaoClienteMotoristaDAO restricaoClienteMotoristaDAO = fabricaDAO.getPostgresRestricaoClienteMotoristaDAO();
			RestricaoClienteMotorista restricaoClienteMotorista = new RestricaoClienteMotorista();
			restricaoClienteMotorista.setCliente(cliente);
		
			List<RestricaoClienteMotorista> lista = restricaoClienteMotoristaDAO.obterRestricoesCliente(cliente.getCodigo(),  em);
			
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter restrições do cliente.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
		
		
	}
	
	public List<RestricaoClienteMotorista> salvarRestricao(Cliente cliente, List<Motorista> motoristas ) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			if(!transaction.isActive())
				transaction.begin();
			
			IRestricaoClienteMotoristaDAO restricaoClienteMotoristaDAO = fabricaDAO.getPostgresRestricaoClienteMotoristaDAO();
			RestricaoClienteMotorista restricao = null;
			List<RestricaoClienteMotorista> lista = new ArrayList<RestricaoClienteMotorista>();
			for(Motorista motorista : motoristas){
				restricao = new RestricaoClienteMotorista();
				restricao.setCliente(cliente);
				restricao.setMotorista(motorista);
				restricao.setDataCriacao(new Date());
				restricao.setCodigo(restricaoClienteMotoristaDAO.save(restricao, em).getCodigo());
				lista.add(restricao);
			}		
			
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar salvar restrição(ões) para o cliente.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<Motorista> removerRestricaoCliente(List<RestricaoClienteMotorista> restricoes) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			if(!transaction.isActive())
				transaction.begin();
			
			IRestricaoClienteMotoristaDAO restricaoClienteMotoristaDAO = fabricaDAO.getPostgresRestricaoClienteMotoristaDAO();
			restricaoClienteMotoristaDAO.deleteLista(restricoes, em);		
			List<Motorista> lista = new ArrayList<Motorista>();
			for(RestricaoClienteMotorista restricao : restricoes )
				lista.add(restricao.getMotorista());
			
			emUtil.commitTransaction(transaction);
			return lista;
			
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar remover restrição(ões) para o cliente.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	

}
