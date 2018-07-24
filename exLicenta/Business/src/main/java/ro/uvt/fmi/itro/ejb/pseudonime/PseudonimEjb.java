package ro.uvt.fmi.itro.ejb.pseudonime;
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
import ro.uvt.fmi.itro.ejb.autor.AutorREST;
import ro.uvt.fmi.itro.ejb.autor.AutorRemote;
import ro.uvt.fmi.itro.ejb.utils.ValidatorUtil;
import ro.uvt.fmi.persistenta.autor.Autor;
import ro.uvt.fmi.persistenta.pseudonim.Pseudonim;
import ro.uvt.fmi.itro.ejb.pseudonime.*;
@Stateless
@Remote(PseudonimRemote.class)
@Local(PseudonimREST.class)

public class PseudonimEjb implements PseudonimRemote, PseudonimREST {
	
	@PersistenceContext(unitName = "itroDS")
	private EntityManager em;
	
	//Pseudonime
	
		public Pseudonim getByIdPseudonim(Long id) {
			Session session = (Session) em.getDelegate();
			return (Pseudonim) session.get(Pseudonim.class, id);
		}
		@Override
		public Response insertPseudonim(Pseudonim entry) {
			try {
				entry = this._insertPseudonim(entry);
			} catch (EJBException e) {
				return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
			}
			return Response.status(HttpStatus.SC_OK).entity(entry).build();

		}
		Pseudonim _insertPseudonim(Pseudonim entry) {
			System.out.println("insert ...." + entry);
			Session session = (Session) em.getDelegate();
			//validateInsert(entry);
			session.persist(entry);
			return entry;
		}
		@Override
		public Response updatePseudonim(Pseudonim entry) {
			try {
				entry = this._updatePseudonim(entry);
			} catch (EJBException e) {
				return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
			}
			return Response.status(HttpStatus.SC_OK).entity(entry).build();		
		}
		Pseudonim _updatePseudonim(Pseudonim entry) {
			Session session = (Session) em.getDelegate();
			//validateUpdate(entry);

			entry = (Pseudonim) session.merge(entry);
			return entry;
		}
		@Override
		public Response deletePseudonim(Long id) {
			try {
				this._deletePseudonim(id);
			} catch (EJBException e) {
				return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
			}
			return Response.status(HttpStatus.SC_OK).build();
		}
		private void _deletePseudonim(Long id) {
			Session session = (Session) em.getDelegate();
			session.delete(getByIdPseudonim(id));
		}
		
		@Override
		public List<Pseudonim> getAll() {
			Session session = (Session) em.getDelegate();
			Query query = session.getNamedQuery(Pseudonim.QUERY_ALL);

			return (List<Pseudonim>) query.list();
		}
		private long querySamePseudonimExists(Pseudonim a, boolean update) {
			Session session = (Session) em.getDelegate();
			Criteria c = session.createCriteria(Autor.class).add(Restrictions.eq("nume", a.getAlias()).ignoreCase());

			if (a.getAlias() != null)
				c.add(Restrictions.eq("alias", a.getAlias()).ignoreCase());
			
			if (update) {
				c.add(Restrictions.ne("id", a.getId()));
			}
			c.setProjection(Projections.rowCount());
			return (Long) c.uniqueResult();
		}

}
