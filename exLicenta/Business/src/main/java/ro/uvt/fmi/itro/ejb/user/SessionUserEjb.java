package ro.uvt.fmi.itro.ejb.user;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;

import ro.uvt.fmi.persistenta.user.User;

@Stateless
public class SessionUserEjb implements SessionUserRemote {
	@Resource
	private EJBContext context;

	@EJB
	UserREST mngUser;

	@Override
	public String getPrincipal() {
		// log.info("APPU::Session bean called ");
		Principal pp = context.getCallerPrincipal();

		System.out.println("getPrincipal::User Name = " + pp.getName());
		System.out.println("getPrincipal::User Name = " + pp.getName());
		// log.info("APPU::User Name = " + pp.getName());
		return (String) (context.getCallerPrincipal().getName());
	}

	@Override
	public User getUser() {
		Principal pp = context.getCallerPrincipal();
		System.out.println("getUser::User Name = " + pp.getName());
		return mngUser.getUserByUsername(pp.getName());

	}

}


