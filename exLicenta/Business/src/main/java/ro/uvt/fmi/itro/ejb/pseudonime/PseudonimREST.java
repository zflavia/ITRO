package ro.uvt.fmi.itro.ejb.pseudonime;
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
import ro.uvt.fmi.itro.ejb.pseudonime.*;
import ro.uvt.fmi.persistenta.pseudonim.Pseudonim;

@Local
@Path("/pseudonim")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PseudonimREST {

	@GET
	@Path("/pseudonime/{id}")
	public Pseudonim getByIdPseudonim(@PathParam("id") Long id);
	
	@GET
	@Path("/pseudonime/")
	public List<Pseudonim> getAll();
	
	@PUT
	@Path("/pseudonime/")
	public Response insertPseudonim(Pseudonim entry);
	
	@POST
	@Path("/pseudonime/")
	public Response updatePseudonim(Pseudonim entry);
	
	@DELETE
	@Path("/pseudonime/{id}")
	public Response deletePseudonim(@PathParam("id") Long id);
}
