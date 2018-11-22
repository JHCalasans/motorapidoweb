package br.com.motorapido.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.model.map.LatLng;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IChamadaDAO;
import br.com.motorapido.dao.IClienteDAO;
import br.com.motorapido.dao.IEnderecoClienteDAO;
import br.com.motorapido.entity.Area;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.entity.Chamada;
import br.com.motorapido.entity.Cliente;
import br.com.motorapido.entity.EnderecoCliente;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.entity.Local;
import br.com.motorapido.entity.SituacaoChamada;
import br.com.motorapido.enums.SituacaoChamadaEnum;
import br.com.motorapido.util.CoordenadaPontoUtil;
import br.com.motorapido.util.CoordenadasAreaUtil;
import br.com.motorapido.util.FuncoesUtil;

public class ChamadaBO extends MotoRapidoBO {

	private static ChamadaBO instance;

	private ChamadaBO() {

	}

	public static ChamadaBO getInstance() {
		if (instance == null)
			instance = new ChamadaBO();

		return instance;
	}

	public Chamada iniciarChamada(Chamada chamada, EnderecoCliente origemEndereco, Local destino, Local origemLocal,
			Funcionario funcionario, List<Caracteristica> caracteristicas) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			boolean salvouCliente = false;
			// Incluindo cliente se ele não existir na base de dados
			if (chamada.getCliente().getCodigo() == null) {
				IClienteDAO clienteDAO = fabricaDAO.getPostgresClienteDAO();

				chamada.getCliente().setAtivo("S");
				chamada.getCliente().setDataCriacao(new Date());

				chamada.setCliente(clienteDAO.save(chamada.getCliente(), em));
				salvouCliente = true;
			}

			if (origemLocal != null) {
				chamada.setOrigem(origemLocal);
				chamada.setEnderecoClienteOrigem(null);
				validarAreaDoChamado(Double.parseDouble(origemLocal.getLatitude()),
						Double.parseDouble(origemLocal.getLongitude()), em);
			} else {
				chamada.setOrigem(null);
				IEnderecoClienteDAO endereceoClienteDAO = fabricaDAO.getPostgresEnderecoClienteDAO();
				origemEndereco.setCliente(chamada.getCliente());
				origemEndereco = endereceoClienteDAO.save(origemEndereco, em);
				chamada.setEnderecoClienteOrigem(origemEndereco);
				validarAreaDoChamado(Double.parseDouble(origemEndereco.getLatitude()),
						Double.parseDouble(origemEndereco.getLongitude()), em);
			}
			chamada.setDestino(destino.getCodigo() == null ? null : destino);
			chamada.setDataCriacao(new Date());
			chamada.setFuncionario(funcionario);
			SituacaoChamada situacaChamada = new SituacaoChamada();
			situacaChamada.setCodigo(SituacaoChamadaEnum.PENDENTE.getCodSituacao());
			chamada.setSituacaoChamada(situacaChamada);

			chamada = chamadaDAO.save(chamada, em);
			emUtil.commitTransaction(transaction);
			return chamada;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar iniciar chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public List<Chamada> obterChamadasAbertas() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			List<Chamada> retorno = chamadaDAO.obterChamadasAbertas(em);
			emUtil.commitTransaction(transaction);
			return retorno;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter chamadas abertas.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public List<Chamada> obterChamadasFiltro(Integer codSituacao) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			List<Chamada> retorno = chamadaDAO.obterChamadasFiltro(codSituacao, em);
			emUtil.commitTransaction(transaction);
			return retorno;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter chamadas.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void removerChamada(Chamada chamada) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			chamadaDAO.delete(chamada, em);
			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar remover chamadas.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	private Area validarAreaDoChamado(double latitude, double longitude, EntityManager em) throws ExcecaoNegocio {
		Area areaOrigem = null;
		List<CoordenadasAreaUtil> listaCoordAreas = AreaBO.getInstance().obterAreas(em);

		CoordenadaPontoUtil[] pontos;
		for (CoordenadasAreaUtil area : listaCoordAreas) {
			CoordenadaPontoUtil ponto = null;
			pontos = new CoordenadaPontoUtil[area.getCoordenadas().size()];
			int count = 0;
			for (LatLng coordenadas : area.getCoordenadas()) {
				ponto = new CoordenadaPontoUtil(coordenadas.getLat(), coordenadas.getLng());
				pontos[count] = ponto;
				count++;
			}
			ponto = new CoordenadaPontoUtil(latitude, longitude);
			if (FuncoesUtil.pontoEstaDentro(ponto, pontos)) {
				areaOrigem = area.getArea();
				break;
			}
		}
		if (areaOrigem == null)
			throw new ExcecaoNegocio("Ponto de origem não está em nenhuma área cadastrada");

		return areaOrigem;

	}

}
