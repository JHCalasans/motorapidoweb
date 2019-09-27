package br.com.motorapido.bo;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IMensagemMotoristaFuncionarioDAO;
import br.com.motorapido.dao.IMotoristaDAO;
import br.com.motorapido.entity.MensagemMotoristaFuncionario;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.mbean.SimpleController;
import br.com.motorapido.util.ObjetoMensagem;
import br.com.motorapido.util.ws.params.MensagemParam;
import jersey.repackaged.com.google.common.collect.Lists;

public class MensagemMotoristaFuncionarioBO extends MotoRapidoBO {

	private static MensagemMotoristaFuncionarioBO instance;

	private MensagemMotoristaFuncionarioBO() {

	}

	public static MensagemMotoristaFuncionarioBO getInstance() {
		if (instance == null)
			instance = new MensagemMotoristaFuncionarioBO();

		return instance;
	}
	

	private String summary = "Nova Mensagem de ";
	private String CHANELL = "/notify";
	
	public MensagemMotoristaFuncionario enviarMensagemDoMotorista(MensagemParam param)
			throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMensagemMotoristaFuncionarioDAO mensagemMotoristaFuncionarioDAO = fabricaDAO
					.getPostgresMensagemMotoristaFuncionarioDAO();
			
			IMotoristaDAO motoristaDAO = fabricaDAO
					.getPostgresMotoristaDAO();

			Motorista moto = motoristaDAO.findById(param.getCodMotorista(), em);
			

			MensagemMotoristaFuncionario mensag = new MensagemMotoristaFuncionario();
			mensag.setMotorista(moto);
			mensag.setDescricao(param.getMensagem());
			mensag.setEnviadaPorMotorista("S");
			mensag.setDataCriacao(new Date());
			mensag = mensagemMotoristaFuncionarioDAO.save(mensag, em);
			
			ObjetoMensagem objetoMsg = new ObjetoMensagem();
			objetoMsg.setCodMotorista(moto.getCodigo());
			objetoMsg.setMessagem(param.getMensagem());
			objetoMsg.setNomeMotorista(moto.getNome());
			
			
			EventBus eventBus = EventBusFactory.getDefault().eventBus();
			try {
				Thread.sleep(500);
				eventBus.publish("/notify", new FacesMessage(StringEscapeUtils.escapeHtml3("NovaMensagem"),
						moto.getNome() + ";" + moto.getCodigo() + ";" + param.getMensagem() + ";" + new SimpleDateFormat("dd/MM/yyyy hh:mm").format(mensag.getDataCriacao())));

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * EventBus eventBus = EventBusFactory.getDefault().eventBus();
			 * eventBus.publish(CHANELL, new
			 * FacesMessage(StringEscapeUtils.escapeHtml3(summary),
			 * StringEscapeUtils.escapeHtml3(moto.getNome())));
			 */
			SimpleController.setUltimoMotMsg(param.getCodMotorista());
			SimpleController.setUltimaMsgEnviada(mensag);
			
			emUtil.commitTransaction(transaction);
			return mensag;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar enviar mensagen.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public List<MensagemMotoristaFuncionario> obterMensagens(Integer codMotorista)
			throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMensagemMotoristaFuncionarioDAO mensagemMotoristaFuncionarioDAO = fabricaDAO
					.getPostgresMensagemMotoristaFuncionarioDAO();

			Motorista moto = new Motorista();
			moto.setCodigo(codMotorista);

			MensagemMotoristaFuncionario mensag = new MensagemMotoristaFuncionario();
			mensag.setMotorista(moto);

			List<MensagemMotoristaFuncionario> lista = mensagemMotoristaFuncionarioDAO.findByExample(mensag, em);

			Collections.sort(lista, new Comparator<MensagemMotoristaFuncionario>(){

				@Override
				public int compare(MensagemMotoristaFuncionario o1, MensagemMotoristaFuncionario o2) {
					return o1.getDataCriacao().compareTo(o2.getDataCriacao());
				}
				
			});
			
			
			if (lista.size() > 30){
				lista = lista.subList(lista.size() - 30, lista.size());
			}
			
			lista = Lists.reverse(lista);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter mensagens.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	
	public MensagemMotoristaFuncionario enviarMensagemDaCentral(MensagemMotoristaFuncionario mensagem) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMensagemMotoristaFuncionarioDAO mensagemMotoristaFuncionarioDAO = fabricaDAO
					.getPostgresMensagemMotoristaFuncionarioDAO();

			MensagemMotoristaFuncionario mensag = new MensagemMotoristaFuncionario();
			mensag.setMotorista(mensagem.getMotorista());
			mensag.setDescricao(mensagem.getDescricao());
			mensag.setEnviadaPorMotorista("N");
			mensag.setDataCriacao(new Date());
			mensag.setFuncionario(mensagem.getFuncionario());
			mensag = mensagemMotoristaFuncionarioDAO.save(mensag, em);
			//SimpleController.setUltimaMsgEnviada(mensag);
			
		/*	PushNotificationUtil.enviarNotificacaoPlayerId(FuncoesUtil.getParam(ParametroEnum.CHAVE_REST_PUSH.getCodigo(), em), 
					FuncoesUtil.getParam(ParametroEnum.CHAVE_APP_ID_ONE_SIGNAL.getCodigo(), em), listaParaNotificacao, mensagem.getDescricao());
		
		*/	emUtil.commitTransaction(transaction);
			return mensag;
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar enviar mensagem.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
}
