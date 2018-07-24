package ro.uvt.fmi.itro.ejb.limba;



import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ro.uvt.fmi.itro.ejb.Utils;
import ro.uvt.fmi.itro.ejb.utils.ValidatorUtil;
import ro.uvt.fmi.persistenta.limba.Limba;

@Stateless
@Remote(LimbaRemote.class)
@Local(LimbaREST.class)
public class LimbaEjb implements LimbaRemote, LimbaREST {

	@PersistenceContext(unitName = "itroDS")
	private EntityManager em;

	@Override
	public List<Limba> getAll() {

		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Limba.QUERY_ALL);

		return (List<Limba>) query.list();

	}

	public Limba getById(Long id) {
		Session session = (Session) em.getDelegate();
		return (Limba) session.get(Limba.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Limba insert(Limba l) throws EJBException {
		Session session = (Session) em.getDelegate();

		validate(l);

		session.persist(l);
		session.refresh(l);
		return l;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Limba update(Limba l) throws EJBException {
		Session session = (Session) em.getDelegate();
		validate(l);

		session.merge(l);
		return l;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Long id) {
		Session session = (Session) em.getDelegate();

		session.delete(getById(id));
	}

	@Override
	public int queryByName(String name) {
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Limba.QUERY_BY_NAME);
		query.setString("nume", name);
		return query.list().size();

	}

	private void validate(Limba l) throws EJBException {
		try {
			new ValidatorUtil().validate(l);
		} catch (ConstraintViolationException ex) {
			throw new EJBException(ex.getMessage());
		}
		if (queryByName(l.getNume()) > 0) {
			throw new EJBException("Duplicate language name");
		}
	}

	@Override
	public Response insertLimba(Limba limba) {
		try {
			limba = this.insert(limba);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(limba).build();

	}

	@Override
	public Response deleteLimba(Long id) {
		try {
			this.delete(id);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}

	@Override
	public Response updateLimba(Limba limba) {
		try {
			this.update(limba);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(limba).build();
	}

}
