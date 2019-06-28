package br.com.motorapido.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IBinarioVeiculoDAO;
import br.com.motorapido.dao.IVeiculoDAO;
import br.com.motorapido.entity.BinarioVeiculo;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.Veiculo;

public class VeiculoBO extends MotoRapidoBO {
	
	private static VeiculoBO instance;

	private VeiculoBO() {

	}

	public static VeiculoBO getInstance() {
		if (instance == null)
			instance = new VeiculoBO();

		return instance;
	}
	
	public BinarioVeiculo obterBinarioVeiculoPorCodigo(Long codBinario) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IBinarioVeiculoDAO binarioVeiculoDAO = fabricaDAO.getPostgresBinarioVeiculoDAO();
			BinarioVeiculo binarioVeiculo = binarioVeiculoDAO.findById(codBinario, em);
			emUtil.commitTransaction(transaction);
			return binarioVeiculo;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter documento do veículo.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Veiculo salvarVeiculo(Veiculo veiculo, byte[] documento) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();
			veiculo.setFlgAtivo("S");
			veiculo.setEmUso("N");
			veiculo.setDataCadastro(new Date());
			veiculo.setChassi(veiculo.getChassi().toUpperCase());
			veiculo.setPlaca(veiculo.getPlaca().toUpperCase());
			
			IBinarioVeiculoDAO binarioVeiculoDAO = fabricaDAO.getPostgresBinarioVeiculoDAO();
			BinarioVeiculo binarioVeiculo = new BinarioVeiculo();
			binarioVeiculo.setBinario(documento);
			 veiculo.setCodBinarioDocumento(binarioVeiculoDAO.save(binarioVeiculo, em).getCodigo());
			
			veiculo = veiculoDAO.save(veiculo, em);
			emUtil.commitTransaction(transaction);
			return veiculo;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar veículo.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<Veiculo> obterVeiculosPorMotorista(Integer codMotorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		List<Veiculo> retorno = null;
		try {
			transaction.begin();
			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();	
			retorno = veiculoDAO.obterVeiculosPorMotorista(codMotorista, em);
			emUtil.commitTransaction(transaction);			
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter veículos.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
		return retorno;
	}
	
	
	public Veiculo obterVeiculosPorPlaca(String placa) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Veiculo retorno = null;
		try {
			transaction.begin();
			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();	
			retorno = veiculoDAO.obterVeiculoPorPlaca(placa, em);
			emUtil.commitTransaction(transaction);			
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter veículo com a placa " + placa, e);
		} finally {
			emUtil.closeEntityManager(em);
		}
		return retorno;
	}
	
	public Veiculo obterVeiculosPorChassi(String chassi) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Veiculo retorno = null;
		try {
			transaction.begin();
			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();	
			retorno = veiculoDAO.obterVeiculoPorChassi(chassi, em);
			emUtil.commitTransaction(transaction);			
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter veículo com o chassi " + chassi, e);
		} finally {
			emUtil.closeEntityManager(em);
		}
		return retorno;
	}
	
	public List<Veiculo> obterVeiculosByExample(Veiculo veiculo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		List<Veiculo> retorno = null;
		try {
			transaction.begin();
			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();	
			retorno = veiculoDAO.findByExample(veiculo, em);
			emUtil.commitTransaction(transaction);			
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter veículos.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
		return retorno;
	}
	
	public void excluirVeiculo(Veiculo veiculo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();	
			veiculo.setFlgAtivo("N");
			veiculo.setDataDesativacao(new Date());
			veiculoDAO.save(veiculo, em);
			emUtil.commitTransaction(transaction);
			
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar remover veículo.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}

	}
	
	public void selecionarVeiculo(Integer codMotorista, Integer codVeiculo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();	
			Veiculo veiculo = new Veiculo();
			veiculo.setMotorista(new Motorista(codMotorista));
			List<Veiculo> lista =veiculoDAO.findByExample(veiculo, em);
			for(Veiculo vei : lista){
				vei.setEmUso("N");
				veiculoDAO.save(vei, em);
			}
			veiculo = lista.stream().filter(ve -> ve.getCodigo() == codVeiculo).findFirst().get();
			veiculo.setEmUso("S");
			veiculoDAO.save(veiculo, em);
			emUtil.commitTransaction(transaction);
			
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar selecionar veículo.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}

	}
	

}
