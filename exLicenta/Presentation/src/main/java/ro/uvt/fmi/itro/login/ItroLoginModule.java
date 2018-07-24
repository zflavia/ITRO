package ro.uvt.fmi.itro.login;

import java.security.Principal;

import javax.security.auth.login.LoginException;

import org.jboss.security.auth.spi.DatabaseServerLoginModule;

public class ItroLoginModule extends DatabaseServerLoginModule {
	private CustomPrincipal principal;

	@Override
	public boolean login() throws LoginException {
		boolean login = super.login();
		if (login) {
			principal = new CustomPrincipal(getUsername(), "An user description!");
		}
		System.out.println("ItroLoginModule login: " + login);
		return login;
	}

	@Override
	protected Principal getIdentity() {
		return principal != null ? principal : super.getIdentity();
	}

}
