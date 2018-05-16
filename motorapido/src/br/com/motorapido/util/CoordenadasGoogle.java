package br.com.motorapido.util;

import java.util.ArrayList;

public class CoordenadasGoogle {

	private ArrayList<Resultado> results;
	
	public CoordenadasGoogle() {

	}

	public ArrayList<Resultado> getResults() {
		return results;
	}

	public void setResults(ArrayList<Resultado> results) {
		this.results = results;
	}

	public class Resultado {
		
		private Geometria geometry;
		
		public Resultado() {

		}

		public Geometria getGeometry() {
			return geometry;
		}

		public void setGeometry(Geometria geometry) {
			this.geometry = geometry;
		}
	}

	public class Geometria {

		private Localizacao location;

		public Geometria() {

		}

		public Localizacao getLocation() {
			return location;
		}

		public void setLocation(Localizacao location) {
			this.location = location;
		}

	}

	public class Localizacao {

		private double lat;
		private double lng;

		public Localizacao() {

		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLng() {
			return lng;
		}

		public void setLng(double lng) {
			this.lng = lng;
		}

	}
}
