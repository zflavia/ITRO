package ro.uvt.fmi.itro.ejb.user;

import java.util.List;

import javax.ejb.Remote;

import ro.uvt.fmi.persistenta.user.User;
import ro.uvt.fmi.persistenta.user.UserToShort;

@Remote
public interface UserRemote {
	

	public User getUserById(Long id);

	public User insertUser(User u, String role);

	public void updateUser(User u);

	public void deleteUser(Long id);
	
	public List<User> getAllActiveUsers();
	
	public List<UserToShort> getAll();

}