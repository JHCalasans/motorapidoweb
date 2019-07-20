package br.com.motorapido.mbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.MatchMode;
import org.primefaces.model.SortOrder;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.PageList;
import br.com.minhaLib.mbean.CustomLazyDataModel;
import br.com.minhaLib.util.excecao.ExcecoesUtil;
import br.com.motorapido.bo.ParametroBO;
import br.com.motorapido.dao.FabricaDAO;
import br.com.motorapido.entity.Parametro;
import br.com.motorapido.entity.ValorParametro;

@SuppressWarnings("deprecation")
@ManagedBean(name = "parametroBean")
@ViewScoped
public class ParametroBean extends SimpleController  {

	private static final long serialVersionUID = 8919611368357167047L;
	private Parametro parametro;
//	private LazyParametroDataModel listaParameroOperacao;
	private List<Parametro> listaParametro;
	private ValorParametro valorParametro;
	private List<ValorParametro> listaValorParametro;
	private String chaveSelecionada;
	private String descricaoSelecionada;
	private int itensPorPagina = 10;

	public ParametroBean() {
		if (getParametro() == null) {
			setListaParametro(new ArrayList<Parametro>());
			parametro = new Parametro();
			setValorParametro(new ValorParametro());
		}
	}

	@PostConstruct
	public void carregar() {
		if (javax.faces.context.FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		try {
			String codParametro =
					((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
							.getRequest()).getParameter("id");

			String incluso =
					((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
							.getRequest()).getParameter("pi");

			if (incluso != null) {
				if (incluso.equals("S"))
					addMsg(FacesMessage.SEVERITY_INFO, "inseridoSucesso");
			}

			if (codParametro != null) {

				setParametro(FabricaDAO.getFabricaDAO().getPostgresParametroDAO().findById(new Long(codParametro)));

				pesquisarValorParametro();
			}
		} catch (Exception e) {
			ExcecoesUtil.tratarExcecao(e);
		}
	}

	/**
	 * Executa a pesquisa de Parametros
	 * 
	 * @author 14032 - Fernando Rodrigues Teles
	 * @param event
	 */
	public void pesquisarParametro() {
		try {
			Parametro parametro = new Parametro();
			parametro.setDescricao(getDescricaoSelecionada());
			parametro.setChave(getChaveSelecionada());
			setListaParametro(FabricaDAO.getFabricaDAO().getPostgresParametroDAO().findByExample(parametro));
			
		} catch (Exception e) {
			ExcecoesUtil.tratarExcecao(e);
		}
	}

	public void pesquisarValorParametro() {
		
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setListaParametro(List<Parametro> listaParametro) {
		this.listaParametro = listaParametro;
	}

	public List<Parametro> getListaParametro() {
		return listaParametro;
	}

	public void salvar() {
		try {
			getParametro().setChave(getParametro().getChave().toUpperCase());
			setParametro(FabricaDAO.getFabricaDAO().getPostgresParametroDAO().saveAtomico(getParametro()));

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("editarParametro.tjse?pi=S&id=" + getParametro().getCodParametro());
		} catch (Exception e) {
			ExcecoesUtil.tratarExcecao(e);
		}
	}

	public void alterar() {
		try {
			getParametro().setChave(getParametro().getChave().toUpperCase());
			setParametro(FabricaDAO.getFabricaDAO().getPostgresParametroDAO().saveAtomico(getParametro()));
			addMsg(FacesMessage.SEVERITY_INFO, "alteradoSucesso");
		} catch (Exception e) {
			ExcecoesUtil.tratarExcecao(e);
		}
	}

	public void excluir(ActionEvent event) {
		try {
		//	FabricaDAO.getFabricaDAO().getPostgresParametroDAO().excluirParametro(getParametro());
			// getListaParametro().remove(getParametro());
			pesquisarParametro();
			addMsg(FacesMessage.SEVERITY_INFO, "excluidoSucesso");
		} catch (Exception e) {
			ExcecoesUtil.tratarExcecao(e);
		}
	}

	public void excluirValorParametro(ActionEvent event) {
		try {
		//	FabricaDAO.getFabricaDAO().getValorParametroDAO().deleteAtomico(
		//			((ValorParametro) event.getComponent().getAttributes().get("row")));
		} catch (Exception e) {
			ExcecoesUtil.tratarExcecao(e);
		}
	}

	public void adicionarValorParametro(ActionEvent event) {
		try {
			getValorParametro().setParametro(getParametro());
			ParametroBO.getInstance().saveValorParametro(getValorParametro());
			ValorParametro valor = new ValorParametro();
			valor.setParametro(getParametro());
		} catch (Exception e) {
			ExcecoesUtil.tratarExcecao(e);
		}
	}

	public List<ValorParametro> getListaValorParametro() {
		return listaValorParametro;
	}

	public void setListaValorParametro(List<ValorParametro> listaValorParametro) {
		this.listaValorParametro = listaValorParametro;
	}

	public void setValorParametro(ValorParametro valorParametro) {
		this.valorParametro = valorParametro;
	}

	public ValorParametro getValorParametro() {
		return valorParametro;
	}

	public String getChaveSelecionada() {
		return chaveSelecionada;
	}

	public void setChaveSelecionada(String chaveSelecionada) {
		this.chaveSelecionada = chaveSelecionada;
	}

	public String getDescricaoSelecionada() {
		return descricaoSelecionada;
	}

	public void setDescricaoSelecionada(String descricaoSelecionada) {
		this.descricaoSelecionada = descricaoSelecionada;
	}


	public int getItensPorPagina() {
		return itensPorPagina;
	}

	public void setItensPorPagina(int itensPorPagina) {
		this.itensPorPagina = itensPorPagina;
	}
	
	

	@Override
	public String salvoSucesso() {
		// TODO Auto-generated method stub
		return null;
	}

}
