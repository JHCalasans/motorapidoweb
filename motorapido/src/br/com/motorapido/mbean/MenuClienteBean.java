package br.com.motorapido.mbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.socket.Socket;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.PerfilMenuBO;
import br.com.motorapido.entity.PerfilMenu;
import br.com.motorapido.util.ExcecoesUtil;

@SuppressWarnings("deprecation")
@ManagedBean(name = "menuClienteBean")
@ViewScoped
public class MenuClienteBean extends SimpleControllerCliente implements Serializable {

	
	private static final long serialVersionUID = -6284586964768023940L;
	private MenuModel model;
	

	public MenuClienteBean() {

	}



	public void setModel(MenuModel model) {
		this.model = model;
	}

	@PostConstruct
	public void init() {

	}

	@Override
	public String salvoSucesso() {
		// TODO Auto-generated method stub
		return null;
	}


}
