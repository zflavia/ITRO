package ro.uvt.fmi.itro.ejb.autor;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import ro.uvt.fmi.persistenta.autor.Autor;

@Remote
public interface AutorRemote {

	public List<Autor> getAll();

	public Autor getById(Long id);

	public Autor insert(Autor t) throws EJBException;

	public Autor update(Autor t) throws EJBException;

	public void delete(Long id);
}
