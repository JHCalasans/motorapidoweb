package br.com.motorapido.util;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.map.LatLng;

import br.com.motorapido.entity.Area;

public class CoordenadasAreaUtil {
	
	
	private List<LatLng> coordenadas;
	
	private Area area;
	
		

	public CoordenadasAreaUtil() {
	
		coordenadas = new ArrayList<LatLng>();
	}

	public List<LatLng> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<LatLng> coordenadas) {
		this.coordenadas = coordenadas;
	}


	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

}
