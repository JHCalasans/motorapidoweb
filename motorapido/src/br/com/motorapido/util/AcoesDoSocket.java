package br.com.motorapido.util;

import java.io.IOException;

import javax.websocket.Session;

import com.google.gson.Gson;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.ChamadaBO;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.bo.MotoristaPosicaoAreaBO;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.excecao.ExcecaoMotoristaPosicaoArea;
import br.com.motorapido.util.ws.params.CancelarChamadaParam;
import br.com.motorapido.util.ws.params.VerificaPosicaoParam;

public class AcoesDoSocket {

	public static void tratarInformacaoPendente(Session session, String servico, String json, String codInformacaoPendente) {
		try {
			switch (servico) {
			case "cancelarChamada":
				cancelarChamada(session, json,codInformacaoPendente);

				break;
			}
		} catch (Exception e) {
			ExcecoesUtil.logarErro(e);
		}
	}

	private static void cancelarChamada(Session session,String json, String codInformacaoPendente) {
		Gson gson = new Gson();
		CancelarChamadaParam param = gson.fromJson(json, CancelarChamadaParam.class);
		try {
			ChamadaBO.getInstance().cancelarChamadaMotorista(param);
			session.getBasicRemote().sendText("InformacaoPendenteResp=>"+codInformacaoPendente);
		} catch (Exception e) {
			ExcecoesUtil.logarErroMotorista(e, 	ControleSessaoWS.obterPorSessao(session.getId()).getCodMotorista(), "cancelarChamada");
		}	
		
	}
	
	
	public static void fechouApp(Session session) {
		try {
			session.close();
			SessaoWS ses = ControleSessaoWS.obterPorSessao(session.getId());
			ControleSessaoWS.remove(ses.getCodMotorista());
			System.out.println("removeu - " + ses.getCodMotorista());
		} catch (Exception e) {
			ExcecoesUtil.logarErroMotorista(e, 	ControleSessaoWS.obterPorSessao(session.getId()).getCodMotorista(), "fecharAppo");
		}
		
	}

	public static void informarLocalizacao(Session session, String json)
			throws ExcecaoNegocio, ExcecaoMotoristaPosicaoArea, IOException {
		Gson gson = new Gson();
		VerificaPosicaoParam param = gson.fromJson(json, VerificaPosicaoParam.class);
		MotoristaPosicaoArea motoPosicao = MotoristaPosicaoAreaBO.getInstance().obterPosicaoMotoristaArea(param);
		System.out.println("Posição motorista " + motoPosicao.getMotorista().getNome());
		/*if (motoPosicao != null) {
			session.getBasicRemote().sendText("LocalizacaoResp=>" + gson.toJson(motoPosicao));
		}*/
	}

	public static void logOut(Session session, String json) throws ExcecaoNegocio, IOException {
		Gson gson = new Gson();
		Motorista motorista = gson.fromJson(json, Motorista.class);
		MotoristaBO.getInstance().logoff(motorista);
		session.close();
		SessaoWS ses = ControleSessaoWS.obterPorSessao(session.getId());
		ControleSessaoWS.remove(ses.getCodMotorista());
	}

}
