package ro.uvt.fmi.persistenta.traduceri;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Editii vechi")
public class EditiiVechi extends Traducere{

}
