package ro.uvt.fmi.itro.ejb.user;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ro.uvt.fmi.persistenta.user.User;
import ro.uvt.fmi.persistenta.user.UserToShort;

@Local
@Path("/user")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface UserREST {
	@GET
	@Path("/culegatori")
	public Collection<User> getAllActiveAndScieUsers();
	
	public User getUserByUsername(final String username);

	List<UserToShort> getAll();

}
