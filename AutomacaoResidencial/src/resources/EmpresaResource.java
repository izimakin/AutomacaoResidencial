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

import models.Empresa;

@Path("empresa")
public class EmpresaResource {
	private Empresa empresa;
	
	EmpresaResource () {
		empresa = Empresa.getInstance();
	}
	
	@GET
	@Produces("application/json")
	public Response getAll() {
		Iterator<String> it = empresa.iterator();
		String jsonResponse = "";
		while (it.hasNext()) {
			String nome = it.next();
			jsonResponse += "{nome : " + nome + ", temperatura : " + empresa.getTemperatura(nome) +"}";
		}
		return Response.status(200).entity("[" + jsonResponse + "]").build();
	}
	
	
	@GET
	@Path("/{nome}")
	@Produces("application/json")
	public Response getTemperatura(@PathParam("nome") String nome) {
		return Response.status(200).entity("{phone : " + empresa.getTemperatura(nome) + "}").build();
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public void createEmpresa(@FormParam("nome") String nome,
			@FormParam("temperatura") String temperatura) {
		empresa.createEmpresa(nome, temperatura);
	}
	
	@PUT
	@Consumes("application/x-www-form-urlencoded")
	public void updateEmpresa(@FormParam("nome") String nome,
			@FormParam("temperatura") String temperatura) {
		empresa.updateEmpresa(nome, temperatura);
	}
	
	@DELETE
	@Path("/nome")
	public void deleteEmpresa(@PathParam("nome") String nome) {
		empresa.deleteEmpresa(nome);
	}
}