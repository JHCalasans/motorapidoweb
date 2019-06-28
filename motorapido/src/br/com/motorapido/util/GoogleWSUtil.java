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
import br.com.motorapido.entity.Chamada;
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
			

		} catch (Exception e) {
			throw new ExcecaoNegocio("Erro ao buscar coordenadas do mapa pelo CEP!");
		}

	}
	
	public static RetornoMatrixGoogleAPI buscarDistanciaAPercorrer(String origem, String destino, String chave) throws ExcecaoNegocio{

		try {
			HttpClient httpClient = HttpClients.custom().build();

			// Buscando distância e tempo entre a origem e destino passados
			HttpUriRequest requestCoordenadas = RequestBuilder.get()
					.setUri("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origem
							+ "&destinations=" + destino + "&key=" + chave)
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
			RetornoMatrixGoogleAPI matrixGoogle = gson.fromJson(json, RetornoMatrixGoogleAPI.class);
		
			return matrixGoogle;
			

		} catch (Exception e) {
			throw new ExcecaoNegocio("Erro ao buscar coordenadas do mapa pelo CEP!");
		}
	}
	
	public static String buscarRota(Chamada chamada) throws ExcecaoNegocio{
		try {
			HttpClient httpClient = HttpClients.custom().build();

			// Buscando distância e tempo entre a origem e destino passados
			HttpUriRequest requestCoordenadas = RequestBuilder.get()
					.setUri("https://maps.googleapis.com/maps/api/directions/json?origin=" + chamada.getLatitudeOrigem()
							+ ","+ chamada.getLongitudeOrigem() +"&destination=" + chamada.getLatitudeDestino() +
							","+chamada.getLongitudeDestino() + "&alternatives=false&key=" + FuncoesUtil.getParam(ParametroEnum.CHAVE_MAPS.getCodigo()))
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
			GoogleDirection googleDirection = gson.fromJson(json, GoogleDirection.class);
		
			return googleDirection.getRoutes()[0].getOverview_polyline().getPoints();
			

		} catch (Exception e) {
			throw new ExcecaoNegocio("Erro ao buscar rotas no mapa");
		}
	}
	
	public static String buscarRota(String latOrigem, String longOrigem, String latDestino, String longDestino) throws ExcecaoNegocio{
		try {
			HttpClient httpClient = HttpClients.custom().build();

			// Buscando distância e tempo entre a origem e destino passados
			HttpUriRequest requestCoordenadas = RequestBuilder.get()
					.setUri("https://maps.googleapis.com/maps/api/directions/json?origin=" + latOrigem
							+ ","+ longOrigem +"&destination=" + latDestino +
							","+longDestino + "&alternatives=false&key=" + FuncoesUtil.getParam(ParametroEnum.CHAVE_MAPS.getCodigo()))
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
			GoogleDirection googleDirection = gson.fromJson(json, GoogleDirection.class);
		
			return googleDirection.getRoutes()[0].getOverview_polyline().getPoints();
			

		} catch (Exception e) {
			throw new ExcecaoNegocio("Erro ao buscar rotas no mapa");
		}
	}

}
