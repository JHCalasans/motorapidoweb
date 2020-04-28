package br.com.motorapido.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import com.google.gson.Gson;

import br.com.motorapido.entity.Motorista;
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
		//System.out.println("sessão encerrada - " + sessao.getSessao().getId());
	}

	public static void remove(Session session) {
		//obterPorSessao(session.getId());
		SessaoWS sess = obterPorSessao(session.getId());		
		sMap.remove(sess.getCodMotorista());
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
	

	public static void enviarSolicitacaoPosicao(List<Motorista> listaMotorista) {
		
		Gson gson = new Gson();
		 for (Motorista motorista : listaMotorista) { 
			 try {
				  sMap.get(motorista.getCodigo()).getSessao().getBasicRemote().sendText("InformarCoordenada=>"+ gson.toJson(motorista));	
			 }catch (Exception e) {
				 ExcecoesUtil.logarErroMotorista(e, motorista.getCodigo(), "InformarCoordenada");
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
	
	public static void enviarTesteMotoristaChamada(Integer codMotorista) throws IOException {

		if(sMap.containsKey(codMotorista))
			sMap.get(codMotorista).getSessao().getBasicRemote().sendText("TesteChamada=>");
			
		
		
	}
	
	public static boolean enviarMensagemChat(Integer codMotorista, String msg, String data) throws IOException {

		if(sMap.containsKey(codMotorista)){
			sMap.get(codMotorista).getSessao().getBasicRemote().sendText("NovaMensagemChat=>"+msg+"=>"+data);
			return true;
		}else
			return false;
			
		
		
	}
}
