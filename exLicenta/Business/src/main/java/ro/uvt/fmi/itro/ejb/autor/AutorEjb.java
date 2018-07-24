package ro.uvt.fmi.itro.ejb.autor;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ro.uvt.fmi.itro.ejb.Utils;
import ro.uvt.fmi.itro.ejb.utils.ValidatorUtil;
import ro.uvt.fmi.persistenta.autor.Autor;
import ro.uvt.fmi.persistenta.bibbliografie.Bibliografie;
import ro.uvt.fmi.persistenta.localitate.Localitate;
import ro.uvt.fmi.persistenta.traduceri.Traducere;

@Stateless
@Remote(AutorRemote.class)
@Local(AutorREST.class)
public class AutorEjb implements AutorRemote, AutorREST {

	@PersistenceContext(unitName = "itroDS")
	private EntityManager em;

	@Override
	public Response insertAutor(Autor autor) {
		try {
			autor = this.insert(autor);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(autor).build();

	}

	@Override
	public Response updateAutor(Autor autor) {
		try {
			this.update(autor);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(autor).build();
	}

	@Override
	public Response deleteAutor(Long id) {
		try {
			this.delete(id);
		} catch (EJBException e) {
			System.out.println("Error: " + e.getMessage());
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}

	@Override
	public List<Autor> getAll() {
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Autor.QUERY_ALL);

		return (List<Autor>) query.list();
	}

	@Override
	public Autor getById(Long id) {
		Session session = (Session) em.getDelegate();
		return (Autor) session.get(Autor.class, id);
	}

	@Override
	public Autor insert(Autor t) throws EJBException {
		Session session = (Session) em.getDelegate();

		validate(t, false);

		session.persist(t);
		session.refresh(t);
		return t;
	}

	@Override
	public Autor update(Autor t) throws EJBException {
		Session session = (Session) em.getDelegate();
		validate(t, true);

		session.merge(t);
		return t;
	}

	@Override
	public void delete(Long id) {
		Session session = (Session) em.getDelegate();

		long used = (Long) session.createCriteria(Bibliografie.class).createAlias("autori", "a")
				.add(Restrictions.eq("a.id", id)).setProjection(Projections.rowCount()).uniqueResult();

		if (used > 0) {
			throw new EJBException("Entity can not be deleted. It is used by another entity.");
		}

		used = (Long) session.createCriteria(Traducere.class).createAlias("autori", "a")
				.add(Restrictions.eq("a.id", id)).setProjection(Projections.rowCount()).uniqueResult();

		if (used > 0) {
			throw new EJBException("Entity can not be deleted. It is used by another entity.");
		}

		session.delete(getById(id));

	}

	private void validate(Autor a, boolean update) throws EJBException {
		try {
			new ValidatorUtil().validate(a);
		} catch (ConstraintViolationException ex) {
			throw new EJBException(ex.getMessage());
		}
		if (querySameAuthorExists(a, update) > 0) {
			throw new EJBException("Duplicate author");
		}
	}

	private long querySameAuthorExists(Autor a, boolean update) {
		Session session = (Session) em.getDelegate();
		Criteria c = session.createCriteria(Autor.class).add(Restrictions.eq("nume", a.getNume()).ignoreCase());

		if (a.getPrenume() != null)
			c.add(Restrictions.eq("prenume", a.getPrenume()).ignoreCase());
		if (a.getTitlu() != null)
			c.add(Restrictions.eq("titlu", a.getTitlu()).ignoreCase());
		if (a.getDetalii() != null)
			c.add(Restrictions.eq("detalii", a.getDetalii()).ignoreCase());

		if (update) {
			c.add(Restrictions.ne("id", a.getId()));
		}
		c.setProjection(Projections.rowCount());
		return (Long) c.uniqueResult();
	}

}
