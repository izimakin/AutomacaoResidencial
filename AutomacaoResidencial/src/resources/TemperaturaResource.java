package resources;

import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import models.Temperatura;

@Path("temperatura")
public class TemperaturaResource {
	private Temperatura temperatura;
	
	TemperaturaResource () {
		temperatura = Temperatura.getInstance();
	}
	
	@GET
	@Produces("application/json")
	public Response getAll() {
		Iterator<String> it = temperatura.iterator();
		String jsonResponse = "";
		if (it.hasNext()) {
			String nome = it.next();
			jsonResponse += "{nome : " + nome + ", temperatura : " + temperatura.getTemperatura(nome) +"}";
		}
		while (it.hasNext()) {
			String nome = it.next();
			jsonResponse += ",{nome : " + nome + ", temperatura : " + temperatura.getTemperatura(nome) +"}";
		}
		return Response.status(200).entity("[" + jsonResponse + "]").build();
	}
	
	
	@GET
	@Path("/{nome}")
	@Produces("application/json")
	public Response getTemperatura(@PathParam("nome") String nome) {
		return Response.status(200).entity("{phone : " + temperatura.getTemperatura(nome) + "}").build();
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public void createSensor(@FormParam("nome") String nome,
			@FormParam("temperatura") Double temperatura) {
		this.temperatura.createSensor(nome, temperatura);
	}
	
	@PUT
	@Path("/{nome}")
	@Consumes("application/x-www-form-urlencoded")
	public void updateSensor(@FormParam("nome") String nome,
			@FormParam("temperatura") Double temperatura) {
		this.temperatura.updateSensor(nome, temperatura);
	}
	
	@DELETE
	@Path("/{nome}")
	public void deleteSensor(@PathParam("nome") String nome) {
		temperatura.deleteSensor(nome);
	}
}