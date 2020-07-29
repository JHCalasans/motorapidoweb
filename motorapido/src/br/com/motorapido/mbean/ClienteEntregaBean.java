package br.com.motorapido.mbean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

import com.google.gson.Gson;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.minhaLib.util.excecao.MsgUtil;
import br.com.motorapido.bo.ClienteBO;
import br.com.motorapido.entity.Cliente;
import br.com.motorapido.entity.EnderecoCliente;
import br.com.motorapido.util.EnderecoCep;
import br.com.motorapido.util.ExcecoesUtil;

@SuppressWarnings("deprecation")
@ManagedBean(name = "clienteEntregaBean")
@ViewScoped
public class ClienteEntregaBean extends SimpleController {


	private static final long serialVersionUID = -3839219600415306293L;

	private Cliente cliente;

	private String cep;

	private String celPesquisa;

	private EnderecoCliente enderecoCliente;
	
	private List<EnderecoCliente> listaEnderecosCliente;

	private Cliente clienteRestricao;

	private String celularAntigo;

	private String emailAntigo;

	@PostConstruct
	public void carregar() {
		if (getFacesContext().isPostback()) {
			return;
		}
		try {
				cliente = new Cliente();
				enderecoCliente = new EnderecoCliente();

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public String getCelularAntigo() {
		return celularAntigo;
	}

	public void setCelularAntigo(String celularAntigo) {
		this.celularAntigo = celularAntigo;
	}

	public String getEmailAntigo() {
		return emailAntigo;
	}

	public void setEmailAntigo(String emailAntigo) {
		this.emailAntigo = emailAntigo;
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
				this.getEnderecoCliente().setCep(this.getCep());
				this.getEnderecoCliente().setCidade(end.getLocalidade());
				this.getEnderecoCliente().setLogradouro(end.getLogradouro());
				this.getEnderecoCliente().setBairro(end.getBairro());

				switch (end.getUf()) {
				case "RO":
					this.getEnderecoCliente().setEstado("Rondônia");
					break;
				case "AC":
					this.getEnderecoCliente().setEstado("Acre");
					break;
				case "AM":
					this.getEnderecoCliente().setEstado("Amazonas");
					break;
				case "RR":
					this.getEnderecoCliente().setEstado("Roraima");
					break;
				case "PA":
					this.getEnderecoCliente().setEstado("Pará");
					break;
				case "AP":
					this.getEnderecoCliente().setEstado("Amapá");
					break;
				case "TO":
					this.getEnderecoCliente().setEstado("Tocantins");
					break;
				case "MA":
					this.getEnderecoCliente().setEstado("Maranhão");
					break;
				case "PI":
					this.getEnderecoCliente().setEstado("Piauí");
					break;
				case "CE":
					this.getEnderecoCliente().setEstado("Ceará");
					break;
				case "RN":
					this.getEnderecoCliente().setEstado("Rio Grande do Norte");
					break;
				case "PB":
					this.getEnderecoCliente().setEstado("Paraíba");
					break;
				case "PE":
					this.getEnderecoCliente().setEstado("Pernambuco");
					break;
				case "AL":
					this.getEnderecoCliente().setEstado("Alagoas");
					break;
				case "SE":
					this.getEnderecoCliente().setEstado("Sergipe");
					break;
				case "BA":
					this.getEnderecoCliente().setEstado("Bahia");
					break;
				case "MG":
					this.getEnderecoCliente().setEstado("Minas Gerais");
					break;
				case "ES":
					this.getEnderecoCliente().setEstado("Espírito Santo");
					break;
				case "RJ":
					this.getEnderecoCliente().setEstado("Rio De Janeiro");
					break;
				case "SP":
					this.getEnderecoCliente().setEstado("São Paulo");
					break;
				case "PR":
					this.getEnderecoCliente().setEstado("Paraná");
					break;
				case "SC":
					this.getEnderecoCliente().setEstado("Santa Catarina");
					break;
				case "RS":
					this.getEnderecoCliente().setEstado("Rio Grande Do Sul");
					break;
				case "MS":
					this.getEnderecoCliente().setEstado("Mato Grosso Do Sul");
					break;
				case "MT":
					this.getEnderecoCliente().setEstado("Mato Grosso");
					break;
				case "GO":
					this.getEnderecoCliente().setEstado("Goiás");
					break;
				case "DF":
					this.getEnderecoCliente().setEstado("Distrito Federal");
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

	public boolean validarEmail() {

		try {
			Cliente clienteTemp = new Cliente();
			if (cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
				clienteTemp.setEmail(cliente.getEmail());
				clienteTemp = ClienteBO.getInstance().obterClienteByExample(clienteTemp);
				if (clienteTemp != null) {
					MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Email já cadastrado na base de dados!.", "");
					return false;
				}
			}
			return true;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return false;
		}

	}

	public boolean validarCelular() {

		try {
			Cliente clienteTemp = new Cliente();
			clienteTemp.setCelular(cliente.getCelular());
			clienteTemp = ClienteBO.getInstance().obterClienteByExample(clienteTemp);
			if (clienteTemp != null) {
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Celular já cadastrado na base de dados!.", "");
				return false;
			}
			return true;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return false;
		}

	}



	public String navegarAlteracao(int codCliente) {
		String url = "alterarCliente.proj?faces-redirect=true&codCliente=" + codCliente;
		return url;
	}

	public String navegarEnderecos(int codCliente) {
		String url = "vincularEndereco.proj?faces-redirect=true&endereco=true&codCliente=" + codCliente;
		return url;
	}

	public void vincularEndereco() {
		try {
			enderecoCliente.setCliente(getCliente());
			ClienteBO.getInstance().salvarEndereco(enderecoCliente);
			listaEnderecosCliente = ClienteBO.getInstance().obterEnderecos(getCliente());
			MsgUtil.updateMessage(FacesMessage.SEVERITY_INFO, "Endereço incluído com sucesso!", "");
			enderecoCliente = new EnderecoCliente();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public String salvarCliente() {

		if (!validarEmail())
			return "";
		if (!validarCelular())
			return "";
		try {
			ClienteBO.getInstance().salvarCliente(cliente, enderecoCliente);
			//MsgUtil.updateMessage(FacesMessage.SEVERITY_INFO, "Cadastro efetuado com sucesso!", "");
			enviarJavascript("PF('dlgCadSucesso').show()");
			return "";
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return "";
		}
	}

	public String alterarCliente() {

		if ((emailAntigo != null && !emailAntigo.equals(cliente.getEmail()))
				|| (emailAntigo == null && cliente.getEmail() != null)) {
			if (!validarEmail())
				return "";
		}
		if ((celularAntigo != null && !celularAntigo.equals(cliente.getCelular()))
				|| (celularAntigo == null && cliente.getCelular() != null)) {
			if (!validarCelular())
				return "";
		}
		try {
			ClienteBO.getInstance().salvarCliente(cliente, null);
			String url = "consultarCliente.proj??faces-redirect=true&altSucesso=true";
			return url;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return "";
		}
	}

	@Override
	public String salvoSucesso() {
		return  "loginCliente.proj??faces-redirect=true";
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	

	public String getCelPesquisa() {
		return celPesquisa;
	}

	public void setCelPesquisa(String celPesquisa) {
		this.celPesquisa = celPesquisa;
	}

	

	public EnderecoCliente getEnderecoCliente() {
		return enderecoCliente;
	}

	public void setEnderecoCliente(EnderecoCliente enderecoCliente) {
		this.enderecoCliente = enderecoCliente;
	}

	public List<EnderecoCliente> getListaEnderecosCliente() {
		return listaEnderecosCliente;
	}

	public void setListaEnderecosCliente(List<EnderecoCliente> listaEnderecosCliente) {
		this.listaEnderecosCliente = listaEnderecosCliente;
	}

	

	public Cliente getClienteRestricao() {
		return clienteRestricao;
	}

	public void setClienteRestricao(Cliente clienteRestricao) {
		this.clienteRestricao = clienteRestricao;
	}

}
