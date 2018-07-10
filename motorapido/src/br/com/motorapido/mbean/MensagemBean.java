package br.com.motorapido.mbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.tomcat.util.net.SocketEvent;
import org.primefaces.component.growl.Growl;
import org.primefaces.component.socket.Socket;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.MensagemFuncionarioBO;
import br.com.motorapido.bo.MensagemMotoristaFuncionarioBO;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.entity.MensagemFuncionario;
import br.com.motorapido.entity.MensagemMotoristaFuncionario;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.util.ExcecoesUtil;

@SuppressWarnings("deprecation")
@ManagedBean(name = "mensagemBean")
@ViewScoped
public class MensagemBean  extends SimpleController {


	private static final long serialVersionUID = 4346645475549207799L;
	
	private List<Motorista> motoristas;
	private List<Motorista> motoristasSelecionados;
	private Motorista motoristaSelecionado;
	private MensagemFuncionario mensagem;
	private List<MensagemMotoristaFuncionario> historico;
	private Socket socket;
	private String codMotoMsg;
	
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
	
	public void validarMensagem(){
			System.out.println(getUltimoMotMsg());
	}
	
	public void ajustarMotoristaSelecionado(Motorista moto){
		motoristaSelecionado = moto;
		try {
			historico = MensagemMotoristaFuncionarioBO.getInstance().obterMensagens(motoristaSelecionado.getCodigo(),
					getFuncionarioLogado().getCodigo());
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	
	public void atualizarMensagens(){
		
		System.out.println("opaaa");
	}
	
	
	public void enviarMensagem(){
		 mensagem.setFuncionario(getFuncionarioLogado());
		 try {
			MensagemFuncionarioBO.getInstance().salvarMensagem(getMensagem(), motoristasSelecionados);
			addMsg(FacesMessage.SEVERITY_INFO, "Mensagem enviada com sucesso");
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
		
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


	public Motorista getMotoristaSelecionado() {
		return motoristaSelecionado;
	}


	public void setMotoristaSelecionado(Motorista motoristaSelecionado) {
		this.motoristaSelecionado = motoristaSelecionado;
	}

	public List<MensagemMotoristaFuncionario> getHistorico() {
		return historico;
	}

	public void setHistorico(List<MensagemMotoristaFuncionario> historico) {
		this.historico = historico;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getCodMotoMsg() {
		return codMotoMsg;
	}

	public void setCodMotoMsg(String codMotoMsg) {
		this.codMotoMsg = codMotoMsg;
	}

}
