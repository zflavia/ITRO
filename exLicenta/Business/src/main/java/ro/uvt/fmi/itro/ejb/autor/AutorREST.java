package ro.uvt.fmi.itro.ejb.autor;

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

import ro.uvt.fmi.persistenta.autor.Autor;

@Local
@Path("/autor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AutorREST {

	@GET
	@Path("/autori")
	public List<Autor> getAll();
	
	@POST
	@Path("/mng")
	public Response insertAutor(Autor autor);
	
	@PUT
	@Path("/mng")
	public Response updateAutor(Autor autor);
	
	@DELETE
	@Path("/mng/{id}")
	public Response deleteAutor(@PathParam("id") Long id);
	
	@GET
	@Path("/mng/{id}")
	public Autor getById(@PathParam("id") Long id);
}

