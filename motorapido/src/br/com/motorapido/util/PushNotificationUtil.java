package br.com.motorapido.util;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;

public class PushNotificationUtil {

		public static void enviarNotificacaoPlayerIdChamada(String authorization, String appId,
				List<String> playersIds, String msg, String codChamadaVeiculo, String tempoEsperaAceitacao) throws ExcecaoNegocio {

			if (playersIds == null || playersIds.size() < 1)
				throw new ExcecaoNegocio("Players Ids não podem estar vazios");

			HttpResponse response = null;
			try {


				String finalPleyrsIds = "";
				for (int i = 0; i < playersIds.size(); i++) {
					if (i > 0)
						finalPleyrsIds += ",\"" + playersIds.get(i) + "\"";
					else
						finalPleyrsIds += "\"" + playersIds.get(i) + "\"";
				}
				
			
				String strJsonBody = "{" + "\"app_id\": \"" + appId + "\","
						+ "\"include_player_ids\": [" + finalPleyrsIds + "],"
						+ "\"data\": {\"codChamadaVeiculo\": " + codChamadaVeiculo +", \"tempoEsperaAceitacao\": " + tempoEsperaAceitacao +"},"
						+ "\"contents\": {\"en\": \"" + msg + "\"}"
						+ "}";

				HttpClient httpClient = HttpClients.custom().build();
				HttpUriRequest request = RequestBuilder.post()
						.setUri("https://onesignal.com/api/v1/notifications")
						.setHeader("Content-Type", "application/json; charset=UTF-8")
						.setHeader("Authorization", "Basic " + authorization)
						.setHeader("accept", "application/json")
						.setEntity(new StringEntity(strJsonBody,"UTF-8")).build();

				response = httpClient.execute(request);
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				

			} catch (Exception t) {
//				try {
//					ExcecoesUtil.TratarExcecao(
//							new ExcecaoNegocio(response.getEntity().getContent().toString()));
//				} catch (UnsupportedOperationException | IOException e) {
//					ExcecoesUtil.TratarExcecao(e);
//				}
			}
		}

		
		public static void enviarNotificacaoPlayerId(String authorization, String appId,
				List<String> playersIds, String msg, String data, String valorData) throws ExcecaoNegocio {

			if (playersIds == null || playersIds.size() < 1)
				throw new ExcecaoNegocio("Players Ids não podem estar vazios");

			HttpResponse response = null;
			try {


				String finalPleyrsIds = "";
				for (int i = 0; i < playersIds.size(); i++) {
					if (i > 0)
						finalPleyrsIds += ",\"" + playersIds.get(i) + "\"";
					else
						finalPleyrsIds += "\"" + playersIds.get(i) + "\"";
				}

				String strJsonBody = "{" + "\"app_id\": \"" + appId + "\","
						+ "\"include_player_ids\": [" + finalPleyrsIds + "],"
						+ "\"data\": {\""+data+"\": \""+valorData+"\"}," + "\"contents\": {\"en\": \"" + msg + "\"}"
						+ "}";

				HttpClient httpClient = HttpClients.custom().build();
				HttpUriRequest request = RequestBuilder.post()
						.setUri("https://onesignal.com/api/v1/notifications")
						.setHeader("Content-Type", "application/json; charset=UTF-8")
						.setHeader("Authorization", "Basic " + authorization)
						.setHeader("accept", "application/json")
						.setEntity(new StringEntity(strJsonBody,"UTF-8")).build();

				response = httpClient.execute(request);
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				

			} catch (Exception t) {
//				try {
//					ExcecoesUtil.TratarExcecao(
//							new ExcecaoNegocio(response.getEntity().getContent().toString()));
//				} catch (UnsupportedOperationException | IOException e) {
//					ExcecoesUtil.TratarExcecao(e);
//				}
			}
		}

}
