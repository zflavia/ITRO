package ro.uvt.fmi.itro.ejb.traducator;

import java.util.List;

import javax.ejb.Remote;

import ro.uvt.fmi.persistenta.traducator.Traducator;
import ro.uvt.fmi.persistenta.traducator.TraducatorToShort;

@Remote
public interface TraducatorRemote {
	public List<Traducator> getAll();

	public Traducator getById(Long id);

	public List<TraducatorToShort> getAllToShort();

	public void updateTraducator(Traducator u);
}