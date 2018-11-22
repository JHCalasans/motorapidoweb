package br.com.motorapido.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.enums.ParametroEnum;

public class GoogleWSUtil {

	public static RetornoGoogleWSCoordenadas buscarCoordenadas(String coordenadas) throws ExcecaoNegocio {

		try {
			HttpClient httpClient = HttpClients.custom().build();

			// Buscando coordenadas pelo cep passado
			HttpUriRequest requestCoordenadas = RequestBuilder.get()
					.setUri("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + coordenadas
							+ "&key=" + FuncoesUtil.getParam(ParametroEnum.CHAVE_MAPS.getCodigo()))
					.setHeader("accept", "application/json").build();

		

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
			RetornoGoogleWSCoordenadas coordGoogle = gson.fromJson(json, RetornoGoogleWSCoordenadas.class);
		
			return coordGoogle;
			/*
			 * this.getLocal()
			 * .setLatitude(String.valueOf(coordGoogle.getResults().get(0).
			 * getGeometry().getLocation().getLat())); this.getLocal()
			 * .setLongitude(String.valueOf(coordGoogle.getResults().get(0).
			 * getGeometry().getLocation().getLng()));
			 */

		} catch (Exception e) {
			throw new ExcecaoNegocio("Erro ao buscar coordenadas do mapa pelo CEP!");
		}

	}

}
