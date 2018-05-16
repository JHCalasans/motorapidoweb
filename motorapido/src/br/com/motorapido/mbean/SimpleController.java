package br.com.motorapido.mbean;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.minhaLib.util.FacesUtil;
import br.com.motorapido.dao.FabricaDAO;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.Paginas;

@SuppressWarnings("deprecation")
@ViewScoped
public abstract class SimpleController implements Serializable {

	private static final long serialVersionUID = -5300498022090265180L;

	private int ROWS_DATATABLE = 20;

	private static Funcionario funcionarioLogado;


	public SimpleController() {
		super();
	}

	protected static void addMsg(final Severity tipo, final String info) {
		String msgTexto = getMessageFor(info);
		FacesMessage msg = new FacesMessage(tipo, msgTexto, msgTexto);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	protected static void addMsg(final String id_message, final Severity tipo, final String info) {
		String msgTexto = getMessageFor(info);
		FacesMessage msg = new FacesMessage(tipo, msgTexto, msgTexto);
		FacesContext.getCurrentInstance().addMessage(id_message, msg);
	}

	protected void addMsg(Severity tipo, String info, Object... params) {
		String msgTexto = getMessageFor(info);
		msgTexto = MessageFormat.format(msgTexto, params);
		FacesMessage msg = new FacesMessage(tipo, msgTexto, msgTexto);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static String getMessageFor(String entry) {
		try {
			String value = getResourceBundle(entry);
			return value;
		} catch (MissingResourceException e) {
			return entry;
		}
	}

	protected static String getResourceBundle(String entry) {
		ResourceBundle bundle = getFacesContext().getApplication().getResourceBundle(getFacesContext(), "bundle");
		return bundle.getString(entry);
	}

	protected Object getRequestAttribute(String name) {
		return getRequest().getAttribute(name);
	}

	protected String getRequestParam(String name) {
		return getRequest().getParameter(name);
	}

	protected static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	protected ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

	protected FabricaDAO getFabrica() {
		return FabricaDAO.getFabricaDAO();
	}

	public static void enviarUpdate(String idComponente) {
		getPrimefacesRequestContext().update(idComponente);
	}

	public static void enviarUpdate(Collection<String> idComponentes) {
		getPrimefacesRequestContext().update(idComponentes);
	}

	public static void enviarJavascript(String script) {
		getPrimefacesRequestContext().execute(script);
	}

	private static RequestContext getPrimefacesRequestContext() {
		return org.primefaces.context.RequestContext.getCurrentInstance();
	}

	public UIComponent findComponent(UIComponent c, String id) {
		if (id.equals(c.getId())) {
			return c;
		}
		Iterator<UIComponent> kids = c.getFacetsAndChildren();
		while (kids.hasNext()) {
			UIComponent found = findComponent(kids.next(), id);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	protected HttpSession getSession() {
		return (HttpSession) getFacesContext().getExternalContext().getSession(false);
	}

	protected Map<String, Object> getSessionMap() {
		return getFacesContext().getExternalContext().getSessionMap();
	}

	protected Object getSessionAttribute(String name) {
		return getSession().getAttribute(name);
	}

	protected String getOriginPage() {
		return getFacesContext().getViewRoot().getViewId();
	}

	public int getRowsDataTable() {
		return ROWS_DATATABLE;
	}

	public void verificaSessaoValida(ComponentSystemEvent event) {
		if (!getSessionMap().containsKey("motoRapido.funcionario"))
			FacesUtil.redirecionar(null, Paginas.PAG_SESSAO_ENCERRADA, true, null);
		else{
			
			setFuncionarioLogado((Funcionario) getSessionMap().get("motoRapido.funcionario"));
		}

	}
	


	public String logout() {
		try {
			if (getSessionMap().containsKey("motoRapido.funcionario"))
				getSessionMap().clear();

			return "/paginas/login.xhtml?faces-redirect=true";

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
		return "";
	}

	
	public abstract String salvoSucesso();

	public Funcionario getFuncionarioLogado() {
		if (funcionarioLogado == null) {
			if (!getSessionMap().containsKey("motoRapido.funcionario"))
				FacesUtil.redirecionar(null, Paginas.PAG_SESSAO_ENCERRADA, true, null);
			else
				setFuncionarioLogado((Funcionario) getSessionMap().get("motoRapido.funcionario"));
		}
		return funcionarioLogado;
	}

	public static void setFuncionarioLogado(Funcionario funcionarioLogado) {
		SimpleController.funcionarioLogado = funcionarioLogado;
	}
	



	
}
