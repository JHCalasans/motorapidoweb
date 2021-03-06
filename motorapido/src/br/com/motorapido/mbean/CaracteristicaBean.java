package br.com.motorapido.mbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.CaracteristicaBO;
import br.com.motorapido.dao.ICaracteristicaDAO;
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
			String codCaracteristicaStr = (String) getRequestParam("codCaracteristica");
			
			String alterou = (String) getRequestParam("alterou");

			if (codCaracteristicaStr != null) {
				Integer codCaracteristica = Integer.valueOf(codCaracteristicaStr);
				carregarCaracteristica(codCaracteristica);
				
			} else {
				caracteristica = new Caracteristica();
				listaCaracteristicas = CaracteristicaBO.getInstance().obterCaracteristicas(caracteristica.getDescricao(),
						null);
				if(alterou != null && alterou.equals("true"))
					addMsg(FacesMessage.SEVERITY_INFO, "Característica alterada com sucesso.");
			}
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	
	private void carregarCaracteristica(Integer codCaracteristica) {
		try {
			ICaracteristicaDAO caracteristicaDAO = getFabrica().getPostgresCaracteristicaDAO();
			caracteristica = caracteristicaDAO.findById(codCaracteristica);
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
			getListaCaracteristicasMemoria().add(CaracteristicaBO.getInstance().salvarCaracteristica(caracteristica));
			addMsg(FacesMessage.SEVERITY_INFO, "Característica cadastrada com sucesso.");
			caracteristica = new Caracteristica();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	

	public String alterar() {
		try {
			getListaCaracteristicasMemoria().removeIf(ca -> ca.getCodigo() == caracteristica.getCodigo());
			//filter(ca -> ca.getCodigo() == caracteristica.getCodigo()).
			Caracteristica cara = CaracteristicaBO.getInstance().alterarCaracteristica(caracteristica);
			if(cara.getAtivo().equals("S"))
				getListaCaracteristicasMemoria().add(cara);
			
			
			ativo = "";
			pesquisar();
			caracteristica = new Caracteristica();
			return "consultarCaracteristica.tjse?faces-redirect=true&alterou=true";
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
		return "";
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
