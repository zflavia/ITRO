package ro.uvt.fmi.itro.ejb.tara;

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
import ro.uvt.fmi.persistenta.judet.Judet;
import ro.uvt.fmi.persistenta.localitate.Localitate;
import ro.uvt.fmi.persistenta.tara.Tara;

@Stateless
@Remote(TaraRemote.class)
@Local(TaraREST.class)
public class TaraEjb implements TaraRemote, TaraREST {

	@PersistenceContext(unitName = "itroDS")
	private EntityManager em;

	@Override
	public List<Tara> getAll() {

		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Tara.QUERY_ALL);

		return (List<Tara>) query.list();

	}

	public Tara getById(Long id) {
		Session session = (Session) em.getDelegate();
		return (Tara) session.get(Tara.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Tara insert(Tara t) throws EJBException {
		Session session = (Session) em.getDelegate();

		validate(t);

		session.persist(t);
		session.refresh(t);
		return t;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Tara update(Tara t) throws EJBException {
		Session session = (Session) em.getDelegate();
		validate(t);

		session.merge(t);
		return t;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Long id) {
		Session session = (Session) em.getDelegate();

		long used = (Long) session.createCriteria(Localitate.class).add(Restrictions.eq("tara.id", id))
				.setProjection(Projections.rowCount()).uniqueResult();

		if (used > 0) {
			throw new EJBException("Entity can not be deleted. It is used by another entity.");
		}
		
		used = (Long) session.createCriteria(Judet.class).add(Restrictions.eq("tara.id", id))
				.setProjection(Projections.rowCount()).uniqueResult();

		if (used > 0) {
			throw new EJBException("Entity can not be deleted. It is used by another entity.");
		}

		session.delete(getById(id));
	}

	@Override
	public int queryByName(String name) {
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Tara.QUERY_BY_NAME);
		query.setString("nume", name);
		return query.list().size();

	}

	private void validate(Tara t) throws EJBException {
		try {
			new ValidatorUtil().validate(t);
		} catch (ConstraintViolationException ex) {
			throw new EJBException(ex.getMessage());
		}
		if (queryByName(t.getNume()) > 0) {
			throw new EJBException("Duplicate contry name");
		}
	}

	@Override
	public Response insertTara(Tara tara) {
		try {
			tara = this.insert(tara);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(tara).build();

	}

	@Override
	public Response deleteTara(Long id) {
		try {
			this.delete(id);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}

	@Override
	public Response updateTara(Tara tara) {
		try {
			this.update(tara);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(tara).build();
	}

}
