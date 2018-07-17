package br.com.motorapido.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IBloqueioMotoristaDAO;
import br.com.motorapido.dao.ICaracteristicaMotoristaDAO;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.dao.IMotoristaDAO;
import br.com.motorapido.entity.BloqueioMotorista;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.entity.CaracteristicaMotorista;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaAparelho;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.util.FuncoesUtil;
import br.com.motorapido.util.JWTUtil;

public class MotoristaBO extends MotoRapidoBO {
	
	private static MotoristaBO instance;

	private MotoristaBO() {

	}

	public static MotoristaBO getInstance() {
		if (instance == null)
			instance = new MotoristaBO();

		return instance;
	}
	
	public List<Motorista> obterMotoristas(String nome, String cpf, String cnh,  String email,  String identidade) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			List<Motorista> lista = motoristaDAO.obterMotoristas(nome, cpf,cnh, email, identidade, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter motoristas.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	public List<Motorista> obterMotoristasAtivos() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			Motorista motorista = new Motorista();
			motorista.setAtivo("S");
			List<Motorista> lista = motoristaDAO.findByExample(motorista, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter motoristas ativos.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public void alterarDisponivel(Integer codMotorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO  motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			Motorista motorista =  motoristaDAO.findById(codMotorista, em);
			motorista.setDisponivel(motorista.getDisponivel().equals("S") ? "N" : "S");
			motoristaDAO.save(motorista, em);
			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar disponibilidade.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<Motorista> obterMotoristasExample(Motorista motorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			List<Motorista> lista = motoristaDAO.findByExample(motorista, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter motoristas.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Motorista login(Motorista motorista) throws ExcecaoNegocio{
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		try {
			String idPush = motorista.getIdPush();
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();			
			IMotoristaAparelhoDAO motoristaAparelhoDAO = fabricaDAO.getPostgresMotoristaAparelhoDAO();
			motorista.setCodigo(null);
			motorista.setDataCriacao(null);
			motorista.setDataDesativacao(null);
			motorista.setDataNascimento(null);
			motorista.setDataVencimentoCNH(null);
			List<Motorista> lista = motoristaDAO.findByExample(motorista, em);
			
			MotoristaAparelho motoristaAparelho = new MotoristaAparelho();
			if(lista != null && lista.size() > 0){
				motorista = lista.get(0);
				
				//Busca se aparelho já está cadastrado para alguém
				motoristaAparelho.setIdPush(idPush);
				List<MotoristaAparelho> listaAparelho = motoristaAparelhoDAO.findByExample(motoristaAparelho, em);
				boolean achou = false;
				if(listaAparelho != null && listaAparelho.size() > 0){
					for(MotoristaAparelho motoAp : listaAparelho){
						//Se estivesse cadastrado para este motorista seto para ativo
						if(motoAp.getCodMotorista() == motorista.getCodigo()){
							motoAp.setAtivo("S");
							motoristaAparelhoDAO.save(motoAp, em);
							achou = true;
							break;
						}//verifico se outros motoristas neste aparelho estão ativo e desativo eles nesse aparelho
						else if (motoAp.getAtivo().equals("S")){
							motoAp.setAtivo("N");
							motoristaAparelhoDAO.save(motoAp, em);							
						}
					}
					//Se o motorista que logou ainda não estava cadastrado nesse aparelho faço o cadastro
					if(!achou){
						motoristaAparelho.setCodMotorista(motorista.getCodigo());
						motoristaAparelho.setAtivo("S");
						motoristaAparelhoDAO.save(motoristaAparelho, em);	
					}					
				}//Se o aparelho ainda não estiver cadastrado faço o primeiro cadastro para este aparelho com este motorista
				else{
					motoristaAparelho.setCodMotorista(motorista.getCodigo());
					motoristaAparelho.setAtivo("S");
					motoristaAparelhoDAO.save(motoristaAparelho, em);	
				}				
					
					
				String chave = FuncoesUtil.getParam(ParametroEnum.CHAVE_SEGURANCA.getCodigo(), em);
				motorista.setChaveServicos(JWTUtil.create(motorista.getLogin(), chave));
				
				emUtil.commitTransaction(transaction);
			}else
				throw new ExcecaoNegocio("Senha/Login incorreto(s)");
			
			return motorista;
		}catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio(e.getMessage());
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar efetuar login.", e);
		}		finally {
			emUtil.closeEntityManager(em);
		}
	}

	
	public Motorista salvarMotorista(Motorista motorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			motorista.setDataCriacao(new Date());
			motorista.setSenha(FuncoesUtil.criptografarSenha(motorista.getSenha()));
			motorista.setAtivo("S");
			motorista.setDisponivel("N");
			motorista.setBloqueado("N");
			motorista = motoristaDAO.save(motorista, em);
		
			emUtil.commitTransaction(transaction);
			return motorista;
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Motorista alterarMotorista(Motorista motorista, List<Caracteristica> caracteristicas) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			ICaracteristicaMotoristaDAO caracteristicaMotoristaDAO = fabricaDAO.getPostgresCaracteristicaMotoristaDAO();

			motorista = motoristaDAO.save(motorista, em);
			
			CaracteristicaMotorista caracteristicaMotorista = new CaracteristicaMotorista();
			caracteristicaMotorista.setMotorista(motorista);
			List<CaracteristicaMotorista> result = caracteristicaMotoristaDAO.findByExample(caracteristicaMotorista, em);
			if(result != null && result.size() > 0){
				caracteristicaMotoristaDAO.deleteLista(result, em);
			}
			
			caracteristicaMotorista.setDataCriacao(new Date());
			for (Caracteristica caracteristica : caracteristicas) {
				caracteristicaMotorista.setCaracteristica(caracteristica);
				caracteristicaMotoristaDAO.save(caracteristicaMotorista, em);
				
			}
			
			emUtil.commitTransaction(transaction);
			return motorista;
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public void bloquearMotorista(Motorista motorista, Funcionario funcionario, String motivo, Date dataInicio, Date dataFinal) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			IBloqueioMotoristaDAO bloqueioMotoristaDAO = fabricaDAO.getPostgresBloqueioMotoristaDAO();
			BloqueioMotorista bloqueio = new BloqueioMotorista();
			bloqueio.setDataInicio(dataInicio);
			bloqueio.setDataFim(dataFinal);
			bloqueio.setFuncionario(funcionario);
			bloqueio.setMotivo(motivo);
			bloqueio.setAtivo("S");
			bloqueio.setMotorista(motorista);
			bloqueioMotoristaDAO.save(bloqueio, em);
			motorista.setBloqueado("S");
			motoristaDAO.save(motorista, em);
			emUtil.commitTransaction(transaction);
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar bloquear motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public void desbloquearMotorista(Motorista motorista, Date dataFim) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			IBloqueioMotoristaDAO bloqueioMotoristaDAO = fabricaDAO.getPostgresBloqueioMotoristaDAO();
			BloqueioMotorista bloqueio = bloqueioMotoristaDAO.obterUltimoPorMotorista(motorista.getCodigo(),  em);
			bloqueio.setDataFim(dataFim);
			bloqueio.setAtivo("N");
			bloqueioMotoristaDAO.save(bloqueio, em);
			motorista.setBloqueado("N");
			motoristaDAO.save(motorista, em);
			emUtil.commitTransaction(transaction);
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar desbloquear motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Motorista obterMotoristaPorCodigo(Integer codigo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			Motorista motorista = motoristaDAO.findById(codigo, em);
			emUtil.commitTransaction(transaction);
			return motorista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter motorista.", e);
		}finally {
			emUtil.closeEntityManager(em);
		}
	}
	
}
