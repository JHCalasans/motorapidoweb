package br.com.motorapido.util;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint("/selecionarMotorista")
public class MotoristaResource {

	@OnMessage(encoders = (JSONEncoder.class))
	public String onMessage (String nome){
		return nome;
	}
}
