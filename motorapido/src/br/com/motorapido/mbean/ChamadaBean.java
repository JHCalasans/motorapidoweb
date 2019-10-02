package br.com.motorapido.mbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.Visibility;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import com.google.maps.model.LatLng;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.ChamadaBO;
import br.com.motorapido.bo.ChamadaVeiculoBO;
import br.com.motorapido.bo.ClienteBO;
import br.com.motorapido.bo.EnderecoClienteBO;
import br.com.motorapido.bo.LocalBO;
import br.com.motorapido.bo.ParametroBO;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.entity.Chamada;
import br.com.motorapido.entity.ChamadaVeiculo;
import br.com.motorapido.entity.Cliente;
import br.com.motorapido.entity.EnderecoCliente;
import br.com.motorapido.entity.Local;
import br.com.motorapido.entity.Logradouro;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.enums.SituacaoChamadaEnum;
import br.com.motorapido.util.ControleSessaoWS;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.GoogleWSUtil;
import br.com.motorapido.util.RetornoGoogleWSCoordenadas;

@SuppressWarnings("deprecation")
@ManagedBean(name = "chamadaBean")
@ViewScoped
public class ChamadaBean extends SimpleController {

	private static final long serialVersionUID = -3771944142798009548L;

	private Chamada chamada;

	private Integer codPesquisa;

	private List<Cliente> listaClientesDialog;

	private String nomePesquisa;

	private String nomeLocalPesquisa;

	private String numCelPesquisa;

	private Cliente cliente;

	private EnderecoCliente enderecoClienteOrigem;

	private EnderecoCliente enderecoDestinoCliente;

	private Local enderecoClienteDestino;

	private List<EnderecoCliente> enderecosDoCliente;

	private List<Local> locais;

	private Boolean isDestino = false;

	private Local localOrigem;

	private Boolean destinoFechado = true;

	private MapModel mapModel;

	private LatLng coordenadas;

	private Integer situacaoChamadaFiltro;

	private List<Chamada> chamadas;
	
	private List<ChamadaVeiculo> detalheChamadas;

	private List<Caracteristica> listaCaracteristicasSelecionadas;

	private List<String> autoComplete = new ArrayList<String>();

	private Logradouro logradouro;

	private Logradouro logradouroDestino;

	private Logradouro logradouroOrigemSelecionado;

	private Logradouro logradouroDestSelecionado;

	private String componenteParaUpdate;

	private String tipoMarcador = "O";

	private String tipoMarcadorJaAdicionado;

	private Boolean showBotaoRemover = false;

	private InputText numeroOrigem;
	
	private Integer contadorLista;

	@PostConstruct
	public void carregar() {
		if (getFacesContext().isPostback()) {
			return;
		}
		try {
			limparCampos();

			// iniciarListaLogradouros();
			mapModel = new DefaultMapModel();
			logradouro = new Logradouro();
			enderecoClienteOrigem = new EnderecoCliente();
			enderecoDestinoCliente = new EnderecoCliente();
			cliente = new Cliente();
			contadorLista = new Integer(getListaChamadasEmEspera().size());
			setSituacaoChamadaFiltro(-1);
			if (coordenadas == null) {
				String coordenadasIniciais = ParametroBO.getInstance()
						.getParam(ParametroEnum.COORDENADAS_INICIAIS.getCodigo());
				String[] coord = coordenadasIniciais.split(";");
				setCoordenadas(new LatLng(Double.parseDouble(coord[0]), Double.parseDouble(coord[1])));
			}
			// atualizarChamadas();
			atualizarChamadasInicio();

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void removerMarcador() {

		for (Marker mark : mapModel.getMarkers()) {
			if (mark.getTitle().startsWith(tipoMarcador)) {
				mapModel.getMarkers().remove(mark);
				break;
			}
		}
	
	}

	

	public Boolean botaoRemoverHabilitado() {
		Boolean retorno = false;
		for (Marker mark : mapModel.getMarkers()) {
			retorno = mark.getTitle().startsWith(tipoMarcador);
			if (retorno)
				break;
		}
		return retorno;
	}

	public void teste() {
		
	}
	
	public void verificarLista(){
		if(getSituacaoChamadaFiltro().equals(SituacaoChamadaEnum.PENDENTE.getCodigo()) ||
				getSituacaoChamadaFiltro().equals(SituacaoChamadaEnum.PENDENTE_GERAL.getCodigo())){
			System.out.println("Teste");
		}
	}

	public void addMarker(PointSelectEvent event) {

		try {
			// mapModel.getMarkers().clear();
			Marker marker = new Marker(event.getLatLng());

			/*
			 * if (tipoMarcadorJaAdicionado != null &&
			 * !tipoMarcadorJaAdicionado.isEmpty() &&
			 * tipoMarcadorJaAdicionado.equals(tipoMarcador)) {
			 * 
			 * for (Marker mark : mapModel.getMarkers()) { //if(mark.) } }
			 */

			for (Marker mark : mapModel.getMarkers()) {
				if (mark.getTitle().startsWith(tipoMarcador)) {
					mapModel.getMarkers().remove(mark);
					break;
				}
			}
			mapModel.addOverlay(marker);
			org.primefaces.model.map.LatLng coord = event.getLatLng();

			RetornoGoogleWSCoordenadas retorno = GoogleWSUtil
					.buscarCoordenadas(String.valueOf(coord.getLat()) + "," + String.valueOf(coord.getLng()));
		/*	Logradouro logra = getListaLogradouro().stream()
					.filter(lo -> lo.getLatitude().substring(0, 8).equals(String.valueOf(-10.9289523).substring(0, 8)))
					.filter(lo -> lo.getLongitude().substring(0, 8).equals(String.valueOf(-37.0503594).substring(0,8)))
					.findFirst().get();*/
			if (!tipoMarcador.equals("O")) {
				marker.setIcon("/motorapido/resources/chegada.png");
				marker.setTitle("Destino");
				logradouroDestino = new Logradouro();
				logradouroDestino
						.setDescricao(retorno.getResults().get(0).getAddress_components().get(1).getLong_name());
				chamada.setCidadeDestino(retorno.getResults().get(0).getAddress_components().get(3).getLong_name());
				logradouroDestino
						.setLogradouroComCidade(logradouroDestino.getDescricao() + " - " + chamada.getCidadeDestino());
				chamada.setBairroDestino(retorno.getResults().get(0).getAddress_components().get(2).getLong_name());
				chamada.setCepDestino(retorno.getResults().get(0).getAddress_components().get(6).getLong_name());
				chamada.setLogradouroDestino(logradouroDestino.getDescricao());

				chamada.setLatitudeDestino(String.valueOf(coord.getLat()));
				chamada.setLongitudeDestino(String.valueOf(coord.getLng()));
				chamada.setComplementoDestino(null);
				

			} else {
				marker.setTitle("Origem");
				logradouro = new Logradouro();
				logradouro.setDescricao(retorno.getResults().get(0).getAddress_components().get(1).getLong_name());
				chamada.setCidadeOrigem(retorno.getResults().get(0).getAddress_components().get(3).getLong_name());
				logradouro.setLogradouroComCidade(logradouro.getDescricao() + " - " + chamada.getCidadeOrigem());
				chamada.setBairroOrigem(retorno.getResults().get(0).getAddress_components().get(2).getLong_name());
				chamada.setCepOrigem(retorno.getResults().get(0).getAddress_components().get(6).getLong_name());
				chamada.setLogradouroOrigem(logradouro.getDescricao());

				chamada.setLatitudeOrigem(String.valueOf(coord.getLat()));
				chamada.setLongitudeOrigem(String.valueOf(coord.getLng()));
				chamada.setComplementoOrigem(null);
			}
			
			coordenadas.lat = coord.getLat();
			coordenadas.lng = coord.getLng();

		} catch (Exception e) {
			
		}

	}

	public void marcadorSelecionado(OverlaySelectEvent event) {
		// mapModel.getMarkers().clear();
		Marker mark = (Marker) event.getOverlay();
		mapModel.getMarkers().remove(mark);
		if (mark.getTitle().startsWith("O")) {
			logradouro = new Logradouro();
			enderecoClienteOrigem = new EnderecoCliente();
			chamada.setBairroOrigem(null);
			chamada.setCepOrigem(null);
			chamada.setCidadeOrigem(null);
			chamada.setComplementoOrigem(null);
			chamada.setLatitudeOrigem(null);
			chamada.setLogradouroOrigem(null);
			chamada.setLongitudeOrigem(null);
			chamada.setNumeroOrigem(null);
		} else {
			logradouroDestino = new Logradouro();
			chamada.setBairroDestino(null);
			chamada.setCepDestino(null);
			chamada.setCidadeDestino(null);
			chamada.setComplementoDestino(null);
			chamada.setLatitudeDestino(null);
			chamada.setLogradouroDestino(null);
			chamada.setLongitudeDestino(null);
			chamada.setNumeroDestino(null);
		}
		/*
		 * logradouro = new Logradouro(); enderecoClienteOrigem = new
		 * EnderecoCliente(); chamada = new Chamada();
		 */
	}

	public void logradouroSelecionado(SelectEvent event) {
		Logradouro logSelecionado = (Logradouro) event.getObject();
		/*
		 * EnderecoCliente endereco = new EnderecoCliente();
		 * endereco.setLatitude(logSelecionado.getLatitude());
		 * endereco.setLongitude(logSelecionado.getLongitude());
		 * enderecoClienteOrigem.setBairro(logSelecionado.getBairro().getBairro(
		 * )); enderecoClienteOrigem.setCep(logSelecionado.getCep());
		 * enderecoClienteOrigem.setCidade(logSelecionado.getCidade().getCidade(
		 * )); enderecoClienteOrigem.setLatitude(logSelecionado.getLatitude());
		 * enderecoClienteOrigem.setLongitude(logSelecionado.getLongitude());
		 * logradouroOrigemSelecionado = logSelecionado;
		 * ajustarMarcadorMapa(endereco);
		 */
		chamada.setLogradouroOrigem(logSelecionado.getDescricao());
		chamada.setBairroOrigem(logSelecionado.getBairro().getBairro());
		chamada.setCepOrigem(logSelecionado.getCep());
		chamada.setCidadeOrigem(logSelecionado.getCidade().getCidade());
		chamada.setLatitudeOrigem(logSelecionado.getLatitude());
		chamada.setLongitudeOrigem(logSelecionado.getLongitude());
		ajustarMarcadorMapaChamada(chamada);

	}

	public void logradouroDestinoSelecionado(SelectEvent event) {
		Logradouro logSelecionado = (Logradouro) event.getObject();
		/*
		 * EnderecoCliente endereco = new EnderecoCliente();
		 * endereco.setLatitude(logSelecionado.getLatitude());
		 * endereco.setLongitude(logSelecionado.getLongitude());
		 * enderecoDestinoCliente.setBairro(logSelecionado.getBairro().getBairro
		 * ()); enderecoDestinoCliente.setCep(logSelecionado.getCep());
		 * enderecoDestinoCliente.setCidade(logSelecionado.getCidade().getCidade
		 * ());
		 * enderecoDestinoCliente.setLatitude(logSelecionado.getLatitude());
		 * enderecoDestinoCliente.setLongitude(logSelecionado.getLongitude());
		 * logradouroDestSelecionado = logSelecionado;
		 * ajustarMarcadorMapa(endereco);
		 */
		chamada.setLogradouroDestino(logSelecionado.getDescricao());
		chamada.setBairroDestino(logSelecionado.getBairro().getBairro());
		chamada.setCepDestino(logSelecionado.getCep());
		chamada.setCidadeDestino(logSelecionado.getCidade().getCidade());
		chamada.setLatitudeDestino(logSelecionado.getLatitude());
		chamada.setLongitudeDestino(logSelecionado.getLongitude());
		// ajustarMarcadorMapaChamada(chamada);

	}

	@Override
	public String salvoSucesso() {
		return null;
	}

	public List<Logradouro> completeText(String query) {
		List<Logradouro> result = new ArrayList<Logradouro>();
		int val = 0;
		try {

			result = getListaLogradouro().stream()
					.filter(lo -> lo.getDescricao().toLowerCase().contains(query.toLowerCase()))
					.collect(Collectors.toList());
			/*
			 * for (int i = 0; i < autoComplete.size(); i++) { val = i; if
			 * (autoComplete.get(i).toLowerCase().contains(query.toLowerCase()))
			 * { result.add(autoComplete.get(i)); } }
			 */
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}

		return result;
	}

	public void atualizarChamadasFiltro() {
		try {
			if(getSituacaoChamadaFiltro() == SituacaoChamadaEnum.ACEITA.getCodigo())
				chamadas = getListaChamadasAceitas();
			else if (getSituacaoChamadaFiltro() == SituacaoChamadaEnum.PENDENTE.getCodigo())
				chamadas = getListaChamadasEmEspera();
			else if (getSituacaoChamadaFiltro() == SituacaoChamadaEnum.EM_CORRIDA.getCodigo())
				chamadas = getListaChamadasEmCorrida();
			else			
				chamadas = ChamadaBO.getInstance().obterChamadasFiltro(getSituacaoChamadaFiltro());
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void atualizarChamadasInicio() {
		try {
			chamadas = ChamadaBO.getInstance().obterChamadasFiltro(-1);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void limparOrigem() {
		enderecoClienteOrigem = new EnderecoCliente();
		localOrigem = null;
	}

	public void limparDestino() {
		// enderecoClienteDestino = new EnderecoCliente();
	}

	public void atualizarChamadas() {
		// try {
		// chamadas = ChamadaBO.getInstance().obterChamadasAbertas();
		chamadas = getListaChamadasEmEspera();
		/*
		 * } catch (ExcecaoNegocio e) { ExcecoesUtil.TratarExcecao(e); }
		 */
	}

	public void pesquisarClienteDialog() {
		try {
			listaClientesDialog = ClienteBO.getInstance().obterClientes(nomePesquisa, null, codPesquisa);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void detalhesChamada(Chamada chamada) {
		try {
			detalheChamadas = ChamadaVeiculoBO.getInstance().obterChamadaVeiculoPorChamada(chamada.getCodigo());
			enviarJavascript("PF('dlgMotoChamada').show();");
		} catch (ExcecaoNegocio e) {			
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void pesquisarClientePorCelular() {
		try {
		
			
			cliente = ClienteBO.getInstance().obterClientePorCelular(numCelPesquisa);
			if (cliente == null) {
				addMsg(FacesMessage.SEVERITY_WARN, "Cliente não encontrado.");
				cliente = new Cliente();
				cliente.setCelular(numCelPesquisa);
			} else
				vincularCliente(cliente);

		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		} 
	}

	public void removerChamada(Chamada chamadaRemover) {
		try {
			getListaChamadasEmEspera().removeIf(cha -> cha.getCodigo().equals(chamadaRemover.getCodigo()));
			ChamadaBO.getInstance().removerChamada(chamadaRemover);
			atualizarChamadasFiltro();
			addMsg(FacesMessage.SEVERITY_INFO, "Chamada cancelada com sucesso.");

		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void adicionarChamada() {
		try {
			if (cliente == null)
				throw new ExcecaoNegocio("Nenhum cliente selecionado");
			if (cliente.getNome() == null)
				throw new ExcecaoNegocio("Nome do cliente obrigatório");
			if (numCelPesquisa == null || numCelPesquisa.isEmpty())
				throw new ExcecaoNegocio("Número de telefone obrigatório");

			if (cliente.getCodigo() == null)
				cliente.setCelular(numCelPesquisa);
			chamada.setCliente(getCliente());

			if (chamada.getLogradouroOrigem() == null || chamada.getLogradouroOrigem().isEmpty())
				throw new ExcecaoNegocio("Informe localização de origem");

			if (chamada.getLogradouroDestino() == null || chamada.getLogradouroDestino().isEmpty())
				throw new ExcecaoNegocio("Informe localização de destino");

			/*
			 * getListaChamadasEmEspera().add(ChamadaBO.getInstance().
			 * iniciarChamada(getChamada(), getFuncionarioLogado(),
			 * listaCaracteristicasSelecionadas));
			 */
			ChamadaBO.getInstance().iniciarChamada(getChamada(), getFuncionarioLogado(),
					listaCaracteristicasSelecionadas);

		/*	String CHANELL = "/chamadas";
			EventBus eventBus = EventBusFactory.getDefault().eventBus();
			eventBus.publish(CHANELL);*/
			
			contadorLista = getListaChamadasEmEspera().size();

			mapModel.getMarkers().clear();
			limparCampos();
			atualizarChamadasFiltro();

			addMsg(FacesMessage.SEVERITY_INFO, "Chamada cadastrada.");
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			logradouroDestino = new Logradouro();
			logradouroDestino.setLogradouroComCidade(chamada.getLogradouroDestino());

			logradouro = new Logradouro();
			logradouro.setLogradouroComCidade(chamada.getLogradouroOrigem());

		}
	}

	public void limparCampos() {
		cliente = null;
		numCelPesquisa = "";
		if (enderecoClienteOrigem != null)
			enderecoClienteOrigem = new EnderecoCliente();

		enderecoClienteDestino = new Local();
		// localOrigem = null;
		chamada = new Chamada();

		logradouro = new Logradouro();
		/*
		 * if(mapModel != null && mapModel.getMarkers() != null &&
		 * mapModel.getMarkers().size() > 0) mapModel.getMarkers().clear();
		 */
	}

	public void alterarTab(TabChangeEvent event) {
		if (event.getTab().getTitle().equals("Endereços Do Cliente") && cliente != null && cliente.getCodigo() != null)
			pesquisarEnderecosCliente(cliente);
	}

	public void pesquisarLocalDialog() {
		try {
			locais = LocalBO.getInstance().obterLocal(nomeLocalPesquisa);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void pesquisarEnderecosCliente(Cliente cliente) {
		try {
			enderecosDoCliente = EnderecoClienteBO.getInstance().obterEnderecosPorCliente(cliente);
			if (enderecosDoCliente != null && enderecosDoCliente.size() > 0)
				setEnderecoClienteOrigem(enderecosDoCliente.get(0));
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void vincularCliente(Cliente cliente) {
		this.cliente = cliente;
		pesquisarEnderecosCliente(cliente);
		logradouro.setLogradouroComCidade(enderecoClienteOrigem.getLogradouro());
		chamada.setBairroOrigem(enderecoClienteOrigem.getBairro());
		chamada.setCepOrigem(enderecoClienteOrigem.getCep());
		chamada.setCidadeOrigem(enderecoClienteOrigem.getCidade());
		chamada.setComplementoOrigem(enderecoClienteOrigem.getComplemento());
		chamada.setLatitudeOrigem(enderecoClienteOrigem.getLatitude());
		chamada.setLogradouroOrigem(enderecoClienteOrigem.getLogradouro());
		chamada.setLongitudeOrigem(enderecoClienteOrigem.getLongitude());
		chamada.setNumeroOrigem(enderecoClienteOrigem.getNumero());
		ajustarMarcadorMapaChamada(chamada);
	}

	public void vincularEnderecoCliente(EnderecoCliente enderecoCliente) {
		if (isDestino) {
			chamada.setBairroDestino(enderecoCliente.getBairro());
			chamada.setCepDestino(enderecoCliente.getCep());
			chamada.setCidadeDestino(enderecoCliente.getCidade());
			chamada.setComplementoDestino(enderecoCliente.getComplemento());
			chamada.setLatitudeDestino(enderecoCliente.getLatitude());
			chamada.setLogradouroDestino(enderecoCliente.getLogradouro());
			chamada.setLongitudeDestino(enderecoCliente.getLongitude());
			chamada.setNumeroDestino(enderecoCliente.getNumero());
			logradouroDestino = new Logradouro();
			logradouroDestino.setDescricao(enderecoCliente.getLogradouro());
			logradouroDestino
					.setLogradouroComCidade(logradouroDestino.getDescricao() + " - " + chamada.getCidadeDestino());
			setDestinoFechado(false);
		} else {
			chamada.setBairroOrigem(enderecoCliente.getBairro());
			chamada.setCepOrigem(enderecoCliente.getCep());
			chamada.setCidadeOrigem(enderecoCliente.getCidade());
			chamada.setComplementoOrigem(enderecoCliente.getComplemento());
			chamada.setLatitudeOrigem(enderecoCliente.getLatitude());
			chamada.setLogradouroOrigem(enderecoCliente.getLogradouro());
			chamada.setLongitudeOrigem(enderecoCliente.getLongitude());
			chamada.setNumeroOrigem(enderecoCliente.getNumero());
			// setEnderecoClienteOrigem(enderecoCliente);

			ajustarMarcadorMapaChamada(chamada);
		}
	}

	public void vincularLocal(Local local) {
		if (isDestino) {
			// setEnderecoClienteDestino(local);
			chamada.setBairroDestino(local.getBairro());
			chamada.setCepDestino(local.getCep());
			chamada.setCidadeDestino(local.getCidade());
			chamada.setComplementoDestino(local.getComplemento());
			chamada.setLatitudeDestino(local.getLatitude());
			chamada.setLogradouroDestino(local.getLogradouro());
			chamada.setLongitudeDestino(local.getLongitude());
			chamada.setNumeroDestino(local.getNumero());
			logradouroDestino = new Logradouro();
			logradouroDestino.setDescricao(local.getLogradouro());
			logradouroDestino
					.setLogradouroComCidade(logradouroDestino.getDescricao() + " - " + chamada.getCidadeDestino());
			setDestinoFechado(false);
		} else {
			// setEnderecoClienteOrigem(converterLocalParaEnderecoCliente(local));
			chamada.setBairroOrigem(local.getBairro());
			chamada.setCepOrigem(local.getCep());
			chamada.setCidadeOrigem(local.getCidade());
			chamada.setComplementoOrigem(local.getComplemento());
			chamada.setLatitudeOrigem(local.getLatitude());
			chamada.setLogradouroOrigem(local.getLogradouro());
			chamada.setLongitudeOrigem(local.getLongitude());
			chamada.setNumeroOrigem(local.getNumero());
			// localOrigem = local;
			ajustarMarcadorMapaChamada(chamada);
			logradouro = new Logradouro();
			logradouro.setDescricao(local.getLogradouro());
			logradouro.setLogradouroComCidade(logradouro.getDescricao() + " - " + chamada.getCidadeOrigem());

		}
	}

	private void ajustarMarcadorMapa(EnderecoCliente enderecoOrigem) {

		Double lat = Double.parseDouble(enderecoOrigem.getLatitude());
		Double longt = Double.parseDouble(enderecoOrigem.getLongitude());
		Marker marker = new Marker(new org.primefaces.model.map.LatLng(lat, longt), "Origem");
		mapModel.getMarkers().clear();
		mapModel.addOverlay(marker);
		coordenadas = new LatLng(lat, longt);
	}

	private void ajustarMarcadorMapaChamada(Chamada chamadaParam) {

		Double lat = Double.parseDouble(chamadaParam.getLatitudeOrigem());
		Double longt = Double.parseDouble(chamadaParam.getLongitudeOrigem());
		Marker marker = new Marker(new org.primefaces.model.map.LatLng(lat, longt), "Origem");
		mapModel.getMarkers().clear();
		mapModel.addOverlay(marker);
		coordenadas = new LatLng(lat, longt);
	}

	public Integer getCodPesquisa() {
		return codPesquisa;
	}

	public void setCodPesquisa(Integer codPesquisa) {
		this.codPesquisa = codPesquisa;
	}

	public List<Cliente> getListaClientesDialog() {
		return listaClientesDialog;
	}

	public void setListaClientesDialog(List<Cliente> listaClientesDialog) {
		this.listaClientesDialog = listaClientesDialog;
	}

	public String getNomePesquisa() {
		return nomePesquisa;
	}

	public void setNomePesquisa(String nomePesquisa) {
		this.nomePesquisa = nomePesquisa;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<EnderecoCliente> getEnderecosDoCliente() {
		return enderecosDoCliente;
	}

	public void setEnderecosDoCliente(List<EnderecoCliente> enderecosDoCliente) {
		this.enderecosDoCliente = enderecosDoCliente;
	}

	public EnderecoCliente getEnderecoClienteOrigem() {
		return enderecoClienteOrigem;
	}

	public void setEnderecoClienteOrigem(EnderecoCliente enderecoClienteOrigem) {
		this.enderecoClienteOrigem = enderecoClienteOrigem;
	}

	public Chamada getChamada() {
		return chamada;
	}

	public void setChamada(Chamada chamada) {
		this.chamada = chamada;
	}

	public String getNomeLocalPesquisa() {
		return nomeLocalPesquisa;
	}

	public void setNomeLocalPesquisa(String nomeLocalPesquisa) {
		this.nomeLocalPesquisa = nomeLocalPesquisa;
	}

	public List<Local> getLocais() {
		return locais;
	}

	public void setLocais(List<Local> locais) {
		this.locais = locais;
	}

	private EnderecoCliente converterLocalParaEnderecoCliente(Local local) {
		EnderecoCliente enderecoTemp = new EnderecoCliente();
		enderecoTemp.setBairro(local.getBairro());
		enderecoTemp.setCep(local.getCep());
		enderecoTemp.setCidade(local.getCidade());
		enderecoTemp.setComplemento(local.getComplemento());
		enderecoTemp.setEstado(local.getEstado());
		enderecoTemp.setLogradouro(local.getLogradouro());
		enderecoTemp.setNumero(local.getNumero());
		enderecoTemp.setLatitude(local.getLatitude());
		enderecoTemp.setLongitude(local.getLongitude());
		return enderecoTemp;
	}

	public void controleAbaDestino(ToggleEvent event) {
		destinoFechado = event.getVisibility() == Visibility.HIDDEN;
	}

	public void setComponenteDestino() {
		setIsDestino(true);
		setComponenteParaUpdate(":formConsulta:fieldDestino");
	}

	public void setComponenteOrigem() {
		setIsDestino(false);
		setComponenteParaUpdate(":formConsulta:fieldOrigem");
	}

	public Local getEnderecoClienteDestino() {
		return enderecoClienteDestino;
	}

	public void setEnderecoClienteDestino(Local enderecoClienteDestino) {
		this.enderecoClienteDestino = enderecoClienteDestino;
	}

	public Boolean getIsDestino() {
		return isDestino;
	}

	public void setIsDestino(Boolean isDestino) {
		this.isDestino = isDestino;
	}

	public Local getLocalOrigem() {
		return localOrigem;
	}

	public void setLocalOrigem(Local localOrigem) {
		this.localOrigem = localOrigem;
	}

	public String getNumCelPesquisa() {
		return numCelPesquisa;
	}

	public void setNumCelPesquisa(String numCelPesquisa) {
		this.numCelPesquisa = numCelPesquisa;
	}

	public Boolean getDestinoFechado() {
		return destinoFechado;
	}

	public void setDestinoFechado(Boolean destinoFechado) {
		this.destinoFechado = destinoFechado;
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

	public List<Chamada> getChamadas() {
		return chamadas;
	}

	public void setChamadas(List<Chamada> chamadas) {
		this.chamadas = chamadas;
	}

	public Integer getSituacaoChamadaFiltro() {
		return situacaoChamadaFiltro;
	}

	public void setSituacaoChamadaFiltro(Integer situacaoChamadaFiltro) {
		this.situacaoChamadaFiltro = situacaoChamadaFiltro;
	}

	public List<Caracteristica> getListaCaracteristicasSelecionadas() {
		return listaCaracteristicasSelecionadas;
	}

	public void setListaCaracteristicasSelecionadas(List<Caracteristica> listaCaracteristicasSelecionadas) {
		this.listaCaracteristicasSelecionadas = listaCaracteristicasSelecionadas;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public String getComponenteParaUpdate() {
		return componenteParaUpdate;
	}

	public void setComponenteParaUpdate(String componenteParaUpdate) {
		this.componenteParaUpdate = componenteParaUpdate;
	}

	public EnderecoCliente getEnderecoDestinoCliente() {
		return enderecoDestinoCliente;
	}

	public void setEnderecoDestinoCliente(EnderecoCliente enderecoDestinoCliente) {
		this.enderecoDestinoCliente = enderecoDestinoCliente;
	}

	public Logradouro getLogradouroDestino() {
		return logradouroDestino;
	}

	public void setLogradouroDestino(Logradouro logradouroDestino) {
		this.logradouroDestino = logradouroDestino;
	}

	public Logradouro getLogradouroOrigemSelecionado() {
		return logradouroOrigemSelecionado;
	}

	public void setLogradouroOrigemSelecionado(Logradouro logradouroOrigemSelecionado) {
		this.logradouroOrigemSelecionado = logradouroOrigemSelecionado;
	}

	public Logradouro getLogradouroDestSelecionado() {
		return logradouroDestSelecionado;
	}

	public void setLogradouroDestSelecionado(Logradouro logradouroDestSelecionado) {
		this.logradouroDestSelecionado = logradouroDestSelecionado;
	}

	public List<Caracteristica> obterCaracteristicas() {
		return getListaCaracteristicasMemoria();
	}

	public String getTipoMarcador() {
		return tipoMarcador;
	}

	public void setTipoMarcador(String tipoMarcador) {
		this.tipoMarcador = tipoMarcador;
	}

	public String getTipoMarcadorJaAdicionado() {
		return tipoMarcadorJaAdicionado;
	}

	public void setTipoMarcadorJaAdicionado(String tipoMarcadorJaAdicionado) {
		this.tipoMarcadorJaAdicionado = tipoMarcadorJaAdicionado;
	}

	public Boolean getShowBotaoRemover() {
		return showBotaoRemover;
	}

	public void setShowBotaoRemover(Boolean showBotaoRemover) {
		this.showBotaoRemover = showBotaoRemover;
	}

	public InputText getNumeroOrigem() {
		return numeroOrigem;
	}

	public void setNumeroOrigem(InputText numeroOrigem) {
		this.numeroOrigem = numeroOrigem;
	}

	public Integer getContadorLista() {
		return contadorLista;
	}

	public void setContadorLista(Integer contadorLista) {
		this.contadorLista = contadorLista;
	}

	public List<ChamadaVeiculo> getDetalheChamadas() {
		return detalheChamadas;
	}

	public void setDetalheChamadas(List<ChamadaVeiculo> detalheChamadas) {
		this.detalheChamadas = detalheChamadas;
	}

}
