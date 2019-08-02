package br.com.motorapido.util;

import com.google.maps.model.LatLng;

public class MotoristaPontoMapa {

	private Integer codMotorista;
	
	private LatLng coordenadas;
	
	public Integer getCodMotorista() {
		return codMotorista;
	}

	public void setCodMotorista(Integer codMotorista) {
		this.codMotorista = codMotorista;
	}

	public LatLng getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(LatLng coordenadas) {
		this.coordenadas = coordenadas;
	}

	public MotoristaPontoMapa() {}
	
	public MotoristaPontoMapa(Integer codMoto, LatLng coord) 
	{
		this.codMotorista = codMoto;
		this.coordenadas = coord;
	}
	
	
	
}
