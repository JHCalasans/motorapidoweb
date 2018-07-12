package br.com.motorapido.mbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.CaracteristicaBO;
import br.com.motorapido.bo.PerfilBO;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.util.ExcecoesUtil;

@SuppressWarnings("deprecation")
@ManagedBean(name = "caracteristicaBean")
@ViewScoped
public class CaracteristicaBean extends SimpleController {

	private static final long serialVersionUID = 473247838236460064L;

	private Caracteristica caracteristica;

	private String ativo;

	private List<Caracteristica> listaCaracteristicas;

	@PostConstruct
	public void carregar() {
		if (getFacesContext().isPostback()) {
			return;
		}
		try {

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void pesquisar() {
		try {
			listaCaracteristicas = CaracteristicaBO.getInstance().obterCaracteristicas(caracteristica.getDescricao(),
					ativo);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	

	public void salvar() {
		try {
			CaracteristicaBO.getInstance().salvarCaracteristica(caracteristica);
			addMsg(FacesMessage.SEVERITY_INFO, "Característica cadastrada com sucesso.");
			caracteristica = new Caracteristica();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	

	public void alterar() {
		try {
			CaracteristicaBO.getInstance().alterarCaracteristica(caracteristica);
			addMsg(FacesMessage.SEVERITY_INFO, "Característica alterada com sucesso.");
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public String navegarAlteracao(int codCaracteristica) {
		String url = "alterarCaracteristica.tjse?faces-redirect=true&codCaracteristica=" + codCaracteristica;
		return url;
	}
	
	public String navegarVinculos(int codCaracteristica) {
		String url = "alterarCaracteristica.tjse?faces-redirect=true&codCaracteristica=" + codCaracteristica;
		return url;
	}

	@Override
	public String salvoSucesso() {
		// TODO Auto-generated method stub
		return null;
	}

	public Caracteristica getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public List<Caracteristica> getListaCaracteristicas() {
		return listaCaracteristicas;
	}

	public void setListaCaracteristicas(List<Caracteristica> listaCaracteristicas) {
		this.listaCaracteristicas = listaCaracteristicas;
	}

}
