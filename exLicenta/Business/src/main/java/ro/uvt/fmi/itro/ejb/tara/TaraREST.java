package ro.uvt.fmi.itro.ejb.tara;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
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

import ro.uvt.fmi.itro.ejb.security.JWTTokenNeeded;
import ro.uvt.fmi.itro.ejb.tara.TaraRemote;
import ro.uvt.fmi.persistenta.tara.Tara;

@Local
@Path("/tara")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
// @RequestScoped
public interface TaraREST {

	@GET
	@Path("/tari")
	@JWTTokenNeeded
	public List<Tara> getAll();
	
	@POST
	@Path("/mng")
	@JWTTokenNeeded
	public Response insertTara(Tara tara);
	
	@PUT
	@Path("/mng")
	@JWTTokenNeeded
	public Response updateTara(Tara tara);
	
	@DELETE
	@Path("/mng/{id}")
	@JWTTokenNeeded
	public Response deleteTara(@PathParam("id") Long id);
	
	@GET
	@Path("/mng/{id}")
	@JWTTokenNeeded
	public Tara getById(@PathParam("id") Long id);
}
