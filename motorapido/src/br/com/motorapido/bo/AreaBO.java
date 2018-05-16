package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.model.map.LatLng;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IAreaDAO;
import br.com.motorapido.dao.ICoordenadasAreaDAO;
import br.com.motorapido.entity.Area;
import br.com.motorapido.entity.CoordenadasArea;
import br.com.motorapido.util.CoordenadasAreaUtil;

public class AreaBO extends MotoRapidoBO {

	private static AreaBO instance;

	private AreaBO() {

	}

	public static AreaBO getInstance() {
		if (instance == null)
			instance = new AreaBO();

		return instance;
	}

	public Area salvarArea(List<LatLng> coordenadas, Area area) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IAreaDAO areaDAO = fabricaDAO.getPostgresAreaDAO();
			area = areaDAO.save(area, em);

			ICoordenadasAreaDAO coordenadasAreaDAO = fabricaDAO.getPostgresCoordenadasAreaDAO();
			for (LatLng coordenada : coordenadas) {
				CoordenadasArea coordenadasArea = new CoordenadasArea();
				coordenadasArea.setArea(area);
				coordenadasArea.setLatitude(coordenada.getLat());
				coordenadasArea.setLongitude(coordenada.getLng());
				coordenadasArea.setOrdem(0);
				coordenadasAreaDAO.save(coordenadasArea, em);
			}
			emUtil.commitTransaction(transaction);
			return area;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar área.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}

	}
	

	
	public Area alterarArea(Area area) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IAreaDAO areaDAO = fabricaDAO.getPostgresAreaDAO();
			area = areaDAO.save(area, em);
			emUtil.commitTransaction(transaction);
			return area;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar área.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}

	}
	
	public void excluirArea(Area area) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IAreaDAO areaDAO = fabricaDAO.getPostgresAreaDAO();
			ICoordenadasAreaDAO coordenadasAreaDAO = fabricaDAO.getPostgresCoordenadasAreaDAO();
			CoordenadasArea coordenadas = new CoordenadasArea();
			coordenadas.setArea(area);
			List<CoordenadasArea> resultado = coordenadasAreaDAO.findByExample(coordenadas, em);
			coordenadasAreaDAO.deleteLista(resultado, em);
			areaDAO.delete(area, em);
			emUtil.commitTransaction(transaction);
			
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar remover área.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}

	}

	@SuppressWarnings("static-access")
	public List<CoordenadasAreaUtil> obterAreas() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ICoordenadasAreaDAO coordenadasAreaDAO = fabricaDAO.getPostgresCoordenadasAreaDAO();
			List<CoordenadasArea> resultado = coordenadasAreaDAO.findAll(em, coordenadasAreaDAO.BY_AREA_ASC);
			Integer ultimoCodigoArea = null;
			CoordenadasAreaUtil coordenadasAreaUtil = new CoordenadasAreaUtil();
			List<CoordenadasAreaUtil> retorno = new ArrayList<CoordenadasAreaUtil>();
			for (CoordenadasArea coordenada : resultado) {
				if (ultimoCodigoArea == null){
					ultimoCodigoArea = coordenada.getArea().getCodigo();
					coordenadasAreaUtil.setArea(coordenada.getArea());
					retorno.add(coordenadasAreaUtil);
				}else if (ultimoCodigoArea != coordenada.getArea().getCodigo()) {
					 ultimoCodigoArea = coordenada.getArea().getCodigo();
					 
					 coordenadasAreaUtil = new CoordenadasAreaUtil();
					 coordenadasAreaUtil.setArea(coordenada.getArea());
					 retorno.add(coordenadasAreaUtil);
				}
				coordenadasAreaUtil.getCoordenadas().add(new LatLng(coordenada.getLatitude(), coordenada.getLongitude()));
			}
			return retorno;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter áreas.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}

	}

}
