package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.criterion.MatchMode;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IClienteDAO;
import br.com.motorapido.dao.IEnderecoClienteDAO;
import br.com.motorapido.entity.Cliente;
import br.com.motorapido.entity.EnderecoCliente;

public class ClienteBO extends MotoRapidoBO {

	private static ClienteBO instance;

	private ClienteBO() {

	}

	public static ClienteBO getInstance() {
		if (instance == null)
			instance = new ClienteBO();

		return instance;
	}

	@SuppressWarnings("static-access")
	public List<Cliente> obterClientes(String nome, String cel, Integer codigo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		List<Cliente> lista = null;
		try {
			transaction.begin();
			IClienteDAO clienteDAO = fabricaDAO.getPostgresClienteDAO();
			Cliente cliente = new Cliente();
			if (codigo != null) {
				cliente.setCodigo(codigo);
				lista = new ArrayList<Cliente>();
				lista.add(clienteDAO.findById(codigo, em));
			} else {
				cliente.setAtivo("S");
				cliente.setNome(nome == "" ? null : nome == " " ? null : nome);
				cliente.setCelular(cel == "" ? null : cel == " " ? null : cel);
				lista = clienteDAO.findByExample(cliente, MatchMode.ANYWHERE, em, clienteDAO.BY_NOME_ASC);
			}
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter clientes.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public Cliente obterClientePorCodigo(Integer codigo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IClienteDAO clienteDAO = fabricaDAO.getPostgresClienteDAO();
			Cliente cliente = clienteDAO.findById(codigo, em);
			emUtil.commitTransaction(transaction);
			return cliente;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter cliente.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	
	public Cliente obterClienteByExample(Cliente cliente) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IClienteDAO clienteDAO = fabricaDAO.getPostgresClienteDAO();
			List<Cliente> lista = clienteDAO.findByExample(cliente, em);
			if(!lista.isEmpty())
				cliente = lista.get(0);
			else
				cliente = null;
			emUtil.commitTransaction(transaction);
			return cliente;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter cliente.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Cliente obterClientePorCelular(String numCelular) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IClienteDAO clienteDAO = fabricaDAO.getPostgresClienteDAO();
			Cliente cliente = new Cliente();
			cliente.setCelular(numCelular);
			List<Cliente> lista = clienteDAO.findByExample(cliente, em);
			if(lista != null && lista.size() > 0)
				cliente = lista.get(0);
			else
				cliente = null;
			emUtil.commitTransaction(transaction);
			return cliente;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter cliente.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public Cliente salvarCliente(Cliente cliente, EnderecoCliente enderecoCliente) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IClienteDAO clienteDAO = fabricaDAO.getPostgresClienteDAO();
			cliente.setDataCriacao(new Date());
			cliente.setAtivo("S");

			cliente = clienteDAO.save(cliente, em);
			if (enderecoCliente != null) {
				IEnderecoClienteDAO enderecoClienteDAO = fabricaDAO.getPostgresEnderecoClienteDAO();
				enderecoCliente.setCliente(cliente);
				enderecoClienteDAO.save(enderecoCliente, em);
			}

			emUtil.commitTransaction(transaction);
			return cliente;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar cliente.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void excluirEnderecoCliente(EnderecoCliente enderecoCliente) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IEnderecoClienteDAO enderecoClienteDAO = fabricaDAO.getPostgresEnderecoClienteDAO();
			enderecoClienteDAO.delete(enderecoCliente, em);
			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar remover endereço.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<EnderecoCliente> obterEnderecos(Cliente cliente) throws ExcecaoNegocio {
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
			throw new ExcecaoNegocio("Falha ao tentar obter endereços do cliente.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public EnderecoCliente salvarEndereco(EnderecoCliente enderecoCliente) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IEnderecoClienteDAO enderecoClienteDAO = fabricaDAO.getPostgresEnderecoClienteDAO();			
			enderecoCliente = enderecoClienteDAO.save(enderecoCliente, em);
			emUtil.commitTransaction(transaction);
			return enderecoCliente;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar incluir endereço do cliente.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
