package ro.uvt.fmi.persistenta.traduceri;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Editii moderne")
public class EditiiModerne extends Traducere{

}
