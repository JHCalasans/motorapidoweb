package br.com.motorapido.mbean;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.minhaLib.mbean.AbstractUsuarioLogadoBean;
import br.com.motorapido.util.ExcecoesUtil;


@ManagedBean(name = HomeBean.NOME_BEAN)
@ViewScoped
public class HomeBean extends SimpleController {

	private static final long serialVersionUID = -2819017417735069396L;

	public static final String NOME_BEAN = "homeBean";

	

	@PostConstruct
	public void carregar() {
		try {

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}



	@Override
	public String salvoSucesso() {
		return null;
	}





	
}
