package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.model.map.LatLng;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoEntidadeReferenciada;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IMotoristaPosicaoAreaDAO;
import br.com.motorapido.entity.Area;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.excecao.ExcecaoMotoristaPosicaoArea;
import br.com.motorapido.mbean.AreaBean;
import br.com.motorapido.mbean.SimpleController;
import br.com.motorapido.util.ControleSessaoWS;
import br.com.motorapido.util.CoordenadaPontoUtil;
import br.com.motorapido.util.CoordenadasAreaUtil;
import br.com.motorapido.util.FuncoesUtil;
import br.com.motorapido.util.MotoristaPontoMapa;
import br.com.motorapido.util.ws.params.VerificaPosicaoParam;
import br.com.motorapido.util.ws.retornos.RetornoVerificaPosicao;

public class MotoristaPosicaoAreaBO extends MotoRapidoBO {

	private static MotoristaPosicaoAreaBO instance;

	private MotoristaPosicaoAreaBO() {

	}

	public static MotoristaPosicaoAreaBO getInstance() {
		if (instance == null)
			instance = new MotoristaPosicaoAreaBO();

		return instance;
	}
	
	public List<MotoristaPosicaoArea> iniciarListaPosicoesMapa() throws ExcecaoNegocio{
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();
			List<MotoristaPosicaoArea> listaMotoristas = motoristaPosicaoAreaDAO.obterMotoristaAtivoCodigo(-1, em);			
			return listaMotoristas;

		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter posições dos motoristas.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	private Area validarAreaDoMotorista(double latitude, double longitude, Integer codMotorista, String login, EntityManager em)
			throws ExcecaoNegocio, ExcecaoMotoristaPosicaoArea, ExcecaoBancoConexao, ExcecaoBanco {
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
		EventBus eventBus = EventBusFactory.getDefault().eventBus();
		eventBus.publish("/notify", new FacesMessage(StringEscapeUtils.escapeHtml3("LocalMotorista"),
				codMotorista.toString()+";"+login+";"+ latitude+";"+longitude));
	
		if (areaOrigem == null) {
			desativarMotoristaEmAreas(codMotorista, em);
			throw new ExcecaoMotoristaPosicaoArea("Motorista não está em nenhuma área!");
		}

		return areaOrigem;

	}

	public List<MotoristaPosicaoArea> obterMotoristasPorArea(Area area)
			throws ExcecaoNegocio, ExcecaoMotoristaPosicaoArea {

		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();
			List<MotoristaPosicaoArea> lista = motoristaPosicaoAreaDAO.obterMotoristasPorArea(area, em);
			return lista;

		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter posição na área do motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}

	}

	public MotoristaPosicaoArea obterPosicaoMotoristaArea(VerificaPosicaoParam param)
			throws ExcecaoNegocio, ExcecaoMotoristaPosicaoArea, ExcecaoBancoConexao, ExcecaoBancoEntidadeReferenciada, ExcecaoBanco {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			MotoristaPosicaoArea result = obterPosicaoMotoristaArea(param, em);
			/*
			 * IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO =
			 * fabricaDAO.getPostgresMotoristaPosicaoAreaDAO(); Area area =
			 * validarAreaDoMotorista(Double.parseDouble(param.getLatitude()),
			 * Double.parseDouble(param.getLongitude()), em); Motorista
			 * motorista = new Motorista();
			 * motorista.setCodigo(param.getCodMotorista());
			 * MotoristaPosicaoArea motoristaPosicao = new
			 * MotoristaPosicaoArea(); motoristaPosicao.setMotorista(motorista);
			 * 
			 * // motoristaPosicao.setAtivo("S"); List<MotoristaPosicaoArea>
			 * lista = motoristaPosicaoAreaDAO.findByExample(motoristaPosicao,
			 * em); boolean encontrouMoto = false; if (lista != null &&
			 * lista.size() > 0) { for (MotoristaPosicaoArea motoPos : lista) {
			 * if (motoPos.getArea().getCodigo() != area.getCodigo()) { //
			 * desativa o motorista na área antiga lista.get(0).setAtivo("N");
			 * motoristaPosicao = motoristaPosicaoAreaDAO.save(motoPos, em); }
			 * else if (motoPos.getAtivo().equals("N")) { //Verifico se o
			 * motorista estava desativado na área atual e ativo ele
			 * motoPos.setAtivo("S"); motoPos.setEntrada(new Date()); Integer
			 * maiorPosicao =
			 * motoristaPosicaoAreaDAO.obterMaiorPosicaoArea(area.getCodigo(),
			 * em); motoPos.setPosicao(maiorPosicao == null ? 1 : maiorPosicao +
			 * 1); motoristaPosicao = motoristaPosicaoAreaDAO.save(motoPos, em);
			 * encontrouMoto = true; }else{ //Se o motorista já estava ativo na
			 * área atual retorno o registro atual motoristaPosicao = motoPos;
			 * encontrouMoto = true; }
			 * 
			 * } } if (!encontrouMoto){ //se o motorista ainda não estava
			 * cadastrado na área atual cadastro ele motoristaPosicao = new
			 * MotoristaPosicaoArea(); motoristaPosicao.setMotorista(motorista);
			 * motoristaPosicao.setArea(area); motoristaPosicao.setAtivo("S");
			 * motoristaPosicao.setEntrada(new Date()); Integer maiorPosicao =
			 * motoristaPosicaoAreaDAO.obterMaiorPosicaoArea(area.getCodigo(),
			 * em); motoristaPosicao.setPosicao(maiorPosicao == null ? 1 :
			 * maiorPosicao + 1); motoristaPosicao =
			 * motoristaPosicaoAreaDAO.save(motoristaPosicao, em); }
			 */

			emUtil.commitTransaction(transaction);
			return result;
		} catch (ExcecaoMotoristaPosicaoArea e) {
			emUtil.commitTransaction(transaction);
			throw e;
		} catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw e;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter posição na área do motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public MotoristaPosicaoArea obterPosicaoMotoristaArea(VerificaPosicaoParam param, EntityManager em)
			throws ExcecaoNegocio, ExcecaoMotoristaPosicaoArea {

		try {

			IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();
			Area area = validarAreaDoMotorista(Double.parseDouble(param.getLatitude()),
					Double.parseDouble(param.getLongitude()), param.getCodMotorista(), param.getLoginMotorista(), em);
			if (param.getCodUltimaArea() == null || (param.getCodUltimaArea() != null && param.getCodUltimaArea() != area.getCodigo())) {

				Motorista motorista = new Motorista();
				motorista.setCodigo(param.getCodMotorista());
				MotoristaPosicaoArea motoristaPosicao = new MotoristaPosicaoArea();
				motoristaPosicao.setMotorista(motorista);

				List<MotoristaPosicaoArea> listOrdem = motoristaPosicaoAreaDAO.obterMotoristasPorArea(area, em);
				// reordena a lista de posição na área
				int count = 1;
				for (MotoristaPosicaoArea motPos : listOrdem) {
					motPos.setPosicao(count);
					motoristaPosicaoAreaDAO.save(motPos, em);
					count++;
				}
				// motoristaPosicao.setAtivo("S");
				List<MotoristaPosicaoArea> lista = motoristaPosicaoAreaDAO.findByExample(motoristaPosicao, em);
				boolean encontrouMoto = false;

				if (lista != null && lista.size() > 0) {
					for (MotoristaPosicaoArea motoPos : lista) {
						if (motoPos.getArea().getCodigo() != area.getCodigo()) {
							// desativa o motorista na área antiga
							motoPos.setAtivo("N");
							// motoristaPosicao =
							// motoristaPosicaoAreaDAO.save(motoPos, em);
						} else if (motoPos.getAtivo().equals("N")) { // Verifico
																		// se
																		// o
																		// motorista
																		// estava
																		// desativado
																		// na
																		// área
																		// atual
																		// e
																		// ativo
																		// ele
							motoPos.setAtivo("S");
							motoPos.setEntrada(new Date());
							// Integer maiorPosicao =
							// motoristaPosicaoAreaDAO.obterMaiorPosicaoArea(area.getCodigo(),
							// em);
							motoPos.setPosicao(count);
							motoPos.setLatitude(param.getLatitude());
							motoPos.setLongitude(param.getLongitude());
							motoristaPosicao = motoristaPosicaoAreaDAO.save(motoPos, em);
							encontrouMoto = true;
						} else { // Se o motorista já estava ativo na área atual
									// retorno o registro atual
							motoPos.setLatitude(param.getLatitude());
							motoPos.setLongitude(param.getLongitude());
							motoristaPosicao = motoristaPosicaoAreaDAO.save(motoPos, em);
							encontrouMoto = true;
						}

					}
				}
				if (!encontrouMoto) { // se o motorista ainda não estava
										// cadastrado
										// na área atual cadastro ele
					motoristaPosicao = new MotoristaPosicaoArea();
					motoristaPosicao.setMotorista(motorista);
					motoristaPosicao.setArea(area);
					motoristaPosicao.setAtivo("S");
					motoristaPosicao.setEntrada(new Date());
					// Integer maiorPosicao =
					// motoristaPosicaoAreaDAO.obterMaiorPosicaoArea(area.getCodigo(),
					// em);
					motoristaPosicao.setLatitude(param.getLatitude());
					motoristaPosicao.setLongitude(param.getLongitude());
					motoristaPosicao.setPosicao(count);
					motoristaPosicao = motoristaPosicaoAreaDAO.save(motoristaPosicao, em);
				}
				return motoristaPosicao;
			}else
				return null;

			
		} catch (ExcecaoMotoristaPosicaoArea e) {
			
			throw e;
		} catch (ExcecaoNegocio e) {
			throw e;
		} catch (Exception e) {
			throw new ExcecaoNegocio("Falha ao tentar obter posição na área do motorista.", e);
		}
	}
	
	private void desativarMotoristaEmAreas(Integer codMotorista, EntityManager em) throws ExcecaoBancoConexao, ExcecaoBanco {
		Motorista motorista = new Motorista();
		motorista.setCodigo(codMotorista);
		MotoristaPosicaoArea motoristaPosicao = new MotoristaPosicaoArea();
		motoristaPosicao.setMotorista(motorista);
		IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();		
		List<MotoristaPosicaoArea> lista = motoristaPosicaoAreaDAO.findByExample(motoristaPosicao, em);
		
		for (MotoristaPosicaoArea motPos : lista) {
			motPos.setAtivo("N");
			motoristaPosicaoAreaDAO.save(motPos, em);			
		}

	}

	public void atualizarPosicoesArea(Area area, EntityManager em) throws ExcecaoBancoConexao, ExcecaoBanco {

		IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();

		List<MotoristaPosicaoArea> listOrdem = motoristaPosicaoAreaDAO.obterMotoristasPorArea(area, em);
		// reordena a lista de posição na área
		int count = 1;

		// lista de motoristas para enviar mensagem do socket de atualizar
		// posição
		List<RetornoVerificaPosicao> listaMotorista = new ArrayList<RetornoVerificaPosicao>();
		RetornoVerificaPosicao retornoPos;
		for (MotoristaPosicaoArea motPos : listOrdem) {
			motPos.setPosicao(count);
			motoristaPosicaoAreaDAO.save(motPos, em);
			count++;
			retornoPos = new RetornoVerificaPosicao();
			retornoPos.setAreaAtual(motPos.getArea());
			retornoPos.setPosicaoNaArea(motPos.getPosicao());
			retornoPos.setCodMotorista(motPos.getMotorista().getCodigo());
			listaMotorista.add(retornoPos);
		}

		ControleSessaoWS.enviarMensagemListaMotorista(listaMotorista);

	}

}
