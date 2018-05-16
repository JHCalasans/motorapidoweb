package br.com.motorapido.mbean;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.google.gson.Gson;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.minhaLib.util.excecao.MsgUtil;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.util.EnderecoCep;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.FuncoesUtil;
import br.com.motorapido.util.UtilDownload;

@SuppressWarnings("deprecation")
@ManagedBean(name = "motoristaBean")
@ViewScoped
public class MotoristaBean extends SimpleController {

	private static final long serialVersionUID = -247976915991325625L;

	private Motorista motorista;

	private UploadedFile foto;

	private StreamedContent streamFoto;

	private String cnh;

	private Date vencimentoCNH;

	private String cep;

	private UploadedFile docCriminais;
	
	private UploadedFile compResidencial;

	private String nomePesquisa;

	private String cpfPesquisa;

	private List<Motorista> listaMotoristas;
	
	private String msgSalvar;
	
	private String motivoBloqueio;
	
	private Motorista motoristaBloqeuar;
	
	private Date dataInicioBloqueio;
	
	private Date dataFinalBloqueio;
	
	

	@PostConstruct
	public void carregar() {
		if (getFacesContext().isPostback()) {
			return;
		}		
		try {
			String codMotoStr = (String) getRequestParam("codMotorista");
			String consultar = (String) getRequestParam("consultaParam");
			if (codMotoStr != null) {
				Integer codMotorista = Integer.valueOf(codMotoStr);
				carregarMotorista(codMotorista);				
				cep = motorista.getCep();
			} else if (consultar != null && (consultar.equals("true") || consultar.equals("true?"))) {
				pesquisarMotorista();

			} else {
				motorista = new Motorista();
				
			}			

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	
	public void limparDlgBloqueio(){
		dataFinalBloqueio = null;
		dataInicioBloqueio = null;
		motivoBloqueio = null;
		motoristaBloqeuar = null;
	}
	
	private void carregarMotorista(Integer codMotorista) {
		try {

			motorista = MotoristaBO.getInstance().obterMotoristaPorCodigo(codMotorista);
			if(motorista.getFoto() != null)
				streamFoto = new DefaultStreamedContent(new ByteArrayInputStream(motorista.getFoto()), "image/*");
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	
	public void carregarMotoristaBloquear(Motorista moto, boolean bloquear) {
		try {
			motoristaBloqeuar = moto;
			if(bloquear)
				enviarJavascript("PF('varDlgBloquearMoto').show()");
			else
				enviarJavascript("PF('vardlConfirmDesbloqueio').show()");
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	public void pesquisarMotorista() {
		try {
			listaMotoristas = MotoristaBO.getInstance().obterMotoristas(nomePesquisa, cpfPesquisa);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public String navegarAlteracao(int codMotorista) {
		String url = "alterarMotorista.proj?faces-redirect=true&codMotorista=" + codMotorista;
		return url;
	}
	
	public String navegarVeiculos(int codMotorista) {
		String url = "/paginas/veiculo/cadastrarVeiculo.proj?faces-redirect=true&codMotorista=" + codMotorista;
		return url;
	}

	public boolean validarCpf() {
		if (this.motorista.getCpf() != null && this.motorista.getCpf().length() != 14) {
			MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "CPF inválido!.", "");
			return false;
		} else {
			try {
				Motorista moto = new Motorista();
				moto.setCpf(motorista.getCpf());
				List<Motorista> lista = MotoristaBO.getInstance().obterMotoristasExample(moto);
				if (lista != null && lista.size() > 0
						&& (motorista.getCodigo() == null || motorista.getCodigo() != lista.get(0).getCodigo())) {
					MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "CPF já cadastrado na base de dados!.", "");
					return false;
				}
				return true;
			} catch (ExcecaoNegocio e) {
				ExcecoesUtil.TratarExcecao(e);
				return false;
			}
		}

	}

	public boolean validarRG() {

		try {
			Motorista moto = new Motorista();
			moto.setIdentidade(motorista.getIdentidade());
			List<Motorista> lista = MotoristaBO.getInstance().obterMotoristasExample(moto);
			if (lista != null && lista.size() > 0
					&& (motorista.getCodigo() == null || motorista.getCodigo() != lista.get(0).getCodigo())) {
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "RG já cadastrado na base de dados!.", "");
				return false;
			}
			return true;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return false;
		}

	}

	public boolean validarEmail() {

		try {
			Motorista moto = new Motorista();
			moto.setEmail(motorista.getEmail());
			List<Motorista> lista = MotoristaBO.getInstance().obterMotoristasExample(moto);
			if (lista != null && lista.size() > 0
					&& (motorista.getCodigo() == null || motorista.getCodigo() != lista.get(0).getCodigo())) {
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Email já cadastrado na base de dados!.", "");
				return false;
			}
			return true;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return false;
		}

	}
	
	public void carregarFotoExibicao() {
		byte[] fotoExibicao = null;
		if (foto != null) {
			fotoExibicao = foto.getContents();
			streamFoto = new DefaultStreamedContent(new ByteArrayInputStream(fotoExibicao), "image/*");
		}
	}
	
	public boolean validarCNH() {

		try {
			Motorista moto = new Motorista();
			moto.setCnh(motorista.getCnh());
			List<Motorista> lista = MotoristaBO.getInstance().obterMotoristasExample(moto);
			if (lista != null && lista.size() > 0
					&& (motorista.getCodigo() == null || motorista.getCodigo() != lista.get(0).getCodigo())) {
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "CNH já cadastrada na base de dados!.", "");
				return false;
			}
			return true;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return false;
		}

	}

	public void salvarMotorista() {
		if (docCriminais == null) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Documentos Criminais não Anexados.");
			return;
		}
		
		if (compResidencial == null) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Comprovante Residencial não Anexado.");
			return;
		}
		if (!validarCpf())
			return;		
		if (!validarRG())
			return;	
		if (!validarEmail())
			return;	
		if (!validarCNH())
			return;	

		try {
			if(foto != null)
				motorista.setFoto(foto.getContents());
			msgSalvar = FuncoesUtil.gerarSenha();
			motorista.setDocCriminais(docCriminais.getContents());
			motorista.setComprovanteResidencia(compResidencial.getContents());
			motorista.setSenha(msgSalvar);
			MotoristaBO.getInstance().salvarMotorista(motorista);
			//limparCampos();
			//addMsg(FacesMessage.SEVERITY_INFO, "Motorista cadastrado com sucesso.");
			enviarJavascript("PF('dlgSucesso').show();");

		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void alterarMotorista() {
	
		if (!validarCpf())
			return;		
		if (!validarRG())
			return;	
		if (!validarEmail())
			return;	
		if (!validarCNH())
			return;	
		try {
			if (foto != null && foto.getContents() != null)
				motorista.setFoto(foto.getContents());
			if(docCriminais != null)
				motorista.setDocCriminais(docCriminais.getContents());
			MotoristaBO.getInstance().alterarMotorista(motorista);
			enviarJavascript("PF('dlgSucesso').show();");
			
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void downloadDocCriminais(Motorista moto)
	{
		try {			
			UtilDownload.download(moto.getDocCriminais(), "Documentos Criminais de "+ moto.getNome() +".pdf",
					UtilDownload.MIMETYPE_OCTETSTREAM,
					UtilDownload.CONTENT_DISPOSITION_ATTACHMENT);
		} catch (Exception e)
		{
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void bloquearMotorista()
	{
		try {			
			MotoristaBO.getInstance().bloquearMotorista(motoristaBloqeuar, getFuncionarioLogado(), motivoBloqueio, dataInicioBloqueio, dataFinalBloqueio);
			enviarJavascript("PF('varDlgBloquearMoto').hide()");
			addMsg(FacesMessage.SEVERITY_INFO, "Motorista bloqueado com sucesso!.");
		} catch (Exception e)
		{
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void desbloquearMotorista()
	{
		try {			
			MotoristaBO.getInstance().desbloquearMotorista(motoristaBloqeuar, new Date());
			addMsg(FacesMessage.SEVERITY_INFO, "Motorista desbloqueado com sucesso!.");
		} catch (Exception e)
		{
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	private void limparCampos() {
		motorista = new Motorista();
		cnh = null;
		vencimentoCNH = null;
		docCriminais = null;
		foto = null;
	}

	public void fileUploadAction(FileUploadEvent event) {
		setFoto(event.getFile());
	}

	public void DocCriminaisUploadAction(FileUploadEvent event) {
		setDocCriminais(event.getFile());
	}
	
	public void comprovanteResidencialUploadAction(FileUploadEvent event) {
		setCompResidencial(event.getFile());
	}

	public void validarCep() {
		if (this.getCep() != null && this.getCep().replace("-", "").replace("_", "").length() == 8) {
			try {

				HttpClient httpClient = HttpClients.custom().build();

				HttpUriRequest request = RequestBuilder.get()
						.setUri("http://www.viacep.com.br/ws/" + this.getCep().replace("-", "") + "/json/")
						.setHeader("accept", "application/json").build();

				HttpResponse response = httpClient.execute(request);

				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				String output;
				String json = "";
				while ((output = br.readLine()) != null) {
					json += output + "\n";
				}

				Gson gson = new Gson();
				EnderecoCep end = gson.fromJson(json, EnderecoCep.class);
				this.getMotorista().setCep(this.getCep());
				this.getMotorista().setCidadeResidencia(end.getLocalidade());
				this.getMotorista().setLogradouro(end.getLogradouro());
				this.getMotorista().setBairro(end.getBairro());

				switch (end.getUf()) {
				case "RO":
					this.getMotorista().setEstadoResidencia("Rondônia");
					break;
				case "AC":
					this.getMotorista().setEstadoResidencia("Acre");
					break;
				case "AM":
					this.getMotorista().setEstadoResidencia("Amazonas");
					break;
				case "RR":
					this.getMotorista().setEstadoResidencia("Roraima");
					break;
				case "PA":
					this.getMotorista().setEstadoResidencia("Pará");
					break;
				case "AP":
					this.getMotorista().setEstadoResidencia("Amapá");
					break;
				case "TO":
					this.getMotorista().setEstadoResidencia("Tocantins");
					break;
				case "MA":
					this.getMotorista().setEstadoResidencia("Maranhão");
					break;
				case "PI":
					this.getMotorista().setEstadoResidencia("Piauí");
					break;
				case "CE":
					this.getMotorista().setEstadoResidencia("Ceará");
					break;
				case "RN":
					this.getMotorista().setEstadoResidencia("Rio Grande do Norte");
					break;
				case "PB":
					this.getMotorista().setEstadoResidencia("Paraíba");
					break;
				case "PE":
					this.getMotorista().setEstadoResidencia("Pernambuco");
					break;
				case "AL":
					this.getMotorista().setEstadoResidencia("Alagoas");
					break;
				case "SE":
					this.getMotorista().setEstadoResidencia("Sergipe");
					break;
				case "BA":
					this.getMotorista().setEstadoResidencia("Bahia");
					break;
				case "MG":
					this.getMotorista().setEstadoResidencia("Minas Gerais");
					break;
				case "ES":
					this.getMotorista().setEstadoResidencia("Espírito Santo");
					break;
				case "RJ":
					this.getMotorista().setEstadoResidencia("Rio De Janeiro");
					break;
				case "SP":
					this.getMotorista().setEstadoResidencia("São Paulo");
					break;
				case "PR":
					this.getMotorista().setEstadoResidencia("Paraná");
					break;
				case "SC":
					this.getMotorista().setEstadoResidencia("Santa Catarina");
					break;
				case "RS":
					this.getMotorista().setEstadoResidencia("Rio Grande Do Sul");
					break;
				case "MS":
					this.getMotorista().setEstadoResidencia("Mato Grosso Do Sul");
					break;
				case "MT":
					this.getMotorista().setEstadoResidencia("Mato Grosso");
					break;
				case "GO":
					this.getMotorista().setEstadoResidencia("Goiás");
					break;
				case "DF":
					this.getMotorista().setEstadoResidencia("Distrito Federal");
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível buscar informações do CEP!.", "");
			}
		} else {
			MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "CEP inválido!.", "");
		}

	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public UploadedFile getFoto() {
		return foto;
	}

	public void setFoto(UploadedFile foto) {
		this.foto = foto;
	}

	public StreamedContent getStreamFoto() {
		carregarFotoExibicao();
		return streamFoto;
	}

	public void setStreamFoto(StreamedContent streamFoto) {
		this.streamFoto = streamFoto;
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public Date getVencimentoCNH() {
		return vencimentoCNH;
	}

	public void setVencimentoCNH(Date vencimentoCNH) {
		this.vencimentoCNH = vencimentoCNH;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public UploadedFile getDocCriminais() {
		return docCriminais;
	}

	public void setDocCriminais(UploadedFile docCriminais) {
		this.docCriminais = docCriminais;
	}

	

	public String getNomePesquisa() {
		return nomePesquisa;
	}

	public void setNomePesquisa(String nomePesquisa) {
		this.nomePesquisa = nomePesquisa;
	}

	public String getCpfPesquisa() {
		return cpfPesquisa;
	}

	public void setCpfPesquisa(String cpfPesquisa) {
		this.cpfPesquisa = cpfPesquisa;
	}

	public List<Motorista> getListaMotoristas() {
		return listaMotoristas;
	}

	public void setListaMotoristas(List<Motorista> listaMotoristas) {
		this.listaMotoristas = listaMotoristas;
	}
	
	@Override
	public String salvoSucesso() {
		limparCampos();
		return "consultarMotorista.proj?faces-redirect=true&consultaParam=true";
		
	}

	public String getMsgSalvar() {
		return msgSalvar;
	}

	public void setMsgSalvar(String msgSalvar) {
		this.msgSalvar = msgSalvar;
	}

	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}

	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}

	public Motorista getMotoristaBloqeuar() {
		return motoristaBloqeuar;
	}

	public void setMotoristaBloqeuar(Motorista motoristaBloqeuar) {
		this.motoristaBloqeuar = motoristaBloqeuar;
	}

	public Date getDataInicioBloqueio() {
		return dataInicioBloqueio;
	}

	public void setDataInicioBloqueio(Date dataInicioBloqueio) {
		this.dataInicioBloqueio = dataInicioBloqueio;
	}

	public Date getDataFinalBloqueio() {
		return dataFinalBloqueio;
	}

	public void setDataFinalBloqueio(Date dataFinalBloqueio) {
		this.dataFinalBloqueio = dataFinalBloqueio;
	}

	
	public Date dataDeHoje(){
		return new Date();
	}


	public UploadedFile getCompResidencial() {
		return compResidencial;
	}


	public void setCompResidencial(UploadedFile compResidencial) {
		this.compResidencial = compResidencial;
	}
	

	

}
