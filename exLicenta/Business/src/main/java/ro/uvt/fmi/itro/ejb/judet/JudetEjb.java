package ro.uvt.fmi.itro.ejb.judet;

import java.util.ArrayList;
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
@Remote(JudetRemote.class)
@Local(JudetREST.class)
public class JudetEjb implements JudetRemote, JudetREST {

	@PersistenceContext(unitName = "itroDS")
	private EntityManager em;

	@Override
	public List<Judet> getAll() {

		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Judet.QUERY_ALL);

		return (List<Judet>) query.list();
	}

	public Judet getById(Long id) {
		Session session = (Session) em.getDelegate();
		return (Judet) session.get(Judet.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Judet insert(Judet t) throws EJBException {
		Session session = (Session) em.getDelegate();

		validateInsert(t);

		session.persist(t);
		session.refresh(t);
		return t;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Judet update(Judet t) throws EJBException {
		Session session = (Session) em.getDelegate();
		validateUpdate(t);

		t = (Judet) session.merge(t);
		return t;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Long id) {
		Session session = (Session) em.getDelegate();

		long used = (Long) session.createCriteria(Localitate.class).add(Restrictions.eq("judet.id", id))
				.setProjection(Projections.rowCount()).uniqueResult();

		if (used > 0) {
			throw new EJBException("Entity can not be deleted. It is used by another entity.");
		}

		session.delete(getById(id));
	}

	private int queryByNameAndCountry(String name, Long countryId) {
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Judet.QUERY_BY_NAME_AND_COUNTRY);
		query.setString("nume", name);
		query.setLong("countryId", countryId);
		return query.list().size();
	}

	private int queryByIdAndNameAndCountry(Long id, String name, Long countryId) {
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Judet.QUERY_BY_ID_AND_NAME_AND_COUNTRY);
		query.setString("nume", name);
		query.setLong("countryId", countryId);
		query.setLong("id", id);
		return query.list().size();
	}

	private void validateInsert(Judet t) throws EJBException {
		if (t.getTara().getId() == null)
			t.setTara(null);
		try {
			new ValidatorUtil().validate(t);
		} catch (ConstraintViolationException ex) {
			throw new EJBException(ex.getMessage());
		}

		if (queryByNameAndCountry(t.getNume(), t.getTara().getId()) > 0) {
			throw new EJBException("Duplicate county name in same country");
		}
	}

	private void validateUpdate(Judet t) throws EJBException {
		try {
			new ValidatorUtil().validate(t);
		} catch (ConstraintViolationException ex) {
			throw new EJBException(ex.getMessage());
		}

		if (queryByIdAndNameAndCountry(t.getId(), t.getNume(), t.getTara().getId()) > 0) {
			throw new EJBException("Duplicate county name in same country");
		}
	}

	@Override
	public Response insertJudet(Judet judet) {
		try {
			judet = this.insert(judet);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(judet).build();

	}

	@Override
	public Response deleteJudet(Long id) {

		try {
			this.delete(id);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}

	@Override
	public Response updateJudet(Judet judet) {
		try {
			judet = this.update(judet);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(judet).build();
	}

	@Override
	public List<Judet> getByCountry(Long id) {
		if (id == null)
			return new ArrayList<>();
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Judet.QUERY_BY_TARA);
		query.setLong("taraId", id);

		return (List<Judet>) query.list();

	}
}
