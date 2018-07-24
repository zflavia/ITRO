package ro.uvt.fmi.itro.ejb.limba;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import ro.uvt.fmi.persistenta.limba.Limba;

@Remote
public interface LimbaRemote {

	public List<Limba> getAll();

	public Limba getById(Long id);

	public Limba insert(Limba l) throws EJBException;

	public Limba update(Limba l) throws EJBException;

	public void delete(Long id);
	
	public int queryByName(String name);

}