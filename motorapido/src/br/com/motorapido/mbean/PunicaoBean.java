package br.com.motorapido.mbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.TipoPunicaoBO;
import br.com.motorapido.dao.ITipoPunicaoDAO;
import br.com.motorapido.entity.TipoPunicao;
import br.com.motorapido.util.ExcecoesUtil;

@SuppressWarnings("deprecation")
@ManagedBean(name = "punicaoBean")
@ViewScoped
public class PunicaoBean extends SimpleController {

	private static final long serialVersionUID = 473247838236460064L;

	private TipoPunicao punicao;

	private String ativo;

	private List<TipoPunicao> listaPunicoes;

	@PostConstruct
	public void carregar() {
		if (getFacesContext().isPostback()) {
			return;
		}
		try {
			String codTipoPunicaoStr = (String) getRequestParam("codTipoPunicao");			
			

			if (codTipoPunicaoStr != null) {
				Integer codTipoPunicao = Integer.valueOf(codTipoPunicaoStr);
				carregarPunicao(codTipoPunicao);
				
			} else {
				punicao = new TipoPunicao();
				listaPunicoes = TipoPunicaoBO.getInstance().obterTiposPunicoes(punicao.getDescricao(),
						null);
			}
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	
	private void carregarPunicao(Integer codTipoPunicao) {
		try {
			ITipoPunicaoDAO tipoPunicaoDAO = getFabrica().getPostgresTipoPunicaoDAO();
			punicao = tipoPunicaoDAO.findById(codTipoPunicao);
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void pesquisar() {
		try {
			listaPunicoes = TipoPunicaoBO.getInstance().obterTiposPunicoes(punicao.getDescricao(),
					ativo);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	

	public String salvar() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			TipoPunicaoBO.getInstance().salvarTipoPunicao(punicao);
			addMsg(FacesMessage.SEVERITY_INFO, "Tipo de punição cadastrada com sucesso.");
			punicao = new TipoPunicao();
			return "consultarPunicao.tjse?faces-redirect=true";
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
		return "";
	}
	

	public String alterar() {
		try {
			TipoPunicaoBO.getInstance().alterarTipoPunicao(punicao);
			ativo = "";
			pesquisar();
			punicao = new TipoPunicao();
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			addMsg(FacesMessage.SEVERITY_INFO, "Tipo de punição alterada com sucesso.");
			return "consultarPunicao.tjse?faces-redirect=true";
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
		return "";
	}
	
	public String navegarAlteracao(int codTipoPunicao) {
		String url = "alterarPunicao.tjse?faces-redirect=true&codTipoPunicao=" + codTipoPunicao;
		return url;
	}
	
	

	@Override
	public String salvoSucesso() {
		// TODO Auto-generated method stub
		return null;
	}

	

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}


	public TipoPunicao getPunicao() {
		return punicao;
	}


	public void setPunicao(TipoPunicao punicao) {
		this.punicao = punicao;
	}


	public List<TipoPunicao> getListaPunicoes() {
		return listaPunicoes;
	}


	public void setListaPunicoes(List<TipoPunicao> listaPunicoes) {
		this.listaPunicoes = listaPunicoes;
	}

}
