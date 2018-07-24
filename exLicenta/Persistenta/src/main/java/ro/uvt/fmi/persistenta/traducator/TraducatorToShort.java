package ro.uvt.fmi.persistenta.traducator;

import java.io.Serializable;
import java.util.Set;

import ro.uvt.fmi.persistenta.pseudonim.Pseudonim;

public class TraducatorToShort implements Serializable{

	Long id;
	String nume;
	String prenume;
    Set<Pseudonim> pseudonime;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getPrenume() {
		return prenume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public Set<Pseudonim> getPseudonime() {
		return pseudonime;
	}

	public void setPseudonime(Set<Pseudonim> pseudonime) {
		this.pseudonime = pseudonime;
	}

	@Override
	public String toString() {
		return "TraducatorToShort [id=" + id + ", nume=" + nume + ", prenume=" + prenume + ", pseudonime=" + pseudonime
				+ "]";
	}
	

}
