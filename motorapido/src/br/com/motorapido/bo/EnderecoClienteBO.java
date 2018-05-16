package br.com.motorapido.bo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IEnderecoClienteDAO;
import br.com.motorapido.entity.Cliente;
import br.com.motorapido.entity.EnderecoCliente;

public class EnderecoClienteBO  extends MotoRapidoBO {

	private static EnderecoClienteBO instance;

	private EnderecoClienteBO() {

	}

	public static EnderecoClienteBO getInstance() {
		if (instance == null)
			instance = new EnderecoClienteBO();

		return instance;
	}
	
	public List<EnderecoCliente> obterEnderecosPorCliente(Cliente cliente) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		List<EnderecoCliente> lista = null;
		try {
			transaction.begin();
			IEnderecoClienteDAO enderecoClienteDAO = fabricaDAO.getPostgresEnderecoClienteDAO();
			EnderecoCliente enderecoCliente = new EnderecoCliente();
			enderecoCliente.setCliente(cliente);			
			lista = enderecoClienteDAO.findByExample(enderecoCliente, em);			
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter endereço(s) do cliente.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
