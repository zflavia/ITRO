package ro.uvt.fmi.itro.ejb.tara;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import ro.uvt.fmi.persistenta.tara.Tara;

@Remote
public interface TaraRemote {

	public List<Tara> getAll();

	public Tara getById(Long id);

	public Tara insert(Tara t) throws EJBException;

	public Tara update(Tara t) throws EJBException;

	public void delete(Long id);
	
	public int queryByName(String name);

}
