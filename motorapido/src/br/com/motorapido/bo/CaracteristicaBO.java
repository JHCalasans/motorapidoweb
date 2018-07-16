package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.ICaracteristicaDAO;
import br.com.motorapido.dao.ICaracteristicaMotoristaDAO;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.entity.CaracteristicaMotorista;

public class CaracteristicaBO  extends MotoRapidoBO {

	private static CaracteristicaBO instance;

	private CaracteristicaBO() {

	}

	public static CaracteristicaBO getInstance() {
		if (instance == null)
			instance = new CaracteristicaBO();

		return instance;
	}
	
	
	public Caracteristica salvarCaracteristica(Caracteristica caracteristica) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ICaracteristicaDAO caracteristicaDAO = fabricaDAO.getPostgresCaracteristicaDAO();
			caracteristica.setAtivo("S");
			caracteristica = caracteristicaDAO.save(caracteristica, em);
			emUtil.commitTransaction(transaction);
			return caracteristica;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar característica.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	
	public Caracteristica alterarCaracteristica(Caracteristica caracteristica) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ICaracteristicaDAO caracteristicaDAO = fabricaDAO.getPostgresCaracteristicaDAO();
			caracteristica = caracteristicaDAO.save(caracteristica, em);
			emUtil.commitTransaction(transaction);
			return caracteristica;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar característica.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<Caracteristica> obterCaracteristicas(String desc, String ativo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ICaracteristicaDAO caracteristicaDAO = fabricaDAO.getPostgresCaracteristicaDAO();
			List<Caracteristica> result = caracteristicaDAO.obterCaracteristicas(desc, ativo, em);
			emUtil.commitTransaction(transaction);
			return result;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter características.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	public List<Caracteristica> obterCaracteristicasPorMotorista(Integer codMotorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ICaracteristicaMotoristaDAO caracteristicaMotoristaDAO = fabricaDAO.getPostgresCaracteristicaMotoristaDAO();
			List<CaracteristicaMotorista> result = caracteristicaMotoristaDAO.obterCaracteristicasPorMotorista(codMotorista, em);
			
			List<Caracteristica> lista = new ArrayList<Caracteristica>();
			for(CaracteristicaMotorista carac : result){
				lista.add(carac.getCaracteristica());
			}
			
			
			
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter características do motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
