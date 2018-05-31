package br.com.motorapido.mbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.entity.MensagemFuncionario;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.util.ExcecoesUtil;

@SuppressWarnings("deprecation")
@ManagedBean(name = "mensagemBean")
@ViewScoped
public class MensagemBean  extends SimpleController {


	private static final long serialVersionUID = 4346645475549207799L;
	
	private List<Motorista> motoristas;
	private List<Motorista> motoristasSelecionados;
	private MensagemFuncionario mensagem;
	
	@PostConstruct
	public void carregar() {
		if (getFacesContext().isPostback()) {
			return;
		}		
		try {
			
			mensagem = new MensagemFuncionario();
			
			Motorista motorista = new Motorista();
			motorista.setAtivo("S");
			motoristas = MotoristaBO.getInstance().obterMotoristasExample(motorista);

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	
	public void enviarMensagem(){
		 mensagem.setFuncionario(getFuncionarioLogado());
		 
		
	}

	@Override
	public String salvoSucesso() {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Motorista> getMotoristas() {
		return motoristas;
	}


	public void setMotoristas(List<Motorista> motoristas) {
		this.motoristas = motoristas;
	}


	public List<Motorista> getMotoristasSelecionados() {
		return motoristasSelecionados;
	}


	public void setMotoristasSelecionados(List<Motorista> motoristasSelecionados) {
		this.motoristasSelecionados = motoristasSelecionados;
	}


	public MensagemFuncionario getMensagem() {
		return mensagem;
	}


	public void setMensagem(MensagemFuncionario mensagem) {
		this.mensagem = mensagem;
	}

}
