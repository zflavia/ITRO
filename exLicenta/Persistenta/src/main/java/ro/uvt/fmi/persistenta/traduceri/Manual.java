package ro.uvt.fmi.persistenta.traduceri;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Manuale")
public class Manual extends Traducere {

}
