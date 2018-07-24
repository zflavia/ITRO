package ro.uvt.fmi.persistenta.traduceri;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Traduceri manuscrise")
public class Manuscris extends Traducere{

}
