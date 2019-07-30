package br.com.motorapido.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

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

	public static void enviarMensagemListaMotorista(List<Integer> listaCodMotorista) {
		/*
		 * List<String> listaErro = new ArrayList<String>(); for (Integer
		 * codMotorista : listaCodMotorista) { try {
		 * sMap.get(codMotorista).getSessao().getBasicRemote().sendText("Teste")
		 * ; } catch (IOException e) {
		 * listaErro.add("Erro em enviar msg para motorista - " + codMotorista +
		 * " => " + e.getMessage()); } }
		 */

	}

	public static boolean enviarMensagemMotoristaChamada(Integer codMotorista, String msg) throws IOException {

		if(sMap.containsKey(codMotorista)){
			sMap.get(codMotorista).getSessao().getBasicRemote().sendText("NovaChamada=>"+msg);
			return true;
		}else
			return false;
			
		
		
	}
}
