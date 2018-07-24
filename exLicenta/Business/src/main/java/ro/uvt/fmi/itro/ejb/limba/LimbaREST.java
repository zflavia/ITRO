package ro.uvt.fmi.itro.ejb.limba;

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

import ro.uvt.fmi.itro.ejb.limba.LimbaRemote;
import ro.uvt.fmi.persistenta.limba.Limba;

@Local
@Path("/limba")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
// @RequestScoped
public interface LimbaREST {

	@GET
	@Path("/limba")
	public List<Limba> getAll();
	
	@POST
	@Path("/mng")
	public Response insertLimba(Limba limba);
	
	@PUT
	@Path("/mng")
	public Response updateLimba(Limba limba);
	
	@DELETE
	@Path("/mng/{id}")
	public Response deleteLimba(@PathParam("id") Long id);
	
	@GET
	@Path("/mng/{id}")
	public Limba getById(@PathParam("id") Long id);
}
