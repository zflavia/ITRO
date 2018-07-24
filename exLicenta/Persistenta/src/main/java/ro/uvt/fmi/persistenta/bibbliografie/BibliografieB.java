package ro.uvt.fmi.persistenta.bibbliografie;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class BibliografieB extends Bibliografie{

}
