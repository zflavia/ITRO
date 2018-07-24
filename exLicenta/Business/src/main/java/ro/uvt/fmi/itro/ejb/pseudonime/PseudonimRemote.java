package ro.uvt.fmi.itro.ejb.pseudonime;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import ro.uvt.fmi.persistenta.pseudonim.*;
@Remote
public interface PseudonimRemote {
	
	public List<Pseudonim> getAll();
	
	public Pseudonim getByIdPseudonim(@PathParam("id") Long id);
	
	public Response insertPseudonim(Pseudonim entry);
	
	public Response updatePseudonim(Pseudonim entry);
	
	public Response deletePseudonim(@PathParam("id") Long id);
	
}
