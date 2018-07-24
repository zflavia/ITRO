package ro.uvt.fmi.persistenta.bibbliografie;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BPAT")
public class BibliografieBPAT extends Bibliografie{

}
