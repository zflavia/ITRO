package ro.uvt.fmi.itro.ejb.traducator;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import ro.uvt.fmi.itro.ejb.Utils;
import ro.uvt.fmi.persistenta.bibbliografie.Bibliografie;
import ro.uvt.fmi.persistenta.bibbliografie.BibliografieB;
import ro.uvt.fmi.persistenta.bibbliografie.BibliografieBPAT;
import ro.uvt.fmi.persistenta.referinte.VizibilitateBDD;
import ro.uvt.fmi.persistenta.traducator.Traducator;
import ro.uvt.fmi.persistenta.traducator.TraducatorToShort;
import ro.uvt.fmi.persistenta.traduceri.*;
import ro.uvt.fmi.persistenta.pseudonim.*;
@Stateless
@Remote(TraducatorRemote.class)
public class TraducatorEjb implements TraducatorRemote, TraducatorDetailsREST {

	@PersistenceContext(unitName = "itroDS")
	private EntityManager em;

	@Override
	public List<Traducator> getAll() {
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(Traducator.QUERY_ALL);
		return (List<Traducator>) query.list();
	}

	@Override
	public Traducator getById(Long id) {
		Session session = (Session) em.getDelegate();
		return (Traducator) session.get(Traducator.class, id);
	}

	public List<TraducatorToShort> getAllToShort() {

		Session session = (Session) em.getDelegate();
		Criteria cr = session.createCriteria(Traducator.class, "traducator");
				cr.setProjection(Projections.projectionList()
						.add(Projections.property("id"), "id")
						.add(Projections.property("nume"), "nume")
						.add(Projections.property("prenume"), "prenume"))
						//.add(Projections.property("pseudonime"), "pseudonime"))
				.setResultTransformer(Transformers.aliasToBean(TraducatorToShort.class));

		// Query query = session.getNamedQuery(Traducator.QUERY_ALL);
		// query.setResultTransformer(Transformers.aliasToBean(TraducatorToShort.class));
		return (List<TraducatorToShort>) cr.list();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTraducator(Traducator u) {
		Session session = (Session) em.getDelegate();
		session.merge(u);

	}
	//VizibilitateBDD
	@Override
	public VizibilitateBDD getByIdBDD(Long id) {
		Session session = (Session) em.getDelegate();
		return (VizibilitateBDD) session.get(VizibilitateBDD.class, id);
	}

	@Override
	public Response insertBDD(VizibilitateBDD entry) {
		try {
			entry = this._insertBDD(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();

	}
	
	VizibilitateBDD _insertBDD(VizibilitateBDD entry) {
		System.out.println("insert ...." + entry);
		Session session = (Session) em.getDelegate();
		//validateInsert(entry);

		session.persist(entry);
		return entry;
	}
	
	VizibilitateBDD _updateBDD(VizibilitateBDD entry) {
		Session session = (Session) em.getDelegate();
		//validateUpdate(entry);

		entry = (VizibilitateBDD) session.merge(entry);
		return entry;
	}

	@Override
	public Response updateBDD(VizibilitateBDD entry) {
		try {
			entry = this._updateBDD(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();		
	}
	
	private void _deleteBDD(Long id) {
		Session session = (Session) em.getDelegate();
		session.delete(getByIdBDD(id));
	}

	@Override
	public Response deleteBDD(Long id) {
		try {
			this._deleteBDD(id);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}

	//BibliografieBPAT
	@Override
	public BibliografieBPAT getByIdBiblioBPAT(Long id) {	
		Session session = (Session) em.getDelegate();
		return (BibliografieBPAT) session.get(BibliografieBPAT.class, id);
	}

	@Override
	public Response insertBiblioBPAT(BibliografieBPAT entry) {
		try {
			entry = this._insertBPAT(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();

	}

	@Override
	public Response updateBiblioBPAT(BibliografieBPAT entry) {
		try {
			entry = this._updateBPAT(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();
	}

	@Override
	public Response deleteBiblioBPAT(Long id) {
		try {
			this._deleteBPAT(id);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}
	//Bibliografie
	@Override
	public BibliografieB getByIdBiblioB(Long id) {
		Session session = (Session) em.getDelegate();
		return (BibliografieB) session.get(BibliografieB.class, id);
	}

	@Override
	public Response insertBiblioB(BibliografieB entry) {
		try {
			entry = this._insertB(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();

	}

	@Override
	public Response updateBiblioB(BibliografieB entry) {
		try {
			entry = this._updateB(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();
	}

	@Override
	public Response deleteBiblioB(Long id) {
		try {
			this._deleteB(id);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}
	
	private void _deleteB(Long id) {
		Session session = (Session) em.getDelegate();
		session.delete(getByIdBiblioB(id));
	}
	private void _deleteBPAT(Long id) {
		Session session = (Session) em.getDelegate();
		session.delete(getByIdBiblioBPAT(id));
	}
	
	private BibliografieB _updateB(BibliografieB entry) {
		Session session = (Session) em.getDelegate();
		//validateUpdate(entry);
		String sql = "update bibliografie set tipBibliografie =:tip where id=:id";
		
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter("tip", "B");
		query.setParameter("id", entry.getId());
		query.executeUpdate();
		
		entry = (BibliografieB) session.merge(entry);
		return entry;
	}
	
	private BibliografieBPAT _updateBPAT(BibliografieBPAT entry) {
		Session session = (Session) em.getDelegate();
		String sql = "update bibliografie set tipBibliografie =:tip where id=:id";
		System.out.println("update ...." + entry);
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter("tip", "BPAT");
		query.setParameter("id", entry.getId());
		query.executeUpdate();
		
		//validateUpdate(entry);

		entry = (BibliografieBPAT) session.merge(entry);
		return entry;
	}
	
	BibliografieB _insertB(BibliografieB entry) {
		System.out.println("insert ...." + entry);
		Session session = (Session) em.getDelegate();
		//validateInsert(entry);
		
		session.persist(entry);
		return entry;
	}
	
	BibliografieBPAT _insertBPAT(BibliografieBPAT entry) {
		System.out.println("insert ...." + entry);
		Session session = (Session) em.getDelegate();
		//validateInsert(entry);

		session.persist(entry);
		return entry;
	}
	//tradManuscript
	@Override
	public Manuscris getByIdTradManuscript(Long id) {
		Session session = (Session) em.getDelegate();
		return (Manuscris) session.get(Manuscris.class, id);
	}
	
	@Override
	public Response insertManuscript(Manuscris entry) {
		try {
			entry = this._insertManuscris(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();

	}
	Manuscris _insertManuscris(Manuscris entry) {
		System.out.println("insert ...." + entry);
		Session session = (Session) em.getDelegate();
		//validateInsert(entry);
		session.persist(entry);
		return entry;
	}
	@Override
	public Response updateManuscript(Manuscris entry) {
		try {
			entry = this._updateManuscript(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();		
	}
	
	Manuscris _updateManuscript(Manuscris entry) {
		Session session = (Session) em.getDelegate();
		//validateUpdate(entry);

		entry = (Manuscris) session.merge(entry);
		return entry;
	}
	private void _deleteManuscript(Long id) {
		Session session = (Session) em.getDelegate();
		session.delete(getByIdTradManuscript(id));
	}

	@Override
	public Response deleteManuscript(Long id) {
		try {
			this._deleteManuscript(id);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}
	
	//Editii Vechi
	@Override
	public EditiiVechi getByIdEditiiVechi(Long id) {
		Session session = (Session) em.getDelegate();
		return (EditiiVechi) session.get(EditiiVechi.class, id);
	}
	@Override
	public Response insertEditiiVechi(EditiiVechi entry) {
		try {
			entry = this._insertEditiiVechi(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();

	}
	EditiiVechi _insertEditiiVechi(EditiiVechi entry) {
		System.out.println("insert ...." + entry);
		Session session = (Session) em.getDelegate();
		//validateInsert(entry);
		session.persist(entry);
		return entry;
	}
	@Override
	public Response updateEditiiVechi(EditiiVechi entry) {
		try {
			entry = this._updateEditiiVechi(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();		
	}
	EditiiVechi _updateEditiiVechi(EditiiVechi entry) {
		Session session = (Session) em.getDelegate();
		//validateUpdate(entry);

		entry = (EditiiVechi) session.merge(entry);
		return entry;
	}
	@Override
	public Response deleteEditiiVechi(Long id) {
		try {
			this._deleteEditiiVechi(id);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}
	private void _deleteEditiiVechi(Long id) {
		Session session = (Session) em.getDelegate();
		session.delete(getByIdEditiiVechi(id));
	}
	
	//Traducere Fara Indicatii
	@Override
	public TraduceriFaraIndicatii getByIdTradFaraIndicatii(Long id) {
		Session session = (Session) em.getDelegate();
		return (TraduceriFaraIndicatii) session.get(TraduceriFaraIndicatii.class, id);
	}
	@Override
	public Response insertTradFaraIndicatii(TraduceriFaraIndicatii entry) {
		try {
			entry = this._insertTradFaraIndicatii(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();

	}
	TraduceriFaraIndicatii _insertTradFaraIndicatii(TraduceriFaraIndicatii entry) {
		System.out.println("insert ...." + entry);
		Session session = (Session) em.getDelegate();
		//validateInsert(entry);
		session.persist(entry);
		return entry;
	}
	@Override
	public Response updateTradFaraIndicatii(TraduceriFaraIndicatii entry) {
		try {
			entry = this._updateTradFaraIndicatii(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();		
	}
	TraduceriFaraIndicatii _updateTradFaraIndicatii(TraduceriFaraIndicatii entry) {
		Session session = (Session) em.getDelegate();
		//validateUpdate(entry);

		entry = (TraduceriFaraIndicatii) session.merge(entry);
		return entry;
	}
	@Override
	public Response deleteTradFaraIndicatii(Long id) {
		try {
			this._deleteTradFaraIndicatii(id);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}
	private void _deleteTradFaraIndicatii(Long id) {
		Session session = (Session) em.getDelegate();
		session.delete(getByIdTradFaraIndicatii(id));
	}
	
	//EditiiModerne
	@Override
	public EditiiModerne getByIdEditiiModerne(Long id) {
		Session session = (Session) em.getDelegate();
		return (EditiiModerne) session.get(EditiiModerne.class, id);
	}
	@Override
	public Response insertEditiiModerne(EditiiModerne entry) {
		try {
			entry = this._insertEditiiModerne(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();

	}
	EditiiModerne _insertEditiiModerne(EditiiModerne entry) {
		System.out.println("insert ...." + entry);
		Session session = (Session) em.getDelegate();
		//validateInsert(entry);
		session.persist(entry);
		return entry;
	}
	@Override
	public Response updateEditiiModerne(EditiiModerne entry) {
		try {
			entry = this._updateEditiiModerne(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();		
	}
	EditiiModerne _updateEditiiModerne(EditiiModerne entry) {
		Session session = (Session) em.getDelegate();
		//validateUpdate(entry);

		entry = (EditiiModerne) session.merge(entry);
		return entry;
	}
	@Override
	public Response deleteEditiiModerne(Long id) {
		try {
			this._deleteEditiiModerne(id);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}
	private void _deleteEditiiModerne(Long id) {
		Session session = (Session) em.getDelegate();
		session.delete(getByIdEditiiModerne(id));
	}
	
	//Manual
	@Override
	public Manual getByIdManual(Long id) {
		Session session = (Session) em.getDelegate();
		return (Manual) session.get(Manual.class, id);
	}
	@Override
	public Response insertManual(Manual entry) {
		try {
			entry = this._insertManual(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();

	}
	Manual _insertManual(Manual entry) {
		System.out.println("insert ...." + entry);
		Session session = (Session) em.getDelegate();
		//validateInsert(entry);
		session.persist(entry);
		return entry;
	}
	@Override
	public Response updateManual(Manual entry) {
		try {
			entry = this._updateManual(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();		
	}
	Manual _updateManual(Manual entry) {
		Session session = (Session) em.getDelegate();
		//validateUpdate(entry);

		entry = (Manual) session.merge(entry);
		return entry;
	}
	@Override
	public Response deleteManual(Long id) {
		try {
			this._deleteManual(id);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}
	private void _deleteManual(Long id) {
		Session session = (Session) em.getDelegate();
		session.delete(getByIdManual(id));
	}
	
	//Dictionar
	@Override
	public Dictionar getByIdDictionar(Long id) {
		Session session = (Session) em.getDelegate();
		return (Dictionar) session.get(Dictionar.class, id);
	}
	@Override
	public Response insertDictionar(Dictionar entry) {
		try {
			entry = this._insertDictionar(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();

	}
	Dictionar _insertDictionar(Dictionar entry) {
		System.out.println("insert ...." + entry);
		Session session = (Session) em.getDelegate();
		//validateInsert(entry);
		session.persist(entry);
		return entry;
	}
	@Override
	public Response updateDictionar(Dictionar entry) {
		try {
			entry = this._updateDictionar(entry);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).entity(entry).build();		
	}
	Dictionar _updateDictionar(Dictionar entry) {
		Session session = (Session) em.getDelegate();
		//validateUpdate(entry);

		entry = (Dictionar) session.merge(entry);
		return entry;
	}
	@Override
	public Response deleteDictionar(Long id) {
		try {
			this._deleteDictionar(id);
		} catch (EJBException e) {
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(Utils.prepareException(e.getMessage())).build();
		}
		return Response.status(HttpStatus.SC_OK).build();
	}
	private void _deleteDictionar(Long id) {
		Session session = (Session) em.getDelegate();
		session.delete(getByIdDictionar(id));
	}
	
}
