package br.com.motorapido.mbean;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.sun.faces.component.visit.FullVisitContext;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.minhaLib.util.FacesUtil;
import br.com.motorapido.bo.CaracteristicaBO;
import br.com.motorapido.bo.ChamadaBO;
import br.com.motorapido.bo.LogradouroBO;
import br.com.motorapido.dao.FabricaDAO;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.entity.Chamada;
import br.com.motorapido.entity.ChamadaVeiculo;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.entity.Logradouro;
import br.com.motorapido.entity.MensagemMotoristaFuncionario;
import br.com.motorapido.enums.SituacaoChamadaEnum;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.Paginas;

@SuppressWarnings("deprecation")
@ViewScoped
public abstract class SimpleController implements Serializable {

	private static final long serialVersionUID = -5300498022090265180L;

	private int ROWS_DATATABLE = 20;

	private  Funcionario funcionarioLogado;

	// código do ultimo motorista que enviou mensagem
	private static Integer ultimoMotMsg;

	// código da ultima mensagem enviada
	private static MensagemMotoristaFuncionario ultimaMsgEnviada;

	//lista de logradouros carregadas em memória
	private static List<Logradouro> listaLogradouro;
	
	//lista de características do motorista carregadas em memória
	private static List<Caracteristica> listaCaracteristicasMemoria;
	
	//lista de chamadas em espera
	private static List<Chamada> listaChamadasEmEspera = new ArrayList<Chamada>();
	
	//lista de chamadas em espera
	private static List<ChamadaVeiculo> listaChamadasAceitas = new ArrayList<ChamadaVeiculo>();

	public SimpleController() {
		super();
	}

	public boolean estaNaPaginaDeMensagem() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request.getRequestURL().toString().contains("enviar");

	}

	public static void iniciarListaLogradouros() {
		try {
			listaLogradouro = LogradouroBO.getInstance().obterLogradourosPorEstados("SE");
			/*
			 * for (Logradouro logradouro : listaLogradouro) {
			 * autoComplete.add(logradouro.getDescricao()); }
			 */
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}

	}
	
	public static void iniciarListaChamadasEmEsperaGeral() {
		try {
			
			/*if(listaChamadasEmEsperaGeral != null)
				listaChamadasEmEsperaGeral.clear();
			
			listaChamadasEmEsperaGeral = ChamadaBO.getInstance().obterChamadasFiltro(SituacaoChamadaEnum.PENDENTE_GERAL.getCodSituacao());*/
						
			if(listaChamadasEmEspera != null)
				listaChamadasEmEspera.clear();
			
			listaChamadasEmEspera = ChamadaBO.getInstance().obterChamadasFiltro(SituacaoChamadaEnum.PENDENTE.getCodSituacao());
			listaChamadasEmEspera.addAll( ChamadaBO.getInstance().obterChamadasFiltro(SituacaoChamadaEnum.PENDENTE_GERAL.getCodSituacao()));
			/*
			 * for (Logradouro logradouro : listaLogradouro) {
			 * autoComplete.add(logradouro.getDescricao()); }
			 */
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}

	}
	
	public static void carregarCaracteristicasAtivas() {
		try {
			listaCaracteristicasMemoria = CaracteristicaBO.getInstance().obterCaracteristicas(null, "S");
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public UIComponent findComponent(final String id) {

		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();
		final UIComponent[] found = new UIComponent[1];

		root.visitTree(new FullVisitContext(context), new VisitCallback() {
			@Override
			public VisitResult visit(VisitContext context, UIComponent component) {
				if (component.getId().equals(id)) {
					found[0] = component;
					return VisitResult.COMPLETE;
				}
				return VisitResult.ACCEPT;
			}
		});

		return found[0];

	}

	protected static void addMsg(final Severity tipo, final String info) {
		String msgTexto = getMessageFor(info);
		FacesMessage msg = new FacesMessage(tipo, msgTexto, msgTexto);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void addMsg(final String id_message, final Severity tipo, final String info) {
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
		else {

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

	public  void setFuncionarioLogado(Funcionario funcionarioLogado) {
		this.funcionarioLogado = funcionarioLogado;
	}

	public Integer getUltimoMotMsg() {
		return ultimoMotMsg;
	}

	public static void setUltimoMotMsg(Integer ultimoMotMsg) {
		SimpleController.ultimoMotMsg = ultimoMotMsg;
	}

	public static MensagemMotoristaFuncionario getUltimaMsgEnviada() {
		return ultimaMsgEnviada;
	}

	public static void setUltimaMsgEnviada(MensagemMotoristaFuncionario ultimaMsgEnviada) {
		SimpleController.ultimaMsgEnviada = ultimaMsgEnviada;
	}

	protected static List<Logradouro> getListaLogradouro() {
		return listaLogradouro;
	}

	protected static void setListaLogradouro(List<Logradouro> listaLogradouro) {
		SimpleController.listaLogradouro = listaLogradouro;
	}

	protected static List<Caracteristica> getListaCaracteristicasMemoria() {
		return listaCaracteristicasMemoria;
	}

	protected static void setListaCaracteristicasMemoria(List<Caracteristica> listaCaracteristicas) {
		SimpleController.listaCaracteristicasMemoria = listaCaracteristicas;
	}

	public static List<Chamada> getListaChamadasEmEspera() {
		return listaChamadasEmEspera;
	}

	public static void setListaChamadasEmEspera(List<Chamada> listaChamadasEmEspera) {
		SimpleController.listaChamadasEmEspera = listaChamadasEmEspera;
	}

	public static List<Chamada> getListaChamadasEmEsperaGeral() {
		return listaChamadasEmEspera.stream().filter(ch -> ch.getSituacaoChamada().getCodigo().equals(SituacaoChamadaEnum.PENDENTE_GERAL.getCodigo())).collect(Collectors.toList());
		//return listaChamadasEmEsperaGeral;
	}

	public static List<ChamadaVeiculo> getListaChamadasAceitas() {
		return listaChamadasAceitas;
	}

	public static void setListaChamadasAceitas(List<ChamadaVeiculo> listaChamadasAceitas) {
		SimpleController.listaChamadasAceitas = listaChamadasAceitas;
	}

	
}
