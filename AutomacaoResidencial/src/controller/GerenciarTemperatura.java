package controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import models.SensorTemperatura;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.RestfulWSClient;

public class GerenciarTemperatura {
	String baseURL = "http://localhost:8080/AutomacaoResidencial/resources/temperatura";
	private Double temperaturaDesejada;
	RestfulWSClient cliente;
	
	public GerenciarTemperatura(Double temperatura) {
		temperaturaDesejada = temperatura;
		cliente = new RestfulWSClient();
		adicionarSensores();
	}
	
	private void adicionarSensores() {
		try {
			cliente.invokeOperation(baseURL, "POST", "nome=Danfoss&temperatura=26.0", "application/x-www-form-urlencoded");
			cliente.invokeOperation(baseURL, "POST", "nome=Benq&temperatura=20.0", "application/x-www-form-urlencoded");
			cliente.invokeOperation(baseURL, "POST", "nome=Samsung&temperatura=18.4", "application/x-www-form-urlencoded");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Double getTemperatura(String nome) {
		try {
			String jsonResult = cliente.invokeOperation(baseURL + "/" + nome, "GET", "", "application/x-www-form-urlencoded");
			JSONObject json = new JSONObject(jsonResult);
			return json.getDouble("temperatura");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<SensorTemperatura> getAllSensorTemperatura() {
		try {
			String jsonResult = cliente.invokeOperation(baseURL, "GET", "", "application/x-www-form-urlencoded");
			JSONArray jsonArray = new JSONArray(jsonResult);
			ArrayList<SensorTemperatura> sensores = new ArrayList<SensorTemperatura>();
			for (int i=0; i < jsonArray.length(); ++i)
				sensores.add(SensorTemperatura.getObjectFromJSON(jsonArray.getJSONObject(i)));
			return sensores; 
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void printRelatorio() {
		ArrayList<SensorTemperatura> sensores = getAllSensorTemperatura();
		System.out.println("------Temperatura-----");
		System.out.println("\tTemperatura desejada: " + temperaturaDesejada);
		Double soma = 0.0;
		for (int i=0; i < sensores.size(); ++i) {
			soma += sensores.get(i).getTemperatura();
			System.out.println("\t" + sensores.get(i).getNome() + ": " + sensores.get(i).getTemperatura());
		}
		Double media = soma / sensores.size();
		System.out.println("\tA média das temperaturas é " + media);
		if (temperaturaDesejada > media)
			System.out.println("\tO radiador tem que aumentar a temperatura!");
		else if (temperaturaDesejada < media)
			System.out.println("\tO radiador tem que diminuir a temperatura!");
		else
			System.out.println("\tTemperatura Ideal!");
	}
}
