package br.com.motorapido.util;

import java.util.ArrayList;

public class RetornoGoogleWSCoordenadas {

	private ArrayList<Resultado> results;

	public class Resultado {

		private ArrayList<ComponentesEndereco> address_components;

		public Resultado() {

		}

		public ArrayList<ComponentesEndereco> getAddress_components() {
			return address_components;
		}

		public void setAddress_components(ArrayList<ComponentesEndereco> address_components) {
			this.address_components = address_components;
		}

		
	}

	public class ComponentesEndereco {
		private String long_name;
		private String short_name;
		private String[] types;

		ComponentesEndereco() {
		};

		public String getLong_name() {
			return long_name;
		}

		public void setLong_name(String long_name) {
			this.long_name = long_name;
		}

		public String getShort_name() {
			return short_name;
		}

		public void setShort_name(String short_name) {
			this.short_name = short_name;
		}

		public String[] getTypes() {
			return types;
		}

		public void setTypes(String[] types) {
			this.types = types;
		}
	}

	public ArrayList<Resultado> getResults() {
		return results;
	}

	public void setResults(ArrayList<Resultado> results) {
		this.results = results;
	}

}
