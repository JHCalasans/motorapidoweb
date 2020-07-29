package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.model.map.LatLng;

import com.google.gson.Gson;

import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IChamadaDAO;
import br.com.motorapido.dao.IChamadaVeiculoDAO;
import br.com.motorapido.dao.IClienteDAO;
import br.com.motorapido.dao.IEnderecoClienteDAO;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.dao.IMotoristaDAO;
import br.com.motorapido.dao.IMotoristaPosicaoAreaDAO;
import br.com.motorapido.dao.ISituacaoChamadaDAO;
import br.com.motorapido.dao.IVeiculoDAO;
import br.com.motorapido.entity.Area;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.entity.Chamada;
import br.com.motorapido.entity.ChamadaVeiculo;
import br.com.motorapido.entity.EnderecoCliente;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.entity.MotoristaAparelho;
import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.entity.SituacaoChamada;
import br.com.motorapido.entity.Usuario;
import br.com.motorapido.entity.Veiculo;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.enums.SituacaoChamadaEnum;
import br.com.motorapido.mbean.SimpleController;
import br.com.motorapido.util.ControleSessaoWS;
import br.com.motorapido.util.CoordenadaPontoUtil;
import br.com.motorapido.util.CoordenadasAreaUtil;
import br.com.motorapido.util.FuncoesUtil;
import br.com.motorapido.util.GoogleWSUtil;
import br.com.motorapido.util.PushNotificationUtil;
import br.com.motorapido.util.RetornoMatrixGoogleAPI;
import br.com.motorapido.util.ws.params.CalculoValorParam;
import br.com.motorapido.util.ws.params.CancelarChamadaParam;
import br.com.motorapido.util.ws.params.NovaChamadaParam;
import br.com.motorapido.util.ws.params.SelecaoChamadaParam;
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

	public String calcularValorChamada(Chamada chamada) throws ExcecaoNegocio {

		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		String retorno = "R$ ";
		try {
			transaction.begin();
			RetornoMatrixGoogleAPI retornoMatrix = GoogleWSUtil.buscarDistanciaAPercorrer(
					chamada.getLatitudeOrigem().toString() + "," + chamada.getLongitudeOrigem(),
					chamada.getLatitudeDestino().toString() + "," + chamada.getLongitudeDestino(),
					FuncoesUtil.getParam(ParametroEnum.CHAVE_MAPS.getCodigo(), em));

			chamada.setDistanciaPrevista(retornoMatrix.getRows()[0].getElements()[0].getDistance().getText());

			chamada.setValorPrevisto(FuncoesUtil.formatarBigDecimal(
					calculoValorPrevisto(retornoMatrix.getRows()[0].getElements()[0].getDistance().getValue(), em)));
			
			retorno = retorno + chamada.getValorPrevisto();
			
			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar calcular valor da chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
		return retorno;
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

			chamada.setValorPrevisto(FuncoesUtil.formatarBigDecimal(
					calculoValorPrevisto(retornoMatrix.getRows()[0].getElements()[0].getDistance().getValue(), em)));

			chamada.setValorFinal(chamada.getValorPrevisto().toString());
			Chamada chamadaTemp = chamadaDAO.save(chamada, em);
			chamada.setCodigo(chamadaTemp.getCodigo());

			emUtil.commitTransaction(transaction);

			SimpleController.getListaChamadasEmEspera().add(chamada);
			return chamada;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar iniciar chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public Chamada iniciarChamadaCliente(Chamada chamada)
			throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			

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

			chamada.setValorPrevisto(FuncoesUtil.formatarBigDecimal(
					calculoValorPrevisto(retornoMatrix.getRows()[0].getElements()[0].getDistance().getValue(), em)));

			chamada.setValorFinal(chamada.getValorPrevisto().toString());
			Chamada chamadaTemp = chamadaDAO.save(chamada, em);
			chamada.setCodigo(chamadaTemp.getCodigo());

			emUtil.commitTransaction(transaction);

			SimpleController.getListaChamadasEmEspera().add(chamada);
			return chamada;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar iniciar pedido.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void iniciarCorrida(SelecaoChamadaParam param) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			Chamada retorno = SimpleController.getListaChamadasAceitas().stream()
					.filter(ch -> ch.getCodigo().equals(param.getChamada().getCodigo())).findFirst().get();

			retorno.setSituacaoChamada(new SituacaoChamada(SituacaoChamadaEnum.EM_CORRIDA.getCodigo()));
			retorno.setDataInicioCorrida(param.getInicioCorrida());
			retorno.setLatitudeInicioCorrida(param.getChamada().getLatitudeInicioCorrida());
			retorno.setLongitudeInicioCorrida(param.getChamada().getLongitudeInicioCorrida());

			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			chamadaDAO.save(retorno, em);

			emUtil.commitTransaction(transaction);

			SimpleController.getListaChamadasAceitas().remove(retorno);
			SimpleController.getListaChamadasEmCorrida().add(retorno);

		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar aceitar chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void finalizarCorrida(CancelarChamadaParam param) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();

			Chamada retorno = SimpleController.getListaChamadasEmCorrida().stream()
					.filter(ch -> ch.getCodigo().equals(param.getChamada().getCodigo())).findFirst().get();

			retorno.setSituacaoChamada(new SituacaoChamada(SituacaoChamadaEnum.FINALIZADA.getCodigo()));
			retorno.setDataFimCorrida(param.getChamada().getDataFimCorrida());
			retorno.setLatitudeFinalCorrida(param.getChamada().getLatitudeFinalCorrida());
			retorno.setLongitudeFinalCorrida(param.getChamada().getLongitudeFinalCorrida());
			// DecimalFormat dc = new DecimalFormat("0.00");
			// retorno.setValorFinal(Float.parseFloat(dc.format(param.getChamada().getValorFinal()).replace(",",
			// ".")));
			retorno.setValorFinal(
					String.format("%.2f", Float.parseFloat(param.getChamada().getValorFinal().replace(',', '.'))));
			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			chamadaDAO.save(retorno, em);

			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();
			Veiculo veiculo = veiculoDAO.obterVeiculoPorId(param.getCodVeiculo(), em);
			veiculo.getMotorista().setDisponivel("S");

			emUtil.commitTransaction(transaction);

			SimpleController.getListaChamadasEmCorrida().remove(retorno);

		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar finalizar chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public Float servicoCalculaValor(CalculoValorParam param) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();

			RetornoMatrixGoogleAPI retornoMatrix = GoogleWSUtil.buscarDistanciaAPercorrer(
					param.getLatitudeOrigem() + "," + param.getLongitudeOrigem(),
					param.getLatitudeDestino() + "," + param.getLongitudeDestino(),
					FuncoesUtil.getParam(ParametroEnum.CHAVE_MAPS.getCodigo(), em));

			Float retorno = calculoValorPrevisto(retornoMatrix.getRows()[0].getElements()[0].getDistance().getValue(),
					em);

			emUtil.commitTransaction(transaction);

			return retorno;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar calcular valor.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	private Float calculoValorPrevisto(String distanciaExata, EntityManager em)
			throws ExcecaoBancoConexao, ExcecaoBanco {

		Float valor = 0.0F;

		// Buscando valores de parâmetros para cálculo de valor da corrida

		// Obtém Valor cobrado no início da corrida
		Float valorInicio = Float.parseFloat(FuncoesUtil.getParam(ParametroEnum.VALOR_INICIO_CORRIDA.getCodigo(), em));

		// Obtém distância que será considerada o início da corrida
		// String distanciaInicio =
		// FuncoesUtil.getParam(ParametroEnum.DISTANCIA_INICIO_CORRIDA.getCodigo(),
		// em);
		// Float distanciInicioKM =
		// Float.parseFloat(distanciaInicio.split("KM")[0]);

		// Verifico a distancia da corrida em KM para usar no cálculo
		Float distanciaKm = Float.parseFloat(distanciaExata) / 1000;

		// Obtém distância que será considerada como fator de valor final
		Float valorPorDistancia = Float
				.parseFloat(FuncoesUtil.getParam(ParametroEnum.VALOR_POR_DISTANCIA.getCodigo(), em));

		// String metricaDistancia =
		// FuncoesUtil.getParam(ParametroEnum.METRICA_DISTANCIA_CALCULO.getCodigo(),
		// em);
		// Float metricaDistanciaKM =
		// Float.parseFloat(metricaDistancia.split("KM")[0]);

		valor = valorInicio + (distanciaKm * valorPorDistancia);

		/*
		 * if (distanciaKm > distanciInicioKM) { valor = valorInicio;
		 * 
		 * // Obtém distância que será considerada como fator de valor final
		 * Float valorPorDistancia = Float
		 * .parseFloat(FuncoesUtil.getParam(ParametroEnum.VALOR_POR_DISTANCIA.
		 * getCodigo(), em));
		 * 
		 * 
		 * String metricaDistancia =
		 * FuncoesUtil.getParam(ParametroEnum.METRICA_DISTANCIA_CALCULO.
		 * getCodigo(), em); Float metricaDistanciaKM =
		 * Float.parseFloat(metricaDistancia.split("KM")[0]);
		 * 
		 * 
		 * distanciaKm = distanciaKm - distanciInicioKM;
		 * 
		 * valor = valor + (distanciaKm * valorPorDistancia); } else valor =
		 * valorInicio;
		 */

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
			chamada.setLongitudeDestino(param.getLongitudeDestino());
			chamada.setLongitudeOrigem(param.getLongitudeOrigem());
			chamada.setNumeroDestino(param.getNumeroDestino());
			chamada.setNumeroOrigem(param.getNumeroOrigem());
			chamada.setObservacao(param.getObservacao());
			chamada.setUsuario(new Usuario(param.getCodUsuario()));

			chamada.setArea(validarAreaDoChamado(Double.parseDouble(chamada.getLatitudeOrigem()),
					Double.parseDouble(chamada.getLongitudeOrigem()), em));

			// chamada.setDestino(destino.getCodigo() == null ? null : destino);
			chamada.setDataCriacao(new Date());

			chamada.setSituacaoChamada(new SituacaoChamada(SituacaoChamadaEnum.PENDENTE.getCodigo()));

			RetornoMatrixGoogleAPI retornoMatrix = GoogleWSUtil.buscarDistanciaAPercorrer(
					chamada.getLatitudeOrigem().toString() + "," + chamada.getLongitudeOrigem(),
					chamada.getLatitudeDestino().toString() + "," + chamada.getLongitudeDestino(),
					FuncoesUtil.getParam(ParametroEnum.CHAVE_MAPS.getCodigo(), em));

			chamada.setDistanciaPrevista(retornoMatrix.getRows()[0].getElements()[0].getDistance().getText());

			chamada.setValorPrevisto(FuncoesUtil.formatarBigDecimal(
					calculoValorPrevisto(retornoMatrix.getRows()[0].getElements()[0].getDistance().getValue(), em)));

			chamada.setDataCriacao(new Date());
			chamada.setDataInicioEspera(new Date());
			chamada = chamadaDAO.save(chamada, em);
			ISituacaoChamadaDAO situacaoChamadaDAO = fabricaDAO.getPostgresSituacaoChamadaDAO();
			SituacaoChamada situacaChamada = situacaoChamadaDAO.findById(SituacaoChamadaEnum.PENDENTE.getCodSituacao(),
					em);
			chamada.setSituacaoChamada(situacaChamada);

			RetornoChamadaUsuario retorno = new RetornoChamadaUsuario();
			retorno.setCodChamada(chamada.getCodigo());
			retorno.setDataChamada(chamada.getDataCriacao());
			retorno.setDestino(param.getLogradouroDestino() + " - " + param.getBairroDestino());
			retorno.setOrigem(param.getLogradouroOrigem() + " - " + param.getBairroOrigem());
			retorno.setValorPrevisto(chamada.getValorPrevisto());

			emUtil.commitTransaction(transaction);

			SimpleController.getListaChamadasEmEspera().add(chamada);

			return retorno;
		} catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw e;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar iniciar chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	// calculo se tempo de resposta do motorista é 10 segundos maior que o tempo
	// limite
	private boolean tempoMaiorQueLimite(Integer tempoEspera, Date dataBase) {
		return (new Date().getTime() - dataBase.getTime()) / 1000 > (tempoEspera + 10);
	}

	public void enviarMsgChamadaMotorista(Chamada chamada) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();

			Area area = validarAreaDoChamado(Double.parseDouble(chamada.getLatitudeOrigem()),
					Double.parseDouble(chamada.getLongitudeOrigem()), em);
			/*
			 * MotoristaPosicaoArea motoristaPosicaoArea = new
			 * MotoristaPosicaoArea(); motoristaPosicaoArea.setArea(area);
			 * motoristaPosicaoArea.setAtivo("S");
			 */
			IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();
			List<MotoristaPosicaoArea> listaMotoristas = motoristaPosicaoAreaDAO.obterMotoristasChamadaPorArea(area,
					chamada.getCodigo(), em);

			IChamadaVeiculoDAO chamadaVeiculoDAO = fabricaDAO.getPostgresChamadaVeiculoDAO();
			ChamadaVeiculo chamadaVeiculo = new ChamadaVeiculo();
			// Busco chamadas anteriores de outros motoristas e desativo elas
			List<ChamadaVeiculo> listaChamada = chamadaVeiculoDAO.obterChamadaAtiva(chamada.getCodigo(), em);
			boolean passouLimite = true;
			for (ChamadaVeiculo chamadaV : listaChamada) {
				// verifico se o tempo de resposta já ultrapassou o tempo limite
				// mais 10 segundos
				if (tempoMaiorQueLimite(
						Integer.parseInt(FuncoesUtil.getParam(ParametroEnum.TEMPO_ESPERA_ACEITACAO.getCodigo(), em)),
						chamadaV.getDataCriacao())) {

					chamadaV.setFlgUltimoMovimento("N");
					chamadaV.setFlgAceita("N");
					chamadaVeiculoDAO.save(chamadaV, em);
				} else
					passouLimite = false;
			}
			// Se não passou o limite de espera + 10 segundos saio do método
			if (!passouLimite) {
				emUtil.commitTransaction(transaction);
				return;
			}
			if (listaMotoristas != null && listaMotoristas.size() > 0) {

				List<String> listaParaNotificacao = new ArrayList<String>();

				// Busco aparelho do motorista para enviar notificação
				IMotoristaAparelhoDAO motoristaAparelhoDAO = fabricaDAO.getPostgresMotoristaAparelhoDAO();
				MotoristaAparelho motoristaAparelho = new MotoristaAparelho();
				motoristaAparelho.setAtivo("S");
				motoristaAparelho.setMotorista(listaMotoristas.get(0).getMotorista());
				List<MotoristaAparelho> lista = motoristaAparelhoDAO
						.obterAparelhoPorMotorista(listaMotoristas.get(0).getMotorista().getCodigo(), em);
				if (lista != null && lista.size() > 0) {

					listaParaNotificacao.add(lista.get(0).getIdPush());

					IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();
					Veiculo veiculo = veiculoDAO.obterVeiculosEmUsoPorMotorista(lista.get(0).getMotorista().getCodigo(),
							em);
					chamadaVeiculo.setChamada(chamada);
					chamadaVeiculo.setFlgAceita("A");
					chamadaVeiculo.setFlgUltimoMovimento("S");
					chamadaVeiculo.setDataCriacao(new Date());
					if (veiculo == null)
						throw new ExcecaoNegocio("Não existe veículo em uso para o motorista "
								+ lista.get(0).getMotorista().getCodigo());
					chamadaVeiculo.setVeiculo(veiculo);

					chamadaVeiculo = chamadaVeiculoDAO.save(chamadaVeiculo, em);

					chamada.setCodChamadaVeiculo(chamadaVeiculo.getCodigo());
					Gson gson = new Gson();
					Chamada chamadaJson = new Chamada();
					chamadaJson = chamada;
					chamadaJson.setFuncionario(null);
					chamadaJson.setTempoParaResposta(
							Integer.parseInt(FuncoesUtil.getParam(ParametroEnum.TEMPO_ESPERA_ACEITACAO.getCodigo())));
					String json = gson.toJson(chamadaJson);
					boolean enviou = ControleSessaoWS
							.enviarMensagemMotoristaChamada(lista.get(0).getMotorista().getCodigo(), json);

					if (!enviou)
						PushNotificationUtil.enviarNotificacaoPlayerIdChamada(
								FuncoesUtil.getParam(ParametroEnum.CHAVE_REST_PUSH.getCodigo(), em),
								FuncoesUtil.getParam(ParametroEnum.CHAVE_APP_ID_ONE_SIGNAL.getCodigo(), em),
								listaParaNotificacao, "Nova Chamada", chamadaVeiculo.getCodigo().toString(),
								FuncoesUtil.getParam(ParametroEnum.TEMPO_ESPERA_ACEITACAO.getCodigo(), em));

				}
			}
			// Se não tiver nenhum motorista disponível na área a chamada vai
			// para lista de espera geral
			else {
				SituacaoChamada situacaChamada = new SituacaoChamada(
						SituacaoChamadaEnum.PENDENTE_GERAL.getCodSituacao());
				// ISituacaoChamadaDAO situacaoChamadaDAO =
				// fabricaDAO.getPostgresSituacaoChamadaDAO();
				// situacaChamada =
				// situacaoChamadaDAO.findById(SituacaoChamadaEnum.PENDENTE_GERAL.getCodSituacao(),
				// em);
				chamada.setDataInicioEspera(new Date());

				chamada.setSituacaoChamada(situacaChamada);
				IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
				chamadaDAO.save(chamada, em);
				ControleSessaoWS.enviarAlertaPendencia("true");
				// SimpleController.getListaChamadasEmEsperaGeral().add(chamada);
			}

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

	public Chamada obterDetalhesChamada(Long codChamadaVeiculo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			IChamadaVeiculoDAO chamadaVeiculoDAO = fabricaDAO.getPostgresChamadaVeiculoDAO();
			ChamadaVeiculo chamadaVeiculo = chamadaVeiculoDAO.obterChamadaVeiculoPorCodigo(codChamadaVeiculo, em);

			Chamada retorno = chamadaDAO.obterChamadaPorCodigo(chamadaVeiculo.getChamada().getCodigo(), em);
			retorno.setCodChamadaVeiculo(chamadaVeiculo.getCodigo());
			emUtil.commitTransaction(transaction);
			return retorno;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter detalhes da chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public Chamada selecionarChamadaPendentePeloApp(SelecaoChamadaParam param) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();

			// IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			Chamada retorno = SimpleController.getListaChamadasEmEsperaGeral().stream()
					.filter(ch -> ch.getCodigo().equals(param.getChamada().getCodigo())).findFirst().get();
			if (retorno == null)
				throw new ExcecaoNegocio("Chamada já foi aceita por outro motorista.");

			retorno.setSituacaoChamada(new SituacaoChamada(SituacaoChamadaEnum.ACEITA.getCodigo()));

			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			chamadaDAO.save(retorno, em);

			IChamadaVeiculoDAO chamadaVeiculoDAO = fabricaDAO.getPostgresChamadaVeiculoDAO();
			ChamadaVeiculo chamadaVeiculo = new ChamadaVeiculo();
			chamadaVeiculo.setDataDecisao(param.getDataDecisao());
			chamadaVeiculo.setDataRecebimento(param.getDataDecisao());
			chamadaVeiculo.setFlgAceita("S");
			chamadaVeiculo.setFlgUltimoMovimento("S");
			chamadaVeiculo.setVeiculo(new Veiculo(param.getCodVeiculo()));
			chamadaVeiculo.setChamada(retorno);
			chamadaVeiculo.setDataCriacao(new Date());
			chamadaVeiculo = chamadaVeiculoDAO.save(chamadaVeiculo, em);

			Float valor = Float.parseFloat(FuncoesUtil.getParam(ParametroEnum.VALOR_POR_DISTANCIA.getCodigo(), em));
			String distanciaInicio = FuncoesUtil.getParam(ParametroEnum.DISTANCIA_INICIO_CORRIDA.getCodigo(), em);
			Float distancia = Float.parseFloat(distanciaInicio.split("KM")[0]);
			Float valorInicial = Float
					.parseFloat(FuncoesUtil.getParam(ParametroEnum.VALOR_INICIO_CORRIDA.getCodigo(), em));

			retorno.setPolylines(GoogleWSUtil.buscarRota(retorno, em));
			retorno.setPolylinesParaOrigem(GoogleWSUtil.buscarRota(param.getLatitudeAtual(), param.getLongitudeAtual(),
					retorno.getLatitudeOrigem(), retorno.getLongitudeOrigem(), em));

			// atualizo a base para que o motorista não esteja disponível
			// enquanto não cancelar ou finalizar esta chamada
			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();
			Veiculo veiculo = veiculoDAO.obterVeiculoPorId(param.getCodVeiculo(), em);
			veiculo.getMotorista().setDisponivel("N");

			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			motoristaDAO.save(veiculo.getMotorista(), em);

			IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();

			// Desativo motorista na área para nao receber chamada enquanto
			// estiver em corrida
			List<MotoristaPosicaoArea> lista = motoristaPosicaoAreaDAO
					.obterMotoristaAtivoCodigo(veiculo.getMotorista().getCodigo(), em);
			for (MotoristaPosicaoArea motoPos : lista) {
				motoPos.setAtivo("N");
				motoristaPosicaoAreaDAO.save(motoPos, em);
			}

			emUtil.commitTransaction(transaction);
			retorno.setCodChamadaVeiculo(chamadaVeiculo.getCodigo());
			retorno.setValorPorDistancia(valor);
			retorno.setDistanciaInicial(distancia);
			retorno.setValorFinal(valorInicial.toString());
			SimpleController.getListaChamadasEmEsperaGeral().remove(retorno);
			SimpleController.getListaChamadasAceitas().add(retorno);

			if (SimpleController.getListaChamadasEmEsperaGeral().size() < 1)
				ControleSessaoWS.enviarAlertaPendencia("false");

			return retorno;
		} catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw e;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar selecionar chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public Chamada aceitarChamadaPeloApp(SelecaoChamadaParam param) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();

			Chamada retorno = SimpleController.getListaChamadasEmEspera().stream()
					.filter(ch -> ch.getCodigo().equals(param.getChamada().getCodigo())).findFirst().get();
			if (retorno == null)
				throw new ExcecaoNegocio("Chamada já foi aceita por outro motorista.");

			retorno.setSituacaoChamada(new SituacaoChamada(SituacaoChamadaEnum.ACEITA.getCodigo()));

			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			chamadaDAO.save(retorno, em);

			IChamadaVeiculoDAO chamadaVeiculoDAO = fabricaDAO.getPostgresChamadaVeiculoDAO();
			ChamadaVeiculo chamadaVeiculo = new ChamadaVeiculo();
			chamadaVeiculo.setDataDecisao(param.getDataDecisao());
			chamadaVeiculo.setDataRecebimento(param.getDataDecisao());
			chamadaVeiculo.setFlgAceita("S");
			chamadaVeiculo.setFlgUltimoMovimento("S");
			chamadaVeiculo.setVeiculo(new Veiculo(param.getCodVeiculo()));
			chamadaVeiculo.setChamada(retorno);
			chamadaVeiculo.setDataCriacao(new Date());
			chamadaVeiculo = chamadaVeiculoDAO.save(chamadaVeiculo, em);

			Float valor = Float.parseFloat(FuncoesUtil.getParam(ParametroEnum.VALOR_POR_DISTANCIA.getCodigo(), em));
			String distanciaInicio = FuncoesUtil.getParam(ParametroEnum.DISTANCIA_INICIO_CORRIDA.getCodigo(), em);
			Float distancia = Float.parseFloat(distanciaInicio.split("KM")[0]);
			Float valorInicial = Float
					.parseFloat(FuncoesUtil.getParam(ParametroEnum.VALOR_INICIO_CORRIDA.getCodigo(), em));

			retorno.setPolylines(GoogleWSUtil.buscarRota(retorno, em));
			retorno.setPolylinesParaOrigem(GoogleWSUtil.buscarRota(param.getLatitudeAtual(), param.getLongitudeAtual(),
					retorno.getLatitudeOrigem(), retorno.getLongitudeOrigem(), em));

			// atualizo a base para que o motorista não esteja disponível
			// enquanto não cancelar ou finalizar esta chamada
			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();
			Veiculo veiculo = veiculoDAO.obterVeiculoPorId(param.getCodVeiculo(), em);
			veiculo.getMotorista().setDisponivel("N");

			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			motoristaDAO.save(veiculo.getMotorista(), em);

			IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();

			// Desativo motorista na área para nao receber chamada enquanto
			// estiver em corrida
			List<MotoristaPosicaoArea> lista = motoristaPosicaoAreaDAO
					.obterMotoristaAtivoCodigo(veiculo.getMotorista().getCodigo(), em);
			for (MotoristaPosicaoArea motoPos : lista) {
				motoPos.setAtivo("N");
				motoristaPosicaoAreaDAO.save(motoPos, em);
			}

			emUtil.commitTransaction(transaction);
			retorno.setCodChamadaVeiculo(chamadaVeiculo.getCodigo());
			retorno.setValorPorDistancia(valor);
			retorno.setDistanciaInicial(distancia);
			retorno.setValorFinal(valorInicial.toString());
			SimpleController.getListaChamadasEmEspera().remove(retorno);
			SimpleController.getListaChamadasAceitas().add(retorno);
			return retorno;
		} catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw e;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar aceitar chamada.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void cancelarChamadaMotorista(CancelarChamadaParam param) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();

			Chamada retorno;
			boolean jaExistia = false;
			if (SimpleController.getListaChamadasAceitas().stream()
					.anyMatch(ch -> ch.getCodigo().equals((param.getChamada().getCodigo())))) {
				retorno = SimpleController.getListaChamadasAceitas().stream()
						.filter(ch -> ch.getCodigo().equals(param.getChamada().getCodigo())).findFirst().get();
				if (!param.getCodChamadaVeiculo().equals(retorno.getCodChamadaVeiculo()))
					throw new Exception("Essa chamada já foi aceita por outro motorista");

				/*
				 * retorno = SimpleController.getListaChamadasAceitas().stream()
				 * .filter(ch -> ch.getCodigo() ==
				 * param.getChamada().getCodigo()).findFirst().get();
				 */

			} else if (SimpleController.getListaChamadasEmEspera().stream()
					.anyMatch(ch -> ch.getCodigo().equals(param.getChamada().getCodigo()))) {
				retorno = SimpleController.getListaChamadasEmEspera().stream()
						.filter(ch -> ch.getCodigo().equals(param.getChamada().getCodigo())).findFirst().get();
				jaExistia = true;
			} else
				throw new ExcecaoNegocio("Chamada já foi aceita ou cancelada");

			// retorno.setSituacaoChamada(new
			// SituacaoChamada(SituacaoChamadaEnum.PENDENTE_GERAL.getCodigo()));

			if (!retorno.getSituacaoChamada().getCodigo().equals(SituacaoChamadaEnum.PENDENTE_GERAL.getCodigo()))
				retorno.setSituacaoChamada(new SituacaoChamada(SituacaoChamadaEnum.PENDENTE.getCodigo()));

			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			chamadaDAO.save(retorno, em);

			IChamadaVeiculoDAO chamadaVeiculoDAO = fabricaDAO.getPostgresChamadaVeiculoDAO();
			ChamadaVeiculo chamadaVeiculo = chamadaVeiculoDAO.findById(param.getCodChamadaVeiculo(), em);

			if (chamadaVeiculo != null) {
				chamadaVeiculo.setFlgUltimoMovimento("N");
				chamadaVeiculoDAO.save(chamadaVeiculo, em);
			}
			ChamadaVeiculo chamadaVeiculo2 = new ChamadaVeiculo();
			chamadaVeiculo2.setDataDecisao(param.getDataCancelamento());
			chamadaVeiculo2.setFlgAceita("N");
			chamadaVeiculo2.setFlgUltimoMovimento("S");
			chamadaVeiculo2.setChamada(retorno);
			chamadaVeiculo2.setDataCriacao(new Date());
			chamadaVeiculo2.setVeiculo(new Veiculo(param.getCodVeiculo()));
			chamadaVeiculoDAO.save(chamadaVeiculo2, em);

			// recoloco motorista como disponível
			IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();
			Veiculo veiculo = veiculoDAO.obterVeiculoPorId(param.getCodVeiculo(), em);
			veiculo.getMotorista().setDisponivel("S");

			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			motoristaDAO.save(veiculo.getMotorista(), em);

			/*
			 * VerificaPosicaoParam verificaParam = new VerificaPosicaoParam();
			 * verificaParam.setCodMotorista(veiculo.getMotorista().getCodigo())
			 * ; verificaParam.setLatitude(param.getLatitudeAtual());
			 * verificaParam.setLongitude(param.getLongitudeAtual());
			 * 
			 * MotoristaPosicaoAreaBO.getInstance().obterPosicaoMotoristaArea(
			 * verificaParam, em);
			 */

			emUtil.commitTransaction(transaction);

			SimpleController.getListaChamadasAceitas().remove(retorno);
			// SimpleController.getListaChamadasEmEsperaGeral().add(retorno);
			if (!jaExistia)
				SimpleController.getListaChamadasEmEspera().add(retorno);

		} catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw e;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar cancelar chamada.", e);
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
