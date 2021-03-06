package br.com.motorapido.mbean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.Normalizer;
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
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

import com.google.gson.Gson;
import com.google.maps.model.LatLng;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.minhaLib.util.excecao.MsgUtil;
import br.com.motorapido.bo.FuncaoBO;
import br.com.motorapido.bo.LocalBO;
import br.com.motorapido.entity.Local;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.util.CoordenadasGoogle;
import br.com.motorapido.util.EnderecoCep;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.FuncoesUtil;

@SuppressWarnings("deprecation")
@ManagedBean(name = "localBean")
@ViewScoped
public class LocalBean extends SimpleController {

	private static final long serialVersionUID = -117170946685991453L;

	private Local local;

	private String cep;

	private String nomePesquisa;

	private List<Local> listaLocais;

	private Local localExcluir;

	private List<org.primefaces.model.map.LatLng> marcadores;

	private MapModel mapModel;

	private LatLng coordenadas;

	@PostConstruct
	public void carregar() {
		if (getFacesContext().isPostback()) {
			return;
		}
		try {
			String codLocalStr = (String) getRequestParam("codLocal");
			String cadSucesso = (String) getRequestParam("cadSucesso");
			String cadastrar = (String) getRequestParam("cadastroParam");
			String altSucesso = (String) getRequestParam("altSucesso");
			if (codLocalStr != null) {
				Integer codLocal = Integer.valueOf(codLocalStr);
				mapModel = new DefaultMapModel();
				carregarLocal(codLocal);		
				
			} else if (cadastrar != null && (cadastrar.equals("true") || cadastrar.equals("true?"))) {
				local = new Local();
				mapModel = new DefaultMapModel();
				setCoordenadas(new LatLng(-10.938604, -37.064269));
			} else {
				if (cadSucesso != null && (cadSucesso.equals("true") || cadSucesso.equals("true?")))
					addMsg(FacesMessage.SEVERITY_INFO, "Local cadastrado com sucesso.");
				if (altSucesso != null && (altSucesso.equals("true") || altSucesso.equals("true?")))
					addMsg(FacesMessage.SEVERITY_INFO, "Local alterado com sucesso.");
				pesquisarLocal();
			}
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void carregarLocal(Integer codLocal) {
		try {
			local = LocalBO.getInstance().obterLocalById(codLocal);
			cep = local.getCep();
			setCoordenadas(new LatLng(Double.parseDouble(local.getLatitude()), Double.parseDouble(local.getLongitude())));
			Marker marker = new Marker(new org.primefaces.model.map.LatLng (getCoordenadas().lat, getCoordenadas().lng), "Marcador");
			mapModel.addOverlay(marker);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public String navegarAlteracao(int codLocal) {
		String url = "alterarLocal.proj?faces-redirect=true&codLocal=" + codLocal;
		return url;
	}

	public void abrirExcluir(Local param) {
		localExcluir = param;
		enviarJavascript("PF('dlConfirmDelete').show();");
	}

	public void excluirLocal() {
		try {
			LocalBO.getInstance().excluirLocal(localExcluir);
			localExcluir = null;
			pesquisarLocal();
			addMsg(FacesMessage.SEVERITY_INFO, "Local excluído com sucesso.");
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void pesquisarLocal() {
		try {
			listaLocais = LocalBO.getInstance().obterLocal(nomePesquisa);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public String salvarLocal() {
		try {
			if(mapModel.getMarkers().isEmpty())
				throw new ExcecaoNegocio("Favor indique um local correspondente ao endereço no mapa.");
			LocalBO.getInstance().salvarLocal(getLocal());
			String url = "consultarLocal.proj??faces-redirect=true&cadSucesso=true";
			return url;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
		return "";
	}

	public String alterarLocal() {
		try {
			LocalBO.getInstance().salvarLocal(getLocal());
			String url = "consultarLocal.proj??faces-redirect=true&altSucesso=true";
			return url;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
		return "";
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
				this.getLocal().setCep(this.getCep());
				this.getLocal().setCidade(end.getLocalidade());
				this.getLocal().setLogradouro(end.getLogradouro());
				this.getLocal().setBairro(end.getBairro());
				buscarCoordenadas();
				switch (end.getUf()) {
				case "RO":
					this.getLocal().setEstado("Rondônia");
					break;
				case "AC":
					this.getLocal().setEstado("Acre");
					break;
				case "AM":
					this.getLocal().setEstado("Amazonas");
					break;
				case "RR":
					this.getLocal().setEstado("Roraima");
					break;
				case "PA":
					this.getLocal().setEstado("Pará");
					break;
				case "AP":
					this.getLocal().setEstado("Amapá");
					break;
				case "TO":
					this.getLocal().setEstado("Tocantins");
					break;
				case "MA":
					this.getLocal().setEstado("Maranhão");
					break;
				case "PI":
					this.getLocal().setEstado("Piauí");
					break;
				case "CE":
					this.getLocal().setEstado("Ceará");
					break;
				case "RN":
					this.getLocal().setEstado("Rio Grande do Norte");
					break;
				case "PB":
					this.getLocal().setEstado("Paraíba");
					break;
				case "PE":
					this.getLocal().setEstado("Pernambuco");
					break;
				case "AL":
					this.getLocal().setEstado("Alagoas");
					break;
				case "SE":
					this.getLocal().setEstado("Sergipe");
					break;
				case "BA":
					this.getLocal().setEstado("Bahia");
					break;
				case "MG":
					this.getLocal().setEstado("Minas Gerais");
					break;
				case "ES":
					this.getLocal().setEstado("Espírito Santo");
					break;
				case "RJ":
					this.getLocal().setEstado("Rio De Janeiro");
					break;
				case "SP":
					this.getLocal().setEstado("São Paulo");
					break;
				case "PR":
					this.getLocal().setEstado("Paraná");
					break;
				case "SC":
					this.getLocal().setEstado("Santa Catarina");
					break;
				case "RS":
					this.getLocal().setEstado("Rio Grande Do Sul");
					break;
				case "MS":
					this.getLocal().setEstado("Mato Grosso Do Sul");
					break;
				case "MT":
					this.getLocal().setEstado("Mato Grosso");
					break;
				case "GO":
					this.getLocal().setEstado("Goiás");
					break;
				case "DF":
					this.getLocal().setEstado("Distrito Federal");
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
	/*
	 * public static String removerAcentos(String str) { return
	 * Normalizer.normalize(str,
	 * Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""); }
	 */

	public void addMarker(PointSelectEvent event) {

		try {
			Marker marker = new Marker(event.getLatLng(), "Marcador");
			getLocal().setLatitude(String.valueOf(marker.getLatlng().getLat()));
			getLocal().setLongitude(String.valueOf(marker.getLatlng().getLng()));
			coordenadas = new LatLng(marker.getLatlng().getLat(), marker.getLatlng().getLng());
			getMapModel().getMarkers().clear();
			getMapModel().addOverlay(marker);
		} catch (Exception e) {
		}

	}

	private void buscarCoordenadas() {

		try {
			HttpClient httpClient = HttpClients.custom().build();

			// Buscando coordenadas pelo cep passado
			HttpUriRequest requestCoordenadas = RequestBuilder.get()
					.setUri("https://maps.googleapis.com/maps/api/geocode/json?address=" + this.cep.replace("-", "")
							+ "+BR&key=" + FuncaoBO.getInstance().getParam(ParametroEnum.CHAVE_MAPS))
					.setHeader("accept", "application/json").build();

			/*
			 * HttpUriRequest requestCoordenadas = RequestBuilder.get() .setUri(
			 * "https://maps.googleapis.com/maps/api/geocode/json?address=" +
			 * URLEncoder.encode(this.getLocal().getBairro(), "UTF-8") + "+" +
			 * URLEncoder.encode(this.getLocal().getLogradouro(), "UTF-8") + "+"
			 * + URLEncoder.encode(this.getLocal().getNumero(), "UTF-8") +
			 * "+BR&key=" +
			 * FuncoesUtil.getParam(ParametroEnum.CHAVE_MAPS.getCodigo()))
			 * .setHeader("accept", "application/json").build();
			 */

			System.out.println(requestCoordenadas.getURI());
			HttpResponse response;
			response = httpClient.execute(requestCoordenadas);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			String json = "";
			while ((output = br.readLine()) != null) {
				json += output + "\n";
			}

			Gson gson = new Gson();
			CoordenadasGoogle coordGoogle = gson.fromJson(json, CoordenadasGoogle.class);
			coordenadas = new LatLng(coordGoogle.getResults().get(0).getGeometry().getLocation().getLat(),
					coordGoogle.getResults().get(0).getGeometry().getLocation().getLng());
			/*
			 * this.getLocal()
			 * .setLatitude(String.valueOf(coordGoogle.getResults().get(0).
			 * getGeometry().getLocation().getLat())); this.getLocal()
			 * .setLongitude(String.valueOf(coordGoogle.getResults().get(0).
			 * getGeometry().getLocation().getLng()));
			 */

		} catch (Exception e) {
			e.printStackTrace();
			MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar coordenadas do mapa pelo CEP!.", "");
		}

	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNomePesquisa() {
		return nomePesquisa;
	}

	public void setNomePesquisa(String nomePesquisa) {
		this.nomePesquisa = nomePesquisa;
	}

	public List<Local> getListaLocais() {
		return listaLocais;
	}

	public void setListaLocais(List<Local> listaLocais) {
		this.listaLocais = listaLocais;
	}

	@Override
	public String salvoSucesso() {
		return null;
	}

	public Local getLocalExcluir() {
		return localExcluir;
	}

	public void setLocalExcluir(Local localExcluir) {
		this.localExcluir = localExcluir;
	}

	public List<org.primefaces.model.map.LatLng> getMarcadores() {
		return marcadores;
	}

	public void setMarcadores(List<org.primefaces.model.map.LatLng> marcadores) {
		this.marcadores = marcadores;
	}

	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public LatLng getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(LatLng coordenadas) {
		this.coordenadas = coordenadas;
	}

}
