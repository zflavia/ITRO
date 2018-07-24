package ro.uvt.fmi.persistenta.traduceri;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Traducerii fara indicatii bibliografice")
public class TraduceriFaraIndicatii extends Traducere{

}
