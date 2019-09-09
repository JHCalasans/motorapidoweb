package br.com.motorapido.mbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.MotoristaAparelhoBO;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaAparelho;
import br.com.motorapido.util.ExcecoesUtil;

@ManagedBean(name = "idAparelhoBean")
@ViewScoped
public class IdAparelhoBean extends SimpleController {


	private static final long serialVersionUID = -5259409692084765770L;

	
	private String ativo;
	private String nomeMotoPesquisa;	
	private List<MotoristaAparelho> aparelhos;
	private List<Motorista> motoristas;
	private String idAparelho;
	
	@PostConstruct
	public void carregar() {
		if (getFacesContext().isPostback()) {
			return;
		}
		try {
			ativo = "T";
			pesquisarAparelhos();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void pesquisarAparelhos() {
		try {
			aparelhos = MotoristaAparelhoBO.getInstance().obterAparelhosMotoristas(ativo);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void pesquisarMotorista() {
		try {
			motoristas = MotoristaBO.getInstance().obterMotoristasPorNome(nomeMotoPesquisa);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void abrirVincular(String idAparelho) {
		this.idAparelho = idAparelho;
		enviarJavascript("PF('dlgVincularMotorista').show();");
	}
	
	
	public void vincularMoto(Integer codMotorista) {
		try {
			MotoristaAparelhoBO.getInstance().vincularMotorista(codMotorista, getIdAparelho());
			addMsg(FacesMessage.SEVERITY_INFO, "Aparelho vinculado com sucesso.");
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
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

	public List<MotoristaAparelho> getAparelhos() {
		return aparelhos;
	}

	public void setAparelhos(List<MotoristaAparelho> aparelhos) {
		this.aparelhos = aparelhos;
	}

	public String getNomeMotoPesquisa() {
		return nomeMotoPesquisa;
	}

	public void setNomeMotoPesquisa(String nomeMotoPesquisa) {
		this.nomeMotoPesquisa = nomeMotoPesquisa;
	}

	public List<Motorista> getMotoristas() {
		return motoristas;
	}

	public void setMotoristas(List<Motorista> motoristas) {
		this.motoristas = motoristas;
	}

	public String getIdAparelho() {
		return idAparelho;
	}

	public void setIdAparelho(String idAparelho) {
		this.idAparelho = idAparelho;
	}

}
