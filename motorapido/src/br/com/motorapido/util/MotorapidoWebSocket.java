package br.com.motorapido.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.excecao.ExcecaoMotoristaPosicaoArea;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@ServerEndpoint(value = "/socket", configurator = MotorapidoWebSocket.class)
public class MotorapidoWebSocket extends Configurator implements ServletRequestListener {

	private SessaoWS sessao;

	@OnOpen
	public void onOpen(Session session, EndpointConfig config)  {

		
		//Map<String, List<String>> params = session.getRequestParameterMap();

		Integer codMotorista = (Integer) config.getUserProperties().get("codMotorista");

		config.getUserProperties().remove("codMotorista");
		
		try {
			ControleSessaoWS.initialize();
			ControleSessaoWS.remove(codMotorista);
			sessao = new SessaoWS();
			sessao.setCodMotorista(codMotorista);
			sessao.setSessao(session);
			ControleSessaoWS.initialize();
			ControleSessaoWS.add(sessao);
			System.out.println("sessão iniciada - " + session.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("sessão encerrada - " + session.getId());
		ControleSessaoWS.remove(session);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		

		try {

			String[] msg = message.split("=>");
			switch (msg[0]) {
			case "LogOut":
				AcoesDoSocket.logOut(session, msg[1]);
				break;
			case "InformarLocalizacao":
				AcoesDoSocket.informarLocalizacao(session, msg[1]);
				break;
			case "InformacaoPendente":
				AcoesDoSocket.tratarInformacaoPendente(session, msg[1], msg[2], msg[3]);
				break;
			case "FehouApp":
				AcoesDoSocket.fechouApp(session);
				break;
			case "ListarSessoes":
				AcoesDoSocket.listarSessoes();
				break;

			}
			/*
			 * session.getBasicRemote().sendText("Hello Client " +
			 * session.getId() + "!"); ControleSessaoWS.listar();
			 */

		} catch (ExcecaoMotoristaPosicaoArea e) {
			enviarResposta(session, "ErroMotoPosResp=>" + e.getMessage());
		} catch (ExcecaoNegocio e) {
			enviarResposta(session, "ErroResp=>" + e.getMessage());
			ExcecoesUtil.logarErro(e);
		} catch (Exception e) {
			ExcecoesUtil.logarErro(e);
		}
	}

	
	public void enviarResposta(Session session, String resp) {
		try {
			session.getBasicRemote().sendText(resp);
		} catch (IOException e) {
			ExcecoesUtil.logarErro(e);
		}
	}

	

	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {

		HttpSession httpSession = (HttpSession) request.getHttpSession();
		Map<String, List<String>> headers = request.getHeaders();
		String auth;
		if (!headers.containsKey("Authentication") || !headers.containsKey("CodMotorista")) {
			throw new RuntimeException("Falha na Autenticação");

		} else {
			auth = headers.get("Authentication").get(0);
			try {
				Jws<Claims> parser = JWTUtil.decode(auth);
				//
				config.getUserProperties().put("codMotorista", Integer.parseInt(headers.get("CodMotorista").get(0)));
			} catch (Exception e) {
				throw new RuntimeException("Falha na Autenticação");
			}
		}

		super.modifyHandshake(config, request, response);

		if (httpSession == null) {
			// System.out.println("httpSession == null after modifyHandshake");
			httpSession = (HttpSession) request.getHttpSession();
		}

		if (httpSession == null) {
			// System.out.println("httpSession == null");
			return;
		}

		config.getUserProperties().put("httpSession", httpSession);

		httpSession = (HttpSession) request.getHttpSession();
		// System.out.println("modifyHandshake " + httpSession.getId());

	}
			
		

	@OnError
	public void onError(Throwable t) {
		 System.out.println("onError::" + t.getMessage());
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub

	}

}
