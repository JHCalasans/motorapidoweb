package br.com.motorapido.mbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;


import br.com.minhaLib.util.excecao.MsgUtil;
import br.com.motorapido.bo.FuncionarioBO;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.FuncoesUtil;

@ManagedBean(name = "senhaBean")
@ViewScoped
public class SenhaBean extends SimpleController {

	private static final long serialVersionUID = 231046857935857262L;

	private String senhaAtual;

	private String senhaNova;

	private String senhaNovaConfirmacao;
	
	
	
	
	public void RedefinirSenha() {
		try {
			if (FuncoesUtil.criptografarSenha(senhaAtual).equals(getFuncionarioLogado().getSenha())) {
				if (senhaNova.equals(senhaNovaConfirmacao)) {
					getFuncionarioLogado().setSenha(FuncoesUtil.criptografarSenha(senhaNova));
					FuncionarioBO.getInstance().alterarSenha(getFuncionarioLogado());
					MsgUtil.updateMessage(FacesMessage.SEVERITY_INFO, "Senha alterada com sucesso.!");
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('dlgSenha').hide();");
				} else
					MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Nova senha não confere com a confirmação!");
			} else
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Senha atual incorreta!");

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
		
	}

	@Override
	public String salvoSucesso() {
		return null;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getSenhaNovaConfirmacao() {
		return senhaNovaConfirmacao;
	}

	public void setSenhaNovaConfirmacao(String senhaNovaConfirmacao) {
		this.senhaNovaConfirmacao = senhaNovaConfirmacao;
	}

}
