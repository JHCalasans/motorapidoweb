package br.com.motorapido.mbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "posicaoAreaBean")
@ViewScoped
public class PosicaoAreaBean  extends SimpleController{


	private static final long serialVersionUID = 5145685886176893792L;

	@Override
	public String salvoSucesso() {
		
		return null;
	}

}
