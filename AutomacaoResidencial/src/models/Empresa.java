package models;

import java.util.HashMap;
import java.util.Iterator;

public class Empresa {
	private static Empresa instance;
	
	// A chave é o nome da empresa e o resultado é a temperatura
	private HashMap<String, String> empresas;

	public Empresa() {
		empresas = new HashMap<String, String>();
	}
	
	public static Empresa getInstance() {
		if (instance == null) {
			instance = new Empresa();
		}
		return instance;
	}
	
	public void createEmpresa(String nome, String temperatura) {
		empresas.put(nome, temperatura);
	}

	public String getTemperatura(String nome) {
		return empresas.get(nome);
	}
	
	public void updateEmpresa(String nome, String temperatura) {
		empresas.put(nome, temperatura);
	}
	
	public void deleteEmpresa(String nome) {
		empresas.remove(nome);
	}
	
	public Iterator<String> iterator() {
		return empresas.keySet().iterator();
	}
}
