package br.com.motorapido.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polygon;
import org.primefaces.model.map.Polyline;

import com.google.maps.model.LatLng;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.AreaBO;
import br.com.motorapido.entity.Area;
import br.com.motorapido.entity.CoordenadasArea;
import br.com.motorapido.util.CoordenadasAreaUtil;
import br.com.motorapido.util.ExcecoesUtil;

@SuppressWarnings("deprecation")
@ManagedBean(name = "areaBean")
@ViewScoped
public class AreaBean extends SimpleController {

	private static final long serialVersionUID = 7692603107809116052L;

	private MapModel mapModel;

	private LatLng coordenadas;

	private List<org.primefaces.model.map.LatLng> marcadores;

	private org.primefaces.model.map.LatLng primeiroMarcador;

	private Area area;
	
	private Area areaAlterar;
	
	private Area areaExcluir;

	private Boolean mostrarMapa = false;

	private List<CoordenadasArea> areasCadastradas;
	
	private List<CoordenadasAreaUtil> listaArea;

	@PostConstruct
	public void carregar() {
		if (getFacesContext().isPostback()) {
			return;
		}
		try {
			area = new Area();
			area.setCor("#0000F0");
			// mapModel = new DefaultMapModel();
			// coordenadas = new LatLng(-10.9536484, -37.0437752);
			
			
			montarAreas();

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void mudarMapa() {
		mostrarMapa = !mostrarMapa;
	}
	
	public void abrirAlterar(Area param){
		areaAlterar = param;
		enviarJavascript("PF('dlgAlterarArea').show();");
	}
	
	public void abrirExcluir(Area param){
		areaExcluir = param;
		enviarJavascript("PF('dlConfirmDelete').show();");
	}
	
	public void alterarArea(){
		try {
			areaAlterar.setCor("#" + areaAlterar.getCorPura());
			AreaBO.getInstance().alterarArea(areaAlterar);			
			montarAreas();
			addMsg(FacesMessage.SEVERITY_INFO, "Área alterada com sucesso.");
			
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	
	public void excluirArea(){
		try {
			AreaBO.getInstance().excluirArea(areaExcluir);			
			montarAreas();
			addMsg(FacesMessage.SEVERITY_INFO, "Área excluída com sucesso.");
			areaExcluir = null;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	private void montarAreas() {
		try {
			mapModel = new DefaultMapModel();
			coordenadas = new LatLng(-10.938604, -37.064269);
			listaArea = AreaBO.getInstance().obterAreas();
			primeiroMarcador = null;
			marcadores = new ArrayList<org.primefaces.model.map.LatLng>();
			for (CoordenadasAreaUtil coordenada : listaArea) {
				Polygon poligono = new Polygon();
				poligono.setStrokeColor("#000000");
				poligono.setFillColor(coordenada.getArea().getCor());
				poligono.setStrokeOpacity(0.9);
				poligono.setFillOpacity(0.2);
				poligono.setPaths(coordenada.getCoordenadas());				
				mapModel.addOverlay(poligono);
			}
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}

	}

	public void marcadorSelecionado(OverlaySelectEvent event) {

		try {
			Marker marker = (Marker) event.getOverlay();
			if (primeiroMarcador == marker.getLatlng() && marcadores.size() > 2) {
				enviarJavascript("PF('dlgArea').show();");

			} else {
				int i = marcadores.size() - 1;
				int indice = marcadores.indexOf(marker.getLatlng());
				while (i >= indice) {
					mapModel.getMarkers().remove(i);
					marcadores.remove(i);
					if (i > 0) {
						mapModel.getPolylines().remove(i - 1);
					}
					i--;
				}

			}
		} catch (Exception e) {
//			Polygon poligono = (Polygon) event.getOverlay();
//			System.out.println(poligono.getFillColor());
		}

	}

	public void salvarArea() {
		try {
			area.setCor("#" + area.getCor());
			AreaBO.getInstance().salvarArea(marcadores, area);
			montarAreas();
			
			addMsg(FacesMessage.SEVERITY_INFO, "Área cadastrado com sucesso.");
			area = new Area();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void addMarker(PointSelectEvent event) {

		
		try {
			Marker marker = new Marker(event.getLatLng(), "Marcador - " + marcadores.size());
			coordenadas.lat = marker.getLatlng().getLat();
			coordenadas.lng = marker.getLatlng().getLng();

			if (marcadores.size() == 0)
				primeiroMarcador = marker.getLatlng();

			if (marcadores.size() > 0) {
				Polyline linha = new Polyline();
				linha.getPaths().add(marcadores.get(marcadores.size() - 1));
				linha.getPaths().add(marker.getLatlng());
				linha.setStrokeWeight(2);
				linha.setStrokeColor("#000000");
				linha.setStrokeOpacity(0.2);
				mapModel.addOverlay(linha);

			}

			marcadores.add(marker.getLatlng());

			mapModel.addOverlay(marker);
		} catch (Exception e) {
		}

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

	public List<org.primefaces.model.map.LatLng> getMarcadores() {
		return marcadores;
	}

	public void setMarcadores(List<org.primefaces.model.map.LatLng> marcadores) {
		this.marcadores = marcadores;
	}

	public org.primefaces.model.map.LatLng getPrimeiroMarcador() {
		return primeiroMarcador;
	}

	public void setPrimeiroMarcador(org.primefaces.model.map.LatLng primeiroMarcador) {
		this.primeiroMarcador = primeiroMarcador;
	}

	@Override
	public String salvoSucesso() {
		// TODO Auto-generated method stub
		return null;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Boolean getMostrarMapa() {
		return mostrarMapa;
	}

	public void setMostrarMapa(Boolean mostrarMapa) {
		this.mostrarMapa = mostrarMapa;
	}

	public List<CoordenadasArea> getAreasCadastradas() {
		return areasCadastradas;
	}

	public void setAreasCadastradas(List<CoordenadasArea> areasCadastradas) {
		this.areasCadastradas = areasCadastradas;
	}

	public List<CoordenadasAreaUtil> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<CoordenadasAreaUtil> listaArea) {
		this.listaArea = listaArea;
	}

	public Area getAreaAlterar() {
		return areaAlterar;
	}

	public void setAreaAlterar(Area areaAlterar) {
		this.areaAlterar = areaAlterar;
	}

	public Area getAreaExcluir() {
		return areaExcluir;
	}

	public void setAreaExcluir(Area areaExcluir) {
		this.areaExcluir = areaExcluir;
	}

	

}
