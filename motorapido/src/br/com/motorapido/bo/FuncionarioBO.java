package br.com.motorapido.bo;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IBinarioFuncionarioDAO;
import br.com.motorapido.dao.IFuncionarioDAO;
import br.com.motorapido.dao.IPerfilDAO;
import br.com.motorapido.entity.BinarioFuncionario;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.util.FuncoesUtil;

public class FuncionarioBO extends MotoRapidoBO {
	
	private static FuncionarioBO instance;

	private FuncionarioBO() {

	}

	public static FuncionarioBO getInstance() {
		if (instance == null)
			instance = new FuncionarioBO();

		return instance;
	}
	
	public BinarioFuncionario obterBinarioFuncionarioPorCodigo(Long codBinario) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IBinarioFuncionarioDAO binarioFuncionarioDAO = fabricaDAO.getPostgresBinarioFuncionarioDAO();
			BinarioFuncionario binarioFuncionario = binarioFuncionarioDAO.findById(codBinario, em);
			emUtil.commitTransaction(transaction);
			return binarioFuncionario;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter informação do funcionário.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Funcionario salvarFuncionario(Funcionario funcionario, int codPerfil, byte[] foto) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IFuncionarioDAO funcionarioDAO = fabricaDAO.getPostgresFuncionarioDAO();
			IPerfilDAO perfilDAO = fabricaDAO.getPostgresPerfilDAO();
			//IFuncionarioPerfilDAO funcionarioPerfilDAO = fabricaDAO.getPostgresFuncionarioPerfilDAO();
			Funcionario funcionarioTemp = new Funcionario();
			funcionarioTemp.setLogin(funcionario.getLogin());
			List<Funcionario> listaFunc = funcionarioDAO.findByExample(funcionarioTemp, em);
			if(listaFunc != null && listaFunc.size() > 0)
				throw new ExcecaoNegocio("Já existe um registro com esse login cadastrado.!");
				
			funcionario.setDataCriacao(new Date());
			funcionario.setSenha(FuncoesUtil.criptografarSenha(funcionario.getSenha()));
			funcionario.setPerfil(perfilDAO.findById(codPerfil, em));
			funcionario.setAtivo("S");
			
			if(foto != null){
				IBinarioFuncionarioDAO binarioFuncionarioDAO = fabricaDAO.getPostgresBinarioFuncionarioDAO();
				
				BinarioFuncionario binarioFuncionario = new BinarioFuncionario();
				binarioFuncionario.setBinario(foto);
				funcionario.setCodBinarioFoto(binarioFuncionarioDAO.save(binarioFuncionario, em).getCodigo());
			}
			
			
			funcionario = funcionarioDAO.save(funcionario, em);
			/*FuncionarioPerfil funcPerfil = new FuncionarioPerfil();
			funcPerfil.setFuncionario(funcionario);
			funcPerfil.setAtivo(true);
			funcPerfil.setDataCriacao(new Date ());
			funcPerfil.setPerfil(perfilDAO.findById(codPerfil, em));
			funcPerfil = funcionarioPerfilDAO.save(funcPerfil, em);	*/	
			
			emUtil.commitTransaction(transaction);
			return funcionario;
		}catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio(e.getMessage(), e);
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar funcionário.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	public Funcionario alterarFuncionario(Funcionario funcionario, int codPerfil, byte[] foto) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IFuncionarioDAO funcionarioDAO = fabricaDAO.getPostgresFuncionarioDAO();
			IPerfilDAO perfilDAO = fabricaDAO.getPostgresPerfilDAO();
			funcionario.setPerfil(perfilDAO.findById(codPerfil, em));
			
			if(foto != null){
				IBinarioFuncionarioDAO binarioFuncionarioDAO = fabricaDAO.getPostgresBinarioFuncionarioDAO();
				BinarioFuncionario binarioFuncionario;
				if(funcionario.getCodBinarioFoto() != null)					
					binarioFuncionario = binarioFuncionarioDAO.findById(funcionario.getCodBinarioFoto(), em);
				else
					binarioFuncionario = new BinarioFuncionario();
				
				binarioFuncionario.setBinario(foto);
				funcionario.setCodBinarioFoto(binarioFuncionarioDAO.save(binarioFuncionario, em).getCodigo());
			}
			
			
			funcionario = funcionarioDAO.save(funcionario, em);

			emUtil.commitTransaction(transaction);
			return funcionario;
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar funcionário.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Funcionario alterarSenha(Funcionario funcionario) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IFuncionarioDAO funcionarioDAO = fabricaDAO.getPostgresFuncionarioDAO();
			funcionario = funcionarioDAO.save(funcionario, em);

			emUtil.commitTransaction(transaction);
			return funcionario;
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar senha.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Funcionario obterFuncionarioPorLoginSenha(String login, String senha) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Funcionario result = null;
		try {
			transaction.begin();
			IFuncionarioDAO funcionarioDAO = fabricaDAO.getPostgresFuncionarioDAO();

			List<Funcionario> lista = funcionarioDAO.obterporLoginSenha(login, FuncoesUtil.criptografarSenha(senha), em);
			if (lista != null && lista.size() > 0)
				result = lista.get(0);

			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter funcionário pelo Login.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<Funcionario> obterFuncionarios(String nome, String cpf) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IFuncionarioDAO funcionarioDAO = fabricaDAO.getPostgresFuncionarioDAO();
			List<Funcionario> lista = funcionarioDAO.obterporFuncionarios(nome, cpf, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter funcionários.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<Funcionario> obterFuncionariosExample(Funcionario funcionario) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IFuncionarioDAO funcionarioDAO = fabricaDAO.getPostgresFuncionarioDAO();
			List<Funcionario> lista = funcionarioDAO.findByExample(funcionario, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter funcionários.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	public Funcionario obterFuncionarioPorCodigo(Integer codigo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IFuncionarioDAO funcionarioDAO = fabricaDAO.getPostgresFuncionarioDAO();
			Funcionario funcionario = funcionarioDAO.obterPorCodigo(codigo, em);
			emUtil.commitTransaction(transaction);
			return funcionario;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter funcionário.", e);
		}finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	

}
