package br.com.motorapido.mbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

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



@ManagedBean(name = "menuBean")
@SessionScoped
public class MenuBean extends SimpleController implements Serializable {


	private static final long serialVersionUID = -2168554630566444675L;
	private MenuModel model;

	public MenuBean() {

	}
	
	public List<PerfilMenu> getMenus(){
		try {
			return PerfilMenuBO.getInstance().obterMenusPorPerfil(getFuncionarioLogado().getPerfil().getCodigo());
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
		return null;
	}
	
	public MenuModel getModel() {
		try {
			if (model == null) {
				model = new DefaultMenuModel();
				//Funcionario funcionario =  getFuncionarioLogado();				
				List<PerfilMenu> perfisMenus = PerfilMenuBO.getInstance().obterMenusPorPerfil(getFuncionarioLogado().getPerfil().getCodigo());
				MenuItem menuItem = null;
				for(PerfilMenu perfilMenu: perfisMenus){
					menuItem =  new DefaultMenuItem(perfilMenu.getMenu().getNome(), null, perfilMenu.getMenu().getUrl());
					getModel().addElement(menuItem);
				}
				Submenu  subMenu = new DefaultSubMenu("Configurações");	
				menuItem =  new DefaultMenuItem("Senha", null, null);
				((DefaultMenuItem)menuItem).setOnclick("PF('dlgSenha').show();");
				((DefaultSubMenu)subMenu).setStyleClass("subMenu");
				((DefaultSubMenu)subMenu).addElement(menuItem);
				getModel().addElement(subMenu);
				
				
			}
		} catch (Exception ex) {
			ExcecoesUtil.TratarExcecao(ex);
			model = null;
		}
		return model;
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
