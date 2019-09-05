package br.com.motorapido.util;

import java.io.IOException;

import javax.websocket.Session;

import com.google.gson.Gson;

import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoEntidadeReferenciada;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.ChamadaBO;
import br.com.motorapido.bo.MensagemMotoristaFuncionarioBO;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.bo.MotoristaPosicaoAreaBO;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.excecao.ExcecaoMotoristaPosicaoArea;
import br.com.motorapido.util.ws.params.CancelarChamadaParam;
import br.com.motorapido.util.ws.params.MensagemParam;
import br.com.motorapido.util.ws.params.VerificaPosicaoParam;
import br.com.motorapido.util.ws.retornos.RetornoVerificaPosicao;

public class AcoesDoSocket {

	public static void tratarInformacaoPendente(Session session, String servico, String json,
			String codInformacaoPendente) {
		try {
			switch (servico) {
			case "cancelarChamada":
				cancelarChamada(session, json, codInformacaoPendente);

				break;
			}
		} catch (Exception e) {
			ExcecoesUtil.logarErro(e);
		}
	}

	public static void novaMensagemChat(Session session, String msg) {
		SessaoWS ses = ControleSessaoWS.obterPorSessao(session.getId());
		MensagemParam param = new MensagemParam();
		param.setMensagem(msg);
		param.setCodMotorista(ses.getCodMotorista());
		try {
			MensagemMotoristaFuncionarioBO.getInstance().enviarMensagemDoMotorista(param);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.logarErro(e);
		}
	}

	private static void cancelarChamada(Session session, String json, String codInformacaoPendente) {
		Gson gson = new Gson();
		CancelarChamadaParam param = gson.fromJson(json, CancelarChamadaParam.class);
		try {
			ChamadaBO.getInstance().cancelarChamadaMotorista(param);
			session.getBasicRemote().sendText("InformacaoPendenteResp=>" + codInformacaoPendente);
		} catch (Exception e) {
			ExcecoesUtil.logarErroMotorista(e, ControleSessaoWS.obterPorSessao(session.getId()).getCodMotorista(),
					"cancelarChamada");
		}

	}

	public static void fechouApp(Session session) {
		try {
			session.close();
			SessaoWS ses = ControleSessaoWS.obterPorSessao(session.getId());
			ControleSessaoWS.remove(ses.getCodMotorista());
			System.out.println("removeu - " + ses.getCodMotorista());
		} catch (Exception e) {
			ExcecoesUtil.logarErroMotorista(e, ControleSessaoWS.obterPorSessao(session.getId()).getCodMotorista(),
					"fecharAppo");
		}

	}

	public static void informarLocalizacao(Session session, String json)
			throws ExcecaoNegocio, ExcecaoMotoristaPosicaoArea, IOException, ExcecaoBancoConexao,
			ExcecaoBancoEntidadeReferenciada, ExcecaoBanco {
		Gson gson = new Gson();
		VerificaPosicaoParam param = gson.fromJson(json, VerificaPosicaoParam.class);

		if (MotoristaBO.getInstance().estaDisponivel(param.getCodMotorista())) {
			MotoristaPosicaoArea motoPosicao = MotoristaPosicaoAreaBO.getInstance().obterPosicaoMotoristaArea(param);
			RetornoVerificaPosicao retorno = new RetornoVerificaPosicao();
			if (motoPosicao != null) {
				retorno.setAreaAtual(motoPosicao.getArea());
				retorno.setPosicaoNaArea(motoPosicao.getPosicao());
			}
			if (motoPosicao != null) {
				session.getBasicRemote().sendText("LocalizacaoResp=>" + gson.toJson(retorno));
			}
		} else { 
			session.getBasicRemote().sendText("IndisponivelResp=>");
		}
	}

	public static void listarSessoes() {
		ControleSessaoWS.listar();
	}

	public static void logOut(Session session, String json) throws ExcecaoNegocio, IOException {
		Gson gson = new Gson();
		Motorista motorista = gson.fromJson(json, Motorista.class);
		MotoristaBO.getInstance().logoff(motorista);
		session.close();
		SessaoWS ses = ControleSessaoWS.obterPorSessao(session.getId());
		ControleSessaoWS.remove(ses.getCodMotorista());
		System.out.println("sessão encerrada logout- " + session.getId());
	}

	public static void ficarIndisponivel(Session session) throws ExcecaoNegocio, IOException {

		SessaoWS sessaoWs = ControleSessaoWS.obterPorSessao(session.getId());

		MotoristaBO.getInstance().ficarIndisponivel(sessaoWs.getCodMotorista());
		// session.close();
		SessaoWS ses = ControleSessaoWS.obterPorSessao(session.getId());
		ControleSessaoWS.remove(ses.getCodMotorista());

	}

}
