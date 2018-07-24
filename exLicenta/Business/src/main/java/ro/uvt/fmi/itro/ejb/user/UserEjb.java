package ro.uvt.fmi.itro.ejb.user;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import ro.uvt.fmi.persistenta.traducator.Traducator;
import ro.uvt.fmi.persistenta.traducator.TraducatorToShort;
import ro.uvt.fmi.persistenta.user.User;
import ro.uvt.fmi.persistenta.user.UserToShort;

@Stateless
@Local(UserREST.class)
@Remote(UserRemote.class)
public class UserEjb implements UserRemote, UserREST{

	
	@PersistenceContext(unitName = "itroDS")
	private EntityManager em;

	
	public User getUserById(Long id) {
		Session session = (Session) em.getDelegate();
		return (User) session.get(User.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public User insertUser(User u, String role) {
//		try {
//			u.validate();
//		} catch (com.viagents.persistence.PersistenceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Session session = (Session) em.getDelegate();
//		Role r = (Role)session.get(Role.class, role);	
//		u.addRole(r);
		session.persist(u);
		return u;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateUser(User u) {
		Session session = (Session) em.getDelegate();
		session.merge(u);

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteUser(Long id) {
		Session session = (Session) em.getDelegate();
		session.delete(getUserById(id));
	}

	

	@Override
	public List<User> getAllActiveUsers() {
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(User.QUERY_BY_ACTIVE_USERS);
		query.setParameter("status", User.USER_STATUS.ACTIVE.getLabel());
		return (List<User>) query.list();
	}

	@Override
	public List<UserToShort> getAll() {
		

			Session session = (Session) em.getDelegate();
			Criteria cr = session.createCriteria(User.class)
					.setProjection(Projections.projectionList()
							.add(Projections.property("id"), "id")
							.add(Projections.property("firstName"), "firstName")
							.add(Projections.property("lastName"), "lastName")
							.add(Projections.property("userName"), "userName")
							.add(Projections.property("alias"), "alias")
							)
					.add(Restrictions.eq("status", User.USER_STATUS.ACTIVE.getLabel()))
					.setResultTransformer(Transformers.aliasToBean(UserToShort.class));
			return (List<UserToShort>) cr.list();
		
	}

	@Override
	public Collection<User> getAllActiveAndScieUsers() {
		Session session = (Session) em.getDelegate();
		Query query = session.getNamedQuery(User.QUERY_BY_ACTIVE_AND_SCIENTIST_USERS);
		query.setParameter("status", User.USER_STATUS.ACTIVE.getLabel());
		query.setParameter("type", User.USER_TYPE.SCIENTIST.getLabel());
		return (List<User>) query.list();
		
	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
