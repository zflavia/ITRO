package ro.uvt.fmi.itro.ejb.user;

import javax.ejb.Remote;

import ro.uvt.fmi.persistenta.user.User;

@Remote
public interface SessionUserRemote {
	public String getPrincipal();

	public User getUser();
	
}
