package br.com.motorapido.mbean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.motorapido.util.ExcecoesUtil;

@SuppressWarnings("deprecation")
@ManagedBean(name = ErroBean.NOME_BEAN)
@ViewScoped
public class ErroBean extends SimpleController {

	private static final long serialVersionUID = -8257001720243437240L;
	public static final String NOME_BEAN = "erroBean";

	@PostConstruct
	public void carregar() {
		try {

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public String irParaLogin() {

		String url = "//index.proj??faces-redirect=true";
		return url;

	}

	@Override
	public String salvoSucesso() {
		return null;
	}

}
