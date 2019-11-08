package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IBinarioMotoristaDAO;
import br.com.motorapido.dao.IBloqueioMotoristaDAO;
import br.com.motorapido.dao.ICaracteristicaMotoristaDAO;
import br.com.motorapido.dao.IChamadaVeiculoDAO;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.dao.IMotoristaDAO;
import br.com.motorapido.dao.IMotoristaPosicaoAreaDAO;
import br.com.motorapido.dao.IVeiculoDAO;
import br.com.motorapido.entity.BinarioMotorista;
import br.com.motorapido.entity.BloqueioMotorista;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.entity.CaracteristicaMotorista;
import br.com.motorapido.entity.ChamadaVeiculo;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaAparelho;
import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.entity.TipoPunicao;
import br.com.motorapido.entity.Veiculo;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.util.ControleSessaoWS;
import br.com.motorapido.util.FuncoesUtil;
import br.com.motorapido.util.JWTUtil;
import br.com.motorapido.util.ws.retornos.RetornoHistoricoMotorista;
import br.com.motorapido.util.ws.retornos.RetornoVeiculosMotorista;

public class MotoristaBO extends MotoRapidoBO {

	private static MotoristaBO instance;

	private MotoristaBO() {

	}

	public static MotoristaBO getInstance() {
		if (instance == null)
			instance = new MotoristaBO();

		return instance;
	}

	public Motorista alterarPermissaoDestinoMotorista(Motorista motorista, Boolean habilitado) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			motorista.setVerDestino(habilitado ? "S" : "N");
			motorista = motoristaDAO.save(motorista, em);
			emUtil.commitTransaction(transaction);
			return motorista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar liberar acesso a destino.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	public boolean estaDisponivel(Integer codMotorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			Motorista motorista = motoristaDAO.findById(codMotorista, em);
			emUtil.commitTransaction(transaction);
			return motorista.getDisponivel().equals("S");
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar checar disponibilidade", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public List<Motorista> obterMotoristas(String nome, String cpf, String cnh, String email, String identidade)
			throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			List<Motorista> lista = motoristaDAO.obterMotoristas(nome, cpf, cnh, email, identidade, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter motoristas.", e);
		} finally {
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
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public List<Motorista> obterMotoristasDisponiveis() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			Motorista motorista = new Motorista();
			motorista.setDisponivel("S");
			List<Motorista> lista = motoristaDAO.findByExample(motorista, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter motoristas ativos.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public List<Motorista> obterMotoristasSemRestricoesCliente(Integer codCliente) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();

			List<Motorista> lista = motoristaDAO.obterMotoristasSemRestricoesCliente(codCliente, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter motoristas sem restrições.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void alterarDisponivel(Integer codMotorista, String situacao) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			Motorista motorista = motoristaDAO.findById(codMotorista, em);

			// Busca se o motorista estava ativo em alguma área para também
			// desativar
			/*
			 * MotoristaPosicaoArea motoristaPosicaoArea = new MotoristaPosicaoArea();
			 * motoristaPosicaoArea.setMotorista(motorista);
			 * motoristaPosicaoArea.setAtivo("S");
			 */
			IMotoristaPosicaoAreaDAO motoristaPosicaoAreaDAO = fabricaDAO.getPostgresMotoristaPosicaoAreaDAO();
			List<MotoristaPosicaoArea> listaMotoristas = motoristaPosicaoAreaDAO.obterMotoristaAtivoCodigo(codMotorista,
					em);

			for (MotoristaPosicaoArea motoPosicao : listaMotoristas) {
				if (motoPosicao.getAtivo().equals("S")) {
					motoPosicao.setAtivo("N");
					motoristaPosicaoAreaDAO.save(motoPosicao, em);
					MotoristaPosicaoAreaBO.getInstance().atualizarPosicoesArea(motoPosicao.getArea(), em);
				}

			}

			motorista.setDisponivel(situacao.equals("S") ? "N" : "S");
			motoristaDAO.save(motorista, em);

			// if(SimpleController.getListaPosicaoMotorista() != null) {
			// if(motorista.getDisponivel().equals("N")) {
			/*
			 * boolean existe = SimpleController.getListaPosicaoMotorista().stream().
			 * anyMatch(mt -> mt.getMotorista().getCodigo() == motorista.getCodigo());
			 * if(existe) { MotoristaPosicaoArea motoTemp =
			 * SimpleController.getListaPosicaoMotorista().stream(). filter(mt ->
			 * mt.getMotorista().getCodigo() == motorista.getCodigo()).findFirst().get();
			 * SimpleController.getListaPosicaoMotorista().remove(motoTemp); }
			 */
			EventBus eventBus = EventBusFactory.getDefault().eventBus();
			eventBus.publish("/notify", new FacesMessage(StringEscapeUtils.escapeHtml3("AlterarDisponivel"),
					codMotorista.toString() + ";" + motorista.getDisponivel()));
			// }
			// }

			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar disponibilidade.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public List<Motorista> obterMotoristasPorNome(String nome) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			List<Motorista> lista = motoristaDAO.obterMotoristasPorNome(nome, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter motoristas.", e);
		} finally {
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
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public List<Motorista> obterPosicaoMotoristasForaDeAreas() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			List<Motorista> lista = motoristaDAO.obterMotoristasForaDeAreas(em);
			emUtil.commitTransaction(transaction);
			ControleSessaoWS.enviarSolicitacaoPosicao(lista);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter motoristas fora de áreas.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void logoff(Motorista motorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			//IMotoristaAparelhoDAO motoristaAparelhoDAO = fabricaDAO.getPostgresMotoristaAparelhoDAO();
			//MotoristaAparelho motoristaAparelho = new MotoristaAparelho();
			// motoristaAparelho.setCodMotorista(motorista.getCodigo());
			Motorista motoTemp = new Motorista(motorista.getCodigo());
			/*
			 * motoristaAparelho.setMotorista(motoTemp);
			 * motoristaAparelho.setIdPush(motorista.getIdPush()); List<MotoristaAparelho>
			 * lista = motoristaAparelhoDAO.findByExample(motoristaAparelho, em);
			 * motoristaAparelho = lista.get(0); motoristaAparelho.setAtivo("S");
			 * motoristaAparelho.setDesativacao(new Date());
			 * motoristaAparelhoDAO.save(motoristaAparelho, em);
			 */

			motorista = motoristaDAO.findById(motorista.getCodigo(), em);

			motorista.setDisponivel("N");

			motoristaDAO.save(motorista, em);

			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar realizar logoff.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void ficarIndisponivel(Integer codMotorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			/*IMotoristaAparelhoDAO motoristaAparelhoDAO = fabricaDAO.getPostgresMotoristaAparelhoDAO();
			MotoristaAparelho motoristaAparelho = new MotoristaAparelho();
			Motorista motoTemp = new Motorista(codMotorista);
			motoristaAparelho.setMotorista(motoTemp);
			List<MotoristaAparelho> lista = motoristaAparelhoDAO.findByExample(motoristaAparelho, em);
			for (MotoristaAparelho moto : lista) {
				moto.setAtivo("N");
				moto.setDesativacao(new Date());
				motoristaAparelhoDAO.save(moto, em);
			}*/

			Motorista motorista = motoristaDAO.findById(codMotorista, em);

			motorista.setDisponivel("N");

			motoristaDAO.save(motorista, em);

			EventBus eventBus = EventBusFactory.getDefault().eventBus();
			eventBus.publish("/notify", new FacesMessage(StringEscapeUtils.escapeHtml3("AlterarDisponivel"),
					codMotorista.toString() + ";" + motorista.getDisponivel()));

			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar ficar indisponível.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public Motorista login(Motorista motorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			String idPush = motorista.getIdPush();
			String idAparelho = motorista.getIdAparelho();
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
			if (lista != null && lista.size() > 0) {
				motorista = lista.get(0);

				// Busca se aparelho já está cadastrado para alguém
				List<MotoristaAparelho> listaAparelho = motoristaAparelhoDAO
						.obterAparelhoPorIdAparelho(idAparelho, em);
				boolean achou = false;
				if (listaAparelho != null && listaAparelho.size() > 0) {
					for (MotoristaAparelho motoAp : listaAparelho) {
						// Se estivesse cadastrado para este motorista seto para
						// ativo
						if (motoAp.getMotorista() != null) {
							if (motoAp.getMotorista().getCodigo() == motorista.getCodigo()) {
								if (motoAp.getAtivo().equals("S")) {
									achou = true;
									break;
								}else {
									throw new ExcecaoNegocio(
											"Aparelho não vinculado ao motorista.");
								}
							}
						}
						// verifico se outros motoristas neste aparelho estão
						// ativo e desativo eles nesse aparelho
						/*
						 * else if (motoAp.getAtivo().equals("S")) { motoAp.setAtivo("N");
						 * motoristaAparelhoDAO.save(motoAp, em); }
						 */
					}
					// Se o motorista que logou ainda não estava cadastrado
					// nesse aparelho faço o cadastro
					if (!achou) {
						throw new ExcecaoNegocio(
								"Aparelho ainda não vinculado ao motorista. Favor comunicar a central.");
						/*
						 * Motorista motoTemp = new Motorista(motorista.getCodigo());
						 * motoristaAparelho.setMotorista(motoTemp); motoristaAparelho.setAtivo("S");
						 * motoristaAparelho.setEntrada(new Date());
						 * motoristaAparelhoDAO.save(motoristaAparelho, em);
						 */
					}
				} // Se o aparelho ainda não estiver cadastrado faço o primeiro
					// cadastro para este aparelho com este motorista
				/*
				 * else { Motorista motoTemp = new Motorista(motorista.getCodigo());
				 * motoristaAparelho.setMotorista(motoTemp); motoristaAparelho.setAtivo("S");
				 * motoristaAparelho.setEntrada(new Date());
				 * motoristaAparelhoDAO.save(motoristaAparelho, em); }
				 */
				// Desativo qualquer outro registro deste motorista
				/*
				 * listaAparelho =
				 * motoristaAparelhoDAO.obterOutrosAparelhosMotorista(motorista.getCodigo(),
				 * idPush, em); List<String> listaParaNotificacao = new ArrayList<String>(); for
				 * (MotoristaAparelho motoAp : listaAparelho) { // envio push para desativar a
				 * sessão no aparelho registrado // anteriormente para este motorista if
				 * (motoAp.getAtivo().equals("S")) { motoAp.setDesativacao(new Date());
				 * listaParaNotificacao.add(motoAp.getIdPush()); } motoAp.setAtivo("N");
				 * motoristaAparelhoDAO.save(motoAp, em); }
				 */
				/*
				 * if (listaParaNotificacao.size() > 0)
				 * PushNotificationUtil.enviarNotificacaoPlayerId(
				 * FuncoesUtil.getParam(ParametroEnum.CHAVE_REST_PUSH.getCodigo(), em),
				 * FuncoesUtil.getParam(ParametroEnum.CHAVE_APP_ID_ONE_SIGNAL.getCodigo(), em),
				 * listaParaNotificacao, "Login realizado em outro aparelho!", "logout",
				 * "logout");
				 */

				// Chave de segurança para uso dos ws
				String chave = FuncoesUtil.getParam(ParametroEnum.CHAVE_SEGURANCA.getCodigo(), em);
				motorista.setChaveServicos(JWTUtil.create(motorista.getLogin(), chave));

				// Chave para uso do google maps
				String chaveGoogle = FuncoesUtil.getParam(ParametroEnum.CHAVE_MAPS.getCodigo(), em);
				motorista.setChaveGoogle(chaveGoogle);

				// Busca por veículos cadastrados do motorista
				IVeiculoDAO veiculoDAO = fabricaDAO.getPostgresVeiculoDAO();
				List<Veiculo> listaVeiculos = veiculoDAO.obterVeiculosPorMotorista(motorista.getCodigo(), em);
				motorista.setVeiculos(new ArrayList<RetornoVeiculosMotorista>());
				for (Veiculo veiculo : listaVeiculos) {
					RetornoVeiculosMotorista retornoVeiculo = new RetornoVeiculosMotorista();
					retornoVeiculo.setModelo(veiculo.getModelo().getDescricao());
					retornoVeiculo.setPlaca(veiculo.getPlaca());
					retornoVeiculo.setTipoVeiculo(veiculo.getModelo().getTipoVeiculo().getDescricao());
					retornoVeiculo.setCodVeiculo(veiculo.getCodigo());
					motorista.getVeiculos().add(retornoVeiculo);
				}

				// Inicia sempre como indisponível
				motorista.setDisponivel("N");
				motoristaDAO.save(motorista, em);

				emUtil.commitTransaction(transaction);
			} else
				throw new ExcecaoNegocio("Senha/Login incorreto(s)");

			motorista.setCodBinarioFoto(null);
			motorista.setCodBinarioDocCriminal(null);
			motorista.setCodBinarioCompResidencia(null);
			return motorista;
		} catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio(e.getMessage());
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar efetuar login.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public Motorista salvarMotorista(Motorista motorista, List<Caracteristica> caracteristicas, byte[] foto,
			byte[] comprovanteResidencia, byte[] documentoCriminal) throws ExcecaoNegocio {
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
			motorista.setVerDestino("N");

			IBinarioMotoristaDAO binarioMotoristaDAO = fabricaDAO.getPostgresBinarioMotoristaDAO();
			BinarioMotorista binarioMotorista = new BinarioMotorista();
			binarioMotorista.setBinario(documentoCriminal);
			motorista.setCodBinarioDocCriminal(binarioMotoristaDAO.save(binarioMotorista, em).getCodigo());
			binarioMotorista.setBinario(comprovanteResidencia);
			motorista.setCodBinarioCompResidencia(binarioMotoristaDAO.save(binarioMotorista, em).getCodigo());

			if (foto != null) {
				binarioMotorista.setBinario(foto);
				motorista.setCodBinarioFoto(binarioMotoristaDAO.save(binarioMotorista, em).getCodigo());
			}

			motorista = motoristaDAO.save(motorista, em);

			// Salvando características do motorista
			ICaracteristicaMotoristaDAO caracteristicaMotoristaDAO = fabricaDAO.getPostgresCaracteristicaMotoristaDAO();
			CaracteristicaMotorista caracteristicaMotorista = new CaracteristicaMotorista();
			caracteristicaMotorista.setMotorista(motorista);
			caracteristicaMotorista.setDataCriacao(new Date());
			for (Caracteristica caracteristica : caracteristicas) {
				caracteristicaMotorista.setCaracteristica(caracteristica);
				caracteristicaMotoristaDAO.save(caracteristicaMotorista, em);
			}

			emUtil.commitTransaction(transaction);
			return motorista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public Motorista alterarSenha(Motorista motorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();

			Motorista motoTemp = motoristaDAO.findById(motorista.getCodigo(), em);

			motoTemp.setSenha(FuncoesUtil.criptografarSenha(motorista.getSenha()));

			motorista = motoristaDAO.save(motoTemp, em);

			emUtil.commitTransaction(transaction);
			return motorista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar senha.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public Motorista alterarMotorista(Motorista motorista, List<Caracteristica> caracteristicas, byte[] foto,
			byte[] documentoCriminal, byte[] compResidencia) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			ICaracteristicaMotoristaDAO caracteristicaMotoristaDAO = fabricaDAO.getPostgresCaracteristicaMotoristaDAO();
			IBinarioMotoristaDAO binarioMotoristaDAO = fabricaDAO.getPostgresBinarioMotoristaDAO();

			if (documentoCriminal != null) {

				BinarioMotorista binarioMotorista = binarioMotoristaDAO.findById(motorista.getCodBinarioDocCriminal(),
						em);
				binarioMotorista.setBinario(documentoCriminal);
				binarioMotoristaDAO.save(binarioMotorista, em);
			}

			if (compResidencia != null) {

				BinarioMotorista binarioMotorista = binarioMotoristaDAO
						.findById(motorista.getCodBinarioCompResidencia(), em);
				binarioMotorista.setBinario(compResidencia);
				binarioMotoristaDAO.save(binarioMotorista, em);
			}

			if (foto != null) {
				BinarioMotorista binarioMotorista = new BinarioMotorista();

				if (motorista.getCodBinarioFoto() != null)
					binarioMotorista = binarioMotoristaDAO.findById(motorista.getCodBinarioFoto(), em);

				binarioMotorista.setBinario(foto);
				motorista.setCodBinarioFoto((binarioMotoristaDAO.save(binarioMotorista, em).getCodigo()));
			}

			motorista = motoristaDAO.save(motorista, em);

			CaracteristicaMotorista caracteristicaMotorista = new CaracteristicaMotorista();
			caracteristicaMotorista.setMotorista(motorista);
			List<CaracteristicaMotorista> result = caracteristicaMotoristaDAO.findByExample(caracteristicaMotorista,
					em);
			if (result != null && result.size() > 0) {
				caracteristicaMotoristaDAO.deleteLista(result, em);
			}

			caracteristicaMotorista.setDataCriacao(new Date());
			for (Caracteristica caracteristica : caracteristicas) {
				caracteristicaMotorista.setCaracteristica(caracteristica);
				caracteristicaMotoristaDAO.save(caracteristicaMotorista, em);

			}

			emUtil.commitTransaction(transaction);
			return motorista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar alterar motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public BinarioMotorista obterBinarioPorCodigo(Long codBinario) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IBinarioMotoristaDAO binarioMotoristaDAO = fabricaDAO.getPostgresBinarioMotoristaDAO();
			BinarioMotorista binarioMotorista = binarioMotoristaDAO.findById(codBinario, em);
			emUtil.commitTransaction(transaction);
			return binarioMotorista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter informação do motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void bloquearMotorista(Motorista motorista, Funcionario funcionario, String motivo, Date dataInicio,
			Date dataFinal, TipoPunicao punicaoSelecionada) throws ExcecaoNegocio {
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
			bloqueio.setTipoPunicao(punicaoSelecionada);
			bloqueioMotoristaDAO.save(bloqueio, em);
			motorista.setBloqueado("S");
			motoristaDAO.save(motorista, em);
			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
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
			BloqueioMotorista bloqueio = bloqueioMotoristaDAO.obterUltimoPorMotorista(motorista.getCodigo(), em);
			bloqueio.setDataFim(dataFim);
			bloqueio.setAtivo("N");
			bloqueioMotoristaDAO.save(bloqueio, em);
			motorista.setBloqueado("N");
			motoristaDAO.save(motorista, em);
			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar desbloquear motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void desbloquearMotoristaRotina() throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaDAO motoristaDAO = fabricaDAO.getPostgresMotoristaDAO();
			IBloqueioMotoristaDAO bloqueioMotoristaDAO = fabricaDAO.getPostgresBloqueioMotoristaDAO();
			List<BloqueioMotorista> lista = bloqueioMotoristaDAO.obterBloqueiosrMotoristaRotina(new Date(), em);
			for (BloqueioMotorista bloqueio : lista) {
				bloqueio.setAtivo("N");
				bloqueioMotoristaDAO.save(bloqueio, em);
				bloqueio.getMotorista().setBloqueado("N");
				motoristaDAO.save(bloqueio.getMotorista(), em);
			}
			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar desbloquear motorista na rotina.", e);
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
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public BinarioMotorista obterBinarioMotoristaPorCodigo(Long codBinario) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IBinarioMotoristaDAO binarioMotoristaDAO = fabricaDAO.getPostgresBinarioMotoristaDAO();
			BinarioMotorista binarioMotorista = binarioMotoristaDAO.findById(codBinario, em);
			emUtil.commitTransaction(transaction);
			return binarioMotorista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter informação do motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public List<RetornoHistoricoMotorista> obterHistoricoMotorista(Integer codigo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IChamadaVeiculoDAO chamadaVeiculoDAO = fabricaDAO.getPostgresChamadaVeiculoDAO();
			List<ChamadaVeiculo> lista = chamadaVeiculoDAO.obterHistoricoMotorista(codigo, em);
			List<RetornoHistoricoMotorista> retorno = new ArrayList<RetornoHistoricoMotorista>();
			for (ChamadaVeiculo chamadaVei : lista) {
				RetornoHistoricoMotorista retHistorico = new RetornoHistoricoMotorista();
				retHistorico.setDataChamada(chamadaVei.getChamada().getDataInicioCorrida());
				retHistorico.setPlaca(chamadaVei.getVeiculo().getPlaca());
				retHistorico.setSituacao(chamadaVei.getChamada().getSituacaoChamada().getDescricao());
				retHistorico.setTipoVeiculo(chamadaVei.getVeiculo().getModelo().getTipoVeiculo().getDescricao());
				retHistorico.setDestino(chamadaVei.getChamada().getLogradouroDestino() + " - "
						+ chamadaVei.getChamada().getBairroDestino());
				retorno.add(retHistorico);
			}
			emUtil.commitTransaction(transaction);
			return retorno;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter histórico do motorista.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
