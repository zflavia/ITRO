package ro.uvt.fmi.itro.ejb.localitate;

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

import ro.uvt.fmi.persistenta.localitate.Localitate;

@Local
@Path("/localitate")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface LocalitateREST {

	@GET
	@Path("/localitati")
	public List<Localitate> getAll();

	@POST
	@Path("/mng")
	public Response insertLocalitate(Localitate localitate);

	@PUT
	@Path("/mng")
	public Response updateLocalitate(Localitate localitate);

	@DELETE
	@Path("/mng/{id}")
	public Response deleteLocalitate(@PathParam("id") Long id);

	@GET
	@Path("/mng/{id}")
	public Localitate getById(@PathParam("id") Long id);
}
