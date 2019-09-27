package br.com.motorapido.mbean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.motorapido.bo.LogErroBO;
import br.com.motorapido.bo.LogErroMotoristaBO;
import br.com.motorapido.entity.LogErro;
import br.com.motorapido.entity.LogErroMotorista;
import br.com.motorapido.util.ExcecoesUtil;

@ManagedBean(name = "logBean")
@ViewScoped
public class LogBean extends SimpleController{


	private static final long serialVersionUID = 3896578044594524165L;
	
	private Date dataInicial;
	private Date dataFinal;
	private List<LogErro> listaErros;
	private List<LogErroMotorista> listaErrosMotoristas;
	
	
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
	
	
	public void pesquisarLog() {
		try {
			listaErros = LogErroBO.getInstance().pesquisarLogPorData(dataInicial, dataFinal);
		}catch(Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void pesquisarLogMotoristas() {
		try {
			listaErrosMotoristas = LogErroMotoristaBO.getInstance().pesquisarLogPorData(dataInicial, dataFinal);
		}catch(Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	@Override
	public String salvoSucesso() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}


	public List<LogErro> getListaErros() {
		return listaErros;
	}


	public void setListaErros(List<LogErro> listaErros) {
		this.listaErros = listaErros;
	}


	public List<LogErroMotorista> getListaErrosMotoristas() {
		return listaErrosMotoristas;
	}


	public void setListaErrosMotoristas(List<LogErroMotorista> listaErrosMotoristas) {
		this.listaErrosMotoristas = listaErrosMotoristas;
	}

}
