package ro.uvt.fmi.persistenta.traduceri;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Dictionare")
public class Dictionar extends Traducere {

}
