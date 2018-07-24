package ro.uvt.fmi.persistenta.referinte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ro.uvt.fmi.persistenta.util.DaoITRO;
import ro.uvt.fmi.persistenta.util.NotNullStringType;


@Entity
@Table(name = "VizibilitateBDD")
@TypeDefs({ @TypeDef(name = "trimmedStringType", typeClass = NotNullStringType.class) })
public class VizibilitateBDD extends DaoITRO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	//@Expose
	private Long id;

	@Column(name = "link", nullable = false)
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.vizibilitateBDD.url.mandatory}")
	@Length(min = 3, message = "{dao.vizibilitateBDD.url.length}")
	private String url;

	@Column(name = "descriereSursa")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.vizibilitateBDD.detalii.mandatory}")
	@Length(min = 3, message = "{dao.vizibilitateBDD.detalii.length}")
	private String detalii;
	
	@Column(name = "traducatorId", nullable = false)
	private Long traducatorId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDetalii() {
		return detalii;
	}

	public void setDetalii(String detalii) {
		this.detalii = detalii;
	}

	public String toString() {
		return "VizibilitateBDD [ id = " + this.id + " traducator_id:" +this.traducatorId+ "]";
	}

	public Long getTraducatorId() {
		return traducatorId;
	}

	public void setTraducatorId(Long traducatorId) {
		this.traducatorId = traducatorId;
	}
	
}
