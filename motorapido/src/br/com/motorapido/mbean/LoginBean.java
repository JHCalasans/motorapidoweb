package br.com.motorapido.mbean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.minhaLib.mbean.AbstractUsuarioLogadoBean;
import br.com.motorapido.bo.FuncionarioBO;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.FacesUtil;
import br.com.motorapido.util.Paginas;

@ManagedBean(name = LoginBean.NOME_BEAN)
@ViewScoped
public class LoginBean extends SimpleController {

	private static final long serialVersionUID = 524489573749923264L;
	public static final String NOME_BEAN = "loginBean";

	private String login;
	private String senha;

	private boolean exibeBannerMenu = true;

	@PostConstruct
	public void carregar() {
		try {
			if (getSessionMap().containsKey("motoRapido.funcionario"))
				FacesUtil.redirecionar(null, Paginas.PAG_HOME, true, null);

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public boolean isExibeBannerMenu() {
		return exibeBannerMenu;
	}

	public void setExibeBannerMenu(boolean exibeBannerMenu) {
		this.exibeBannerMenu = exibeBannerMenu;
	}

	public LoginBean() {

	}

	public void redirecionarHome() {
		try {
			FacesUtil.redirecionar(null, Paginas.PAG_LOGIN, true, null);
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void logar() {
		try {
			Funcionario funcionario = FuncionarioBO.getInstance().obterFuncionarioPorLoginSenha(login, senha);
			if (funcionario != null) {
				if (funcionario.getPerfil().getAcessaSistema().equals("S")) {
					getSessionMap().put("motoRapido.funcionario", funcionario);
					FacesUtil.redirecionar(null, Paginas.PAG_CHAMADAS, true, null);
				}else
					addMsg(FacesMessage.SEVERITY_ERROR, "Usuário sem permissão para acessar.");
			} else
				addMsg(FacesMessage.SEVERITY_ERROR, "Login/Senha incorretos.");
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String salvoSucesso() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
