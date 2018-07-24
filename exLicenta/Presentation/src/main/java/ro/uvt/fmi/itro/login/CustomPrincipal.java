package ro.uvt.fmi.itro.login;

import org.jboss.security.SimplePrincipal;

public class CustomPrincipal extends SimplePrincipal {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;

	public CustomPrincipal(String name, String description) {
		super(name);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
