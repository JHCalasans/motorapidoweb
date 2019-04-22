package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.model.map.LatLng;

import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IChamadaDAO;
import br.com.motorapido.dao.IClienteDAO;
import br.com.motorapido.dao.IEnderecoClienteDAO;
import br.com.motorapido.dao.IMensagemFuncionarioDAO;
import br.com.motorapido.dao.IMensagemFuncionarioMotoristaDAO;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.dao.IMotoristaPosicaoAreaDAO;
import br.com.motorapido.dao.ISituacaoChamadaDAO;
import br.com.motorapido.entity.Area;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.entity.Chamada;
import br.com.motorapido.entity.EnderecoCliente;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.entity.Local;
import br.com.motorapido.entity.Logradouro;
import br.com.motorapido.entity.MensagemFuncionario;
import br.com.motorapido.entity.MensagemFuncionarioMotorista;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaAparelho;
import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.entity.SituacaoChamada;
import br.com.motorapido.entity.Usuario;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.enums.SituacaoChamadaEnum;
import br.com.motorapido.mbean.SimpleController;
import br.com.motorapido.util.CoordenadaPontoUtil;
import br.com.motorapido.util.CoordenadasAreaUtil;
import br.com.motorapido.util.FuncoesUtil;
import br.com.motorapido.util.GoogleWSUtil;
import br.com.motorapido.util.PushNotificationUtil;
import br.com.motorapido.util.RetornoMatrixGoogleAPI;
import br.com.motorapido.util.ws.params.NovaChamadaParam;
import br.com.motorapido.util.ws.retornos.RetornoChamadaUsuario;

public class ChamadaBO extends MotoRapidoBO {

	private static ChamadaBO instance;

	private ChamadaBO() {

	}

	public static ChamadaBO getInstance() {
		if (instance == null)
			instance = new ChamadaBO();

		return instance;
	}

	public Chamada iniciarChamada(Chamada chamada, Funcionario funcionario, List<Caracteristica> caracteristicas)
			throws ExcecaoNegocio {
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

			// salva também o primeiro endereço da primeira chamada do cliente
			if (salvouCliente) {
				IEnderecoClienteDAO endereceoClienteDAO = fabricaDAO.getPostgresEnderecoClienteDAO();
				EnderecoCliente origemEndereco = new EnderecoCliente();
				origemEndereco.setBairro(chamada.getBairroOrigem());
				origemEndereco.setCep(chamada.getCepOrigem());
				origemEndereco.setCidade(chamada.getCidadeOrigem());
				origemEndereco.setComplemento(chamada.getComplementoOrigem());
				origemEndereco.setEstado("SE");
				origemEndereco.setLatitude(chamada.getLatitudeOrigem());
				origemEndereco.setLogradouro(chamada.getLogradouroOrigem());
				origemEndereco.setLongitude(chamada.getLongitudeOrigem());
				origemEndereco.setNumero(chamada.getNumeroOrigem());
				origemEndereco.setCliente(chamada.getCliente());
				origemEndereco = endereceoClienteDAO.save(origemEndereco, em);
			}

			chamada.setArea(validarAreaDoChamado(Double.parseDouble(chamada.getLatitudeOrigem()),
					Double.parseDouble(chamada.getLongitudeOrigem()), em));

			// chamada.setDestino(destino.getCodigo() == null ? null : destino);
			chamada.setDataCriacao(new Date());
			chamada.setFuncionario(funcionario);
			SituacaoChamada situacaChamada = new SituacaoChamada();
			ISituacaoChamadaDAO situacaoChamadaDAO = fabricaDAO.getPostgresSituacaoChamadaDAO();
			situacaChamada = situacaoChamadaDAO.findById(SituacaoChamadaEnum.PENDENTE.getCodSituacao(), em);
			chamada.setSituacaoChamada(situacaChamada);
			
			RetornoMatrixGoogleAPI retornoMatrix = GoogleWSUtil.buscarDistanciaAPercorrer(
					chamada.getLatitudeOrigem().toString() + "," + chamada.getLongitudeOrigem(),
					chamada.getLatitudeDestino().toString() + "," + chamada.getLongitudeDestino(),
					FuncoesUtil.getParam(ParametroEnum.CHAVE_MAPS.getCodigo(), em));
			
			chamada.setDistanciaPrevista(retornoMatrix.getRows()[0].getElements()[0].getDistance().getText());
			
			chamada.setValorPrevisto(
					FuncoesUtil.formatarBigDecimal(calculoValorPrevisto(retornoMatrix.getRows()[0].getElements()[0].getDistance().getValue(),em)));


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
	
	private Float calculoValorPrevisto(String distanciaExata, EntityManager em) throws ExcecaoBancoConexao, ExcecaoBanco{
		
		
		Float valor = 0.0F;
		
		//Buscando valores de parâmetros para cálculo de valor da corrida
		
		//Obtém Valor cobrado no início da corrida
		Float valorInicio =  Float.parseFloat(FuncoesUtil.getParam(ParametroEnum.VALOR_INICIO_CORRIDA.getCodigo(), em));
		
		//Obtém distância que será considerada o início da corrida
		String distanciaInicio = FuncoesUtil.getParam(ParametroEnum.DISTANCIA_INICIO_CORRIDA.getCodigo(), em);		
		Float distanciInicioKM = Float.parseFloat(distanciaInicio.split("KM")[0]);
		
		//Verifico a distancia da corrida em KM para usar no cálculo
		Float distanciaKm = Float.parseFloat(distanciaExata)/1000 ;
		if( distanciaKm > distanciInicioKM ){
			valor =  valorInicio;
			
			//Obtém distância que será considerada como fator de valor final
			Float valorPorDistancia = Float.parseFloat(FuncoesUtil.getParam(ParametroEnum.VALOR_POR_DISTANCIA.getCodigo(), em));		

		/*	String metricaDistancia = FuncoesUtil.getParam(ParametroEnum.METRICA_DISTANCIA_CALCULO.getCodigo(), em);		
			Float metricaDistanciaKM = Float.parseFloat(metricaDistancia.split("KM")[0]);
			*/
			
			
			distanciaKm = distanciaKm - distanciInicioKM;
			
			valor = valor + (distanciaKm * valorPorDistancia);
		}else
			valor = valorInicio;
					
		
		return valor;
	}

	public RetornoChamadaUsuario iniciarChamadaApp(NovaChamadaParam param) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			Chamada chamada = new Chamada();
			chamada.setBairroDestino(param.getBairroDestino());
			chamada.setBairroOrigem(param.getBairroOrigem());
			chamada.setCepDestino(param.getCepDestino());
			chamada.setCepOrigem(param.getCepOrigem());
			chamada.setCidadeDestino(param.getCidadeDestino());
			chamada.setCidadeOrigem(param.getCidadeOrigem());
			chamada.setComplementoDestino(param.getComplementoDestino());
			chamada.setComplementoOrigem(param.getComplementoOrigem());
			chamada.setLatitudeDestino(param.getLatitudeDestino());
			chamada.setLatitudeOrigem(param.getLatitudeOrigem());
			chamada.setLogradouroDestino(param.getLogradouroDestino());
			chamada.setLogradouroOrigem(param.getLogradouroOrigem());
			chamada.setLongitudeDestino(param.getLatitudeDestino());
			chamada.setLongitudeOrigem(param.getLongitudeOrigem());
			chamada.setNumeroDestino(param.getNumeroDestino());
			chamada.setNumeroOrigem(param.getNumeroOrigem());
			chamada.setObservacao(param.getObservacao());
			chamada.setUsuario(new Usuario(param.getCodUsuario()));

			chamada.setArea(validarAreaDoChamado(Double.parseDouble(chamada.getLatitudeOrigem()),
					Double.parseDouble(chamada.getLongitudeOrigem()), em));

			// chamada.setDestino(destino.getCodigo() == null ? null : destino);
			chamada.setDataCriacao(new Date());
			SituacaoChamada situacaChamada = new SituacaoChamada();
			ISituacaoChamadaDAO situacaoChamadaDAO = fabricaDAO.getPostgresSituacaoChamadaDAO();
			situacaChamada = situacaoChamadaDAO.findById(SituacaoChamadaEnum.PENDENTE.getCodSituacao(), em);
			chamada.setSituacaoChamada(situacaChamada);
			
			RetornoMatrixGoogleAPI retornoMatrix = GoogleWSUtil.buscarDistanciaAPercorrer(
					chamada.getLatitudeOrigem().toString() + "," + chamada.getLongitudeOrigem(),
					chamada.getLatitudeDestino().toString() + "," + chamada.getLongitudeDestino(),
					FuncoesUtil.getParam(ParametroEnum.CHAVE_MAPS.getCodigo(), em));
			
			chamada.setDistanciaPrevista(retornoMatrix.getRows()[0].getElements()[0].getDistance().getText());
			
			chamada.setValorPrevisto(
					FuncoesUtil.formatarBigDecimal(calculoValorPrevisto(retornoMatrix.getRows()[0].getElements()[0].getDistance().getValue(),em)));


			chamada.setDataCriacao(new Date());
			chamada.setDataInicioEspera(new Date());
			chamada = chamadaDAO.save(chamada, em);
			
			
			
			RetornoChamadaUsuario retorno = new RetornoChamadaUsuario();
			retorno.setCodChamada(chamada.getCodigo());
			retorno.setDataChamada(chamada.getDataCriacao());
			retorno.setDestino(param.getLogradouroDestino() + " - " + param.getBairroDestino());
			retorno.setOrigem(param.getLogradouroOrigem() + " - " + param.getBairroOrigem());
			retorno.setValorPrevisto(chamada.getValorPrevisto());
			
			emUtil.commitTransaction(transaction);
			
			SimpleController.getListaChamadasEmEspera().add(chamada);
			
			
			return retorno;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar iniciar chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void enviarMsgChamadaMotorista(Chamada chamada) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();

			Area area = validarAreaDoChamado(Double.parseDouble(chamada.getLatitudeOrigem()),
					Double.parseDouble(chamada.getLongitudeOrigem()), em);
			MotoristaPosicaoArea motoristaPosicaoArea = new MotoristaPosicaoArea();
			motoristaPosicaoArea.setArea(area);
			motoristaPosicaoArea.setAtivo("S");
			IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();
			List<MotoristaPosicaoArea> listaMotoristas = motoristaPosicaoAreaDAO.findByExample(motoristaPosicaoArea, em,
					motoristaPosicaoAreaDAO.BY_POS_ASC);
			if (listaMotoristas != null && listaMotoristas.size() > 0) {

				List<String> listaParaNotificacao = new ArrayList<String>();
				// Busco aparelho do motorista para enviar notificação
				IMotoristaAparelhoDAO motoristaAparelhoDAO = fabricaDAO.getPostgresMotoristaAparelhoDAO();
				MotoristaAparelho motoristaAparelho = new MotoristaAparelho();
				motoristaAparelho.setAtivo("S");
				motoristaAparelho.setCodMotorista(listaMotoristas.get(0).getMotorista().getCodigo());
				List<MotoristaAparelho> lista = motoristaAparelhoDAO.findByExample(motoristaAparelho, em);
				if (lista != null && lista.size() > 0)
					listaParaNotificacao.add(lista.get(0).getIdPush());

				PushNotificationUtil.enviarNotificacaoPlayerId(
						FuncoesUtil.getParam(ParametroEnum.CHAVE_REST_PUSH.getCodigo(), em),
						FuncoesUtil.getParam(ParametroEnum.CHAVE_APP_ID_ONE_SIGNAL.getCodigo(), em),
						listaParaNotificacao, "Nova Cahmada");
			}
			// Se não tiver nenhum motorista disponível na área a chamada vai
			// para lista de espera geral
			else
				SimpleController.getListaChamadasEmEsperaGeral().add(chamada);

			emUtil.commitTransaction(transaction);

		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar enviar mensagem.", e);
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
			SituacaoChamada situacaChamada = new SituacaoChamada();
			situacaChamada.setCodigo(SituacaoChamadaEnum.CANCELADA.getCodSituacao());
			chamada.setSituacaoChamada(situacaChamada);
			chamada.setDataCancelamento(new Date());
			chamadaDAO.save(chamada, em);
			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar cancelar chamadas.", e);
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
