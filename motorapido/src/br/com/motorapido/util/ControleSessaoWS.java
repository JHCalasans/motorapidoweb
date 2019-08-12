package br.com.motorapido.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import com.google.gson.Gson;

import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.util.ws.retornos.RetornoVerificaPosicao;

public class ControleSessaoWS {

	private static ControleSessaoWS instance;

	private static Map<Integer, SessaoWS> sMap = new HashMap<Integer, SessaoWS>();

	private ControleSessaoWS() {
	}

	public static void add(SessaoWS s) {
		sMap.put(s.getCodMotorista(), s);
	}

	public static void remove(Integer codMotorista) throws IOException {
		SessaoWS sessao =  obterPorChave(codMotorista);
		if(sessao != null)
			sessao.getSessao().close();
		else
			return;
		
		sMap.remove(codMotorista);
		System.out.println("sessão encerrada - " + sessao.getSessao().getId());
	}

	public static void remove(Session session) {
		//obterPorSessao(session.getId());
		
		sMap.remove(obterPorSessao(session.getId()).getCodMotorista());
	}

	public static void initialize() {
		if (instance == null) {
			instance = new ControleSessaoWS();
		}
	}

	public static SessaoWS obterPorChave(Integer codMotorista) {
		return sMap.get(codMotorista);
	}

	public static SessaoWS obterPorSessao(String idSessao) {
		return sMap.entrySet().stream().filter(se -> se.getValue().getSessao().getId().equals(idSessao)).findFirst()
				.get().getValue();
	}

	public static void listar() {
		System.out.println(sMap.keySet().toString());
	}

	public static void enviarMensagemListaMotorista(List<RetornoVerificaPosicao> listaMotorista) {
		
		Gson gson = new Gson();
		 for (RetornoVerificaPosicao motorista : listaMotorista) { 
			 try {
				  sMap.get(motorista.getCodMotorista()).getSessao().getBasicRemote().sendText("AtualizarPosicao=>"+ gson.toJson(motorista));	
			 }catch (Exception e) {
				 ExcecoesUtil.logarErroMotorista(e, motorista.getCodMotorista(), "AtualizarPosicao");
			}
		 }
		
		
		 

	}

	public static boolean enviarMensagemMotoristaChamada(Integer codMotorista, String msg) throws IOException {

		if(sMap.containsKey(codMotorista)){
			sMap.get(codMotorista).getSessao().getBasicRemote().sendText("NovaChamada=>"+msg);
			return true;
		}else
			return false;
			
		
		
	}
}
