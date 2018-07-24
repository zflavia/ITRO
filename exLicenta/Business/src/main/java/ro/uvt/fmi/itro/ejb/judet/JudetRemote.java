package ro.uvt.fmi.itro.ejb.judet;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import ro.uvt.fmi.persistenta.judet.Judet;
import ro.uvt.fmi.persistenta.tara.Tara;

@Remote
public interface JudetRemote {
	public List<Judet> getAll();

	public Judet getById(Long id);

	public Judet insert(Judet t) throws EJBException;

	public Judet update(Judet t) throws EJBException;

	public void delete(Long id);
}
