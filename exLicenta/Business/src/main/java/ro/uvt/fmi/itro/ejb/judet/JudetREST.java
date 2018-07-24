package ro.uvt.fmi.itro.ejb.judet;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ro.uvt.fmi.persistenta.judet.Judet;

@Local
@Path("/judet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface JudetREST {

	@GET
	@Path("/judete")
	public List<Judet> getAll();
	
	@GET
	@Path("/judete/{id}")
	public List<Judet> getByCountry(@PathParam("id") Long id);

	@POST
	@Path("/mng")
	public Response insertJudet(Judet judet);

	@PUT
	@Path("/mng")
	public Response updateJudet(Judet judet);

	@DELETE
	@Path("/mng/{id}")
	public Response deleteJudet(@PathParam("id") Long id);

	@GET
	@Path("/mng/{id}")
	public Judet getById(@PathParam("id") Long id);
}
