package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.model.map.LatLng;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IAreaDAO;
import br.com.motorapido.dao.ICoordenadasAreaDAO;
import br.com.motorapido.dao.IMotoristaPosicaoAreaDAO;
import br.com.motorapido.entity.Area;
import br.com.motorapido.entity.CoordenadasArea;
import br.com.motorapido.entity.MotoristaPosicaoArea;
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

	private boolean ArePolygonsOverlapped(List<LatLng> coordenadas, EntityManager em) throws ExcecaoNegocio {
		List<CoordenadasAreaUtil> lista = obterAreas(em);
		for (CoordenadasAreaUtil coord : lista) {
			for (int i = 0; i < coordenadas.size() - 1; i++) {
				for (int k = 0; k < coord.getCoordenadas().size() - 1; k++) {
					if (SimplePolylineIntersection(coordenadas.get(i), coordenadas.get(i+1), coord.getCoordenadas().get(k), coord.getCoordenadas().get(k+1)) != null)
						return true;
				}
			}

			
		}
		return false;

	}

	private LatLng SimplePolylineIntersection(LatLng latlong1, LatLng latlong2, LatLng latlong3, LatLng latlong4) {
		// Line segment 1 (p1, p2)
		double A1 = latlong2.getLat() - latlong1.getLat();
		double B1 = latlong1.getLng() - latlong2.getLng();
		double C1 = A1 * latlong1.getLng() + B1 * latlong1.getLat();

		// Line segment 2 (p3, p4)
		double A2 = latlong4.getLat() - latlong3.getLat();
		double B2 = latlong3.getLng() - latlong4.getLng();
		double C2 = A2 * latlong3.getLng() + B2 * latlong3.getLat();

		double determinate = A1 * B2 - A2 * B1;

		LatLng intersection;
		if (determinate != 0) {
			double x = (B2 * C1 - B1 * C2) / determinate;
			double y = (A1 * C2 - A2 * C1) / determinate;

			LatLng intersect = new LatLng(y, x);

			if (inBoundedBox(latlong1, latlong2, intersect) && inBoundedBox(latlong3, latlong4, intersect))
				intersection = intersect;
			else
				intersection = null;
		} else // lines are parrallel
			intersection = null;

		return intersection;
	}

	private boolean inBoundedBox(LatLng latlong1, LatLng latlong2, LatLng latlong3) {
		boolean betweenLats;
		boolean betweenLons;

		if (latlong1.getLat() < latlong2.getLat())
			betweenLats = (latlong1.getLat() <= latlong3.getLat() && latlong2.getLat() >= latlong3.getLat());
		else
			betweenLats = (latlong1.getLat() >= latlong3.getLat() && latlong2.getLat() <= latlong3.getLat());

		if (latlong1.getLng() < latlong2.getLng())
			betweenLons = (latlong1.getLng() <= latlong3.getLng() && latlong2.getLng() >= latlong3.getLng());
		else
			betweenLons = (latlong1.getLng() >= latlong3.getLng() && latlong2.getLng() <= latlong3.getLng());

		return (betweenLats && betweenLons);
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
			coordenadas.setLatitude(null);
			coordenadas.setLongitude(null);
			coordenadas.setOrdem(null);
			List<CoordenadasArea> resultado = coordenadasAreaDAO.findByExample(coordenadas, em);
			coordenadasAreaDAO.deleteLista(resultado, em);
			IMotoristaPosicaoAreaDAO motoPosicaoDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();
			MotoristaPosicaoArea motori = new MotoristaPosicaoArea();
			motori.setArea(area);
			List<MotoristaPosicaoArea> result = motoPosicaoDAO.findByExample(motori, em);
			for (MotoristaPosicaoArea mo : result) {
				if (mo.getAtivo().equals("S"))
					throw new ExcecaoNegocio("Não foi possível excluir. Existem motoristas ativos na área");
			}
			motoPosicaoDAO.deleteLista(result, em);

			areaDAO.delete(area, em);
			emUtil.commitTransaction(transaction);

		} catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw e;
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
				if (ultimoCodigoArea == null) {
					ultimoCodigoArea = coordenada.getArea().getCodigo();
					coordenadasAreaUtil.setArea(coordenada.getArea());
					retorno.add(coordenadasAreaUtil);
				} else if (ultimoCodigoArea != coordenada.getArea().getCodigo()) {
					ultimoCodigoArea = coordenada.getArea().getCodigo();

					coordenadasAreaUtil = new CoordenadasAreaUtil();
					coordenadasAreaUtil.setArea(coordenada.getArea());
					retorno.add(coordenadasAreaUtil);
				}
				coordenadasAreaUtil.getCoordenadas()
						.add(new LatLng(coordenada.getLatitude(), coordenada.getLongitude()));
			}
			emUtil.commitTransaction(transaction);
			return retorno;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter áreas.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}

	}

	@SuppressWarnings("static-access")
	public List<CoordenadasAreaUtil> obterAreas(EntityManager em) throws ExcecaoNegocio {

		try {
			ICoordenadasAreaDAO coordenadasAreaDAO = fabricaDAO.getPostgresCoordenadasAreaDAO();
			List<CoordenadasArea> resultado = coordenadasAreaDAO.findAll(em, coordenadasAreaDAO.BY_AREA_ASC);
			Integer ultimoCodigoArea = null;
			CoordenadasAreaUtil coordenadasAreaUtil = new CoordenadasAreaUtil();
			List<CoordenadasAreaUtil> retorno = new ArrayList<CoordenadasAreaUtil>();
			for (CoordenadasArea coordenada : resultado) {
				if (ultimoCodigoArea == null) {
					ultimoCodigoArea = coordenada.getArea().getCodigo();
					coordenadasAreaUtil.setArea(coordenada.getArea());
					retorno.add(coordenadasAreaUtil);
				} else if (ultimoCodigoArea != coordenada.getArea().getCodigo()) {
					ultimoCodigoArea = coordenada.getArea().getCodigo();

					coordenadasAreaUtil = new CoordenadasAreaUtil();
					coordenadasAreaUtil.setArea(coordenada.getArea());
					retorno.add(coordenadasAreaUtil);
				}
				coordenadasAreaUtil.getCoordenadas()
						.add(new LatLng(coordenada.getLatitude(), coordenada.getLongitude()));
			}
			return retorno;
		} catch (Exception e) {
			throw new ExcecaoNegocio("Falha ao tentar obter áreas.", e);
		}

	}

}
