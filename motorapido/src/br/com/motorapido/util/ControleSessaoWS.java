package br.com.motorapido.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControleSessaoWS {

	private static ControleSessaoWS instance;

	private static Map<Integer, SessaoWS> sMap = new HashMap<Integer, SessaoWS>();

	private ControleSessaoWS() {
	}

	public static void add(SessaoWS s) {
		sMap.put(s.getCodMotorista(), s);
	}

	public static void remove(Integer codMotorista) {
		sMap.remove(codMotorista);
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
		/*List<String> listaErro = new ArrayList<String>();
		for (Integer codMotorista : listaCodMotorista) {
			try {
				sMap.get(codMotorista).getSessao().getBasicRemote().sendText("Teste");
			} catch (IOException e) {
				listaErro.add("Erro em enviar msg para motorista - " + codMotorista + " => " + e.getMessage());
			}
		}*/

	}
}
