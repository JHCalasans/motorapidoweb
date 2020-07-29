package br.com.motorapido.mbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.minhaLib.mbean.AbstractUsuarioLogadoBean;
import br.com.motorapido.bo.ClienteBO;
import br.com.motorapido.bo.EnderecoClienteBO;
import br.com.motorapido.bo.FuncionarioBO;
import br.com.motorapido.entity.Cliente;
import br.com.motorapido.entity.EnderecoCliente;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.util.ControleSessaoWS;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.FacesUtil;
import br.com.motorapido.util.Paginas;

@SuppressWarnings("deprecation")
@ManagedBean(name = "loginClienteBean")
@ViewScoped
public class LoginClienteBean extends SimpleControllerCliente {


	private static final long serialVersionUID = -425929019704239736L;
	private String login;
	private String senha;

	private boolean exibeBannerMenu = true;

	@PostConstruct
	public void carregar() {
		try {
			if (getSessionMap().containsKey("motoRapido.cliente"))
				FacesUtil.redirecionar(null, Paginas.PAG_NOVA_ENTREGA, true, null);

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

	public LoginClienteBean() {

	}

	public void redirecionarHome() {
		try {
			FacesUtil.redirecionar(null, Paginas.PAG_NOVA_ENTREGA, true, null);
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void logar() {
		try {
			Cliente cliente = ClienteBO.getInstance().obterClientePorLoginSenha(login, senha);
			if (cliente != null) {
					getSessionMap().put("motoRapido.cliente", cliente);
					List<EnderecoCliente> lista = EnderecoClienteBO.getInstance().obterEnderecosPorCliente(cliente);
					if (lista != null && lista.size() > 0)
						getSessionMap().put("motoRapido.enderecoCliente", lista.get(0));
					FacesUtil.redirecionar(null, Paginas.PAG_NOVA_ENTREGA, true, null);
				
			} else
				addMsg(FacesMessage.SEVERITY_ERROR, "Login/Senha incorreto(s).");
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
