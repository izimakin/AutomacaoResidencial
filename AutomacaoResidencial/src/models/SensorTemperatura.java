package models;

import org.json.JSONException;
import org.json.JSONObject;

public class SensorTemperatura {
	private String nome = null;
	private Double temperatura = null;
	
	public SensorTemperatura() {
	}
	
	public SensorTemperatura(String nome, Double temperatura) {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}
	
	public static SensorTemperatura getObjectFromJSON(JSONObject json) {
		try {
			return new SensorTemperatura(json.getString("nome"), json.getDouble("temperatura"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
