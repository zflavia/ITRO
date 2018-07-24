package ro.uvt.fmi.itro.ejb.localitate;

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
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ro.uvt.fmi.itro.ejb.Utils;
import ro.uvt.fmi.itro.ejb.utils.ValidatorUtil;
import ro.uvt.fmi.persistenta.localitate.Localitate;
import ro.uvt.fmi.persistenta.traducator.Traducator;

@Stateless
@Remote(LocalitateRemote.class)
@Local(LocalitateREST.class)
public class LocalitateEjb implements LocalitateRemote, LocalitateREST {

	@PersistenceContext(unitName = "itroDS")
	private EntityManager em;

	@Override
	public List<Localitate> getAll() {

		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Localitate.QUERY_ALL);

		return (List<Localitate>) query.list();
	}

	public Localitate getById(Long id) {
		Session session = (Session) em.getDelegate();
		return (Localitate) session.get(Localitate.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Localitate insert(Localitate t) throws EJBException {
		Session session = (Session) em.getDelegate();

		System.out.println("t.judet:" + t.getJudet()+"  t.tara:"+t.getTara());
		validateInsert(t);

		session.persist(t);
		return t;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Localitate update(Localitate l) throws EJBException {
		Session session = (Session) em.getDelegate();
		validateUpdate(l);

		l = (Localitate) session.merge(l);
		return l;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Long id) {
		Session session = (Session) em.getDelegate();

		Criterion locNastere = Restrictions.eq("localitateNastere.id", id);
		Criterion locDeces = Restrictions.eq("localitateDeces.id", id);

		Criterion completeCondition = Restrictions.disjunction().add(locNastere).add(locDeces);

		long used = (Long) session.createCriteria(Traducator.class).add(completeCondition)
				.setProjection(Projections.rowCount()).uniqueResult();

		if (used > 0) {
			throw new EJBException("Entity can not be deleted. It is used by another entity.");
		}

		session.delete(getById(id));
	}

	

	private void validateInsert(Localitate l) throws EJBException {
		try {
			new ValidatorUtil().validate(l);
		} catch (ConstraintViolationException ex) {
			throw new EJBException(ex.getMessage());
		}
		if (querySameLocalityExists(l, false) > 0) {
			throw new EJBException("Duplicate locality name");
		}
	}

	private long querySameLocalityExists(Localitate l, boolean update) {
		Session session = (Session) em.getDelegate();
		Criteria c = session.createCriteria(Localitate.class)
				.add(Restrictions.eq("nume", l.getNume()).ignoreCase());
		if (l.getTara() != null) {
			c.add(Restrictions.eq("tara.id", l.getTara().getId()));
		}
		if (l.getJudet() != null) {
			c.add(Restrictions.eq("judet.id", l.getJudet().getId()));
		}
		if (update) {
			c.add(Restrictions.ne("id", l.getId()));
		}
		c.setProjection(Projections.rowCount());
		return (Long) c.uniqueResult();
	}

	private void validateUpdate(Localitate l) throws EJBException {
		try {
			new ValidatorUtil().validate(l);
		} catch (ConstraintViolationException ex) {
			throw new EJBException(ex.getMessage());
		}
		if (querySameLocalityExists(l, true) > 0) {
			throw new EJBException("Duplicate locality name");
		}
	}

	@Override
	public Response insertLocalitate(Localitate localitate) {
		try {
			localitate = this.insert(localitate);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(localitate).build();

	}

	@Override
	public Response deleteLocalitate(Long id) {
		try {
			this.delete(id);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}

	@Override
	public Response updateLocalitate(Localitate localitate) {
		try {
			localitate = this.update(localitate);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(localitate).build();
	}

}
