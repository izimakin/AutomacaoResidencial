package models;

import java.util.HashMap;
import java.util.Iterator;

public class Temperatura {
	private static Temperatura instance;
	
	// A chave é o nome da empresa e o resultado é a temperatura
	private HashMap<String, Double> sensores;

	public Temperatura() {
		sensores = new HashMap<String, Double>();
	}
	
	public static Temperatura getInstance() {
		if (instance == null) {
			instance = new Temperatura();
		}
		return instance;
	}
	
	public void createSensor(String nome, Double temperatura) {
		sensores.put(nome, temperatura);
	}

	public Double getTemperatura(String nome) {
		return sensores.get(nome);
	}
	
	public void updateSensor(String nome, Double temperatura) {
		sensores.put(nome, temperatura);
	}
	
	public void deleteSensor(String nome) {
		sensores.remove(nome);
	}
	
	public Iterator<String> iterator() {
		return sensores.keySet().iterator();
	}
}
