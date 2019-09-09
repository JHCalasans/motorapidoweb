package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IMensagemFuncionarioDAO;
import br.com.motorapido.dao.IMensagemFuncionarioMotoristaDAO;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.entity.MensagemFuncionario;
import br.com.motorapido.entity.MensagemFuncionarioMotorista;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaAparelho;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.util.FuncoesUtil;
import br.com.motorapido.util.PushNotificationUtil;

public class MensagemFuncionarioBO extends MotoRapidoBO {
	
	private static MensagemFuncionarioBO instance;

	private MensagemFuncionarioBO() {

	}

	public static MensagemFuncionarioBO getInstance() {
		if (instance == null)
			instance = new MensagemFuncionarioBO();

		return instance;
	}
	
	public MensagemFuncionario salvarMensagem(MensagemFuncionario mensagem, List<Motorista> listaMotoristas) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMensagemFuncionarioDAO mensagemFuncionarioDAO = fabricaDAO.getPostgresMensagemFuncionarioDAO();
			IMensagemFuncionarioMotoristaDAO mensagemFuncionarioMotoristaDAO = fabricaDAO.getPostgresMensagemFuncionarioMotoristaDAO();
			IMotoristaAparelhoDAO motoristaAparelhoDAO = fabricaDAO.getPostgresMotoristaAparelhoDAO();
			
			mensagem.setDataCriacao(new Date());
			mensagem = mensagemFuncionarioDAO.save(mensagem, em);
			MensagemFuncionarioMotorista mensagemFuncionarioMotorista = null;
			MotoristaAparelho motoristaAparelho = new MotoristaAparelho();
			List<String> listaParaNotificacao = new ArrayList<String>();
			for(Motorista motorista : listaMotoristas){
				mensagemFuncionarioMotorista = new MensagemFuncionarioMotorista();
				mensagemFuncionarioMotorista.setMensagemFuncionario(mensagem);
				mensagemFuncionarioMotorista.setMotorista(motorista);
				mensagemFuncionarioMotorista.setVisualizada("N");
				mensagemFuncionarioMotoristaDAO.save(mensagemFuncionarioMotorista, em);
				motoristaAparelho.setAtivo("S");
				//motoristaAparelho.setCodMotorista(motorista.getCodigo());
				motoristaAparelho.setMotorista(motorista);
				List<MotoristaAparelho> lista = motoristaAparelhoDAO.findByExample(motoristaAparelho, em);
				if(lista != null && lista.size() > 0)
					listaParaNotificacao.add(lista.get(0).getIdPush());
				
			}			
			
			
			/*PushNotificationUtil.enviarNotificacaoPlayerId(FuncoesUtil.getParam(ParametroEnum.CHAVE_REST_PUSH.getCodigo(), em), 
					FuncoesUtil.getParam(ParametroEnum.CHAVE_APP_ID_ONE_SIGNAL.getCodigo(), em), listaParaNotificacao, mensagem.getDescricao());
		*/
			emUtil.commitTransaction(transaction);
			return mensagem;
		}catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar enviar mensagem.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	

	
}
