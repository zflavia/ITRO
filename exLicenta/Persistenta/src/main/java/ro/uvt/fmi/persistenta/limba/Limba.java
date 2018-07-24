package ro.uvt.fmi.persistenta.limba;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ro.uvt.fmi.persistenta.util.DaoITRO;
import ro.uvt.fmi.persistenta.util.NotNullStringType;

@Entity
@Table(name = "Limba", uniqueConstraints = { @UniqueConstraint(columnNames = "nume") })
@TypeDefs({ @TypeDef(name = "trimmedStringType", typeClass = NotNullStringType.class) })
@NamedQueries({ @NamedQuery(name = Limba.QUERY_ALL, query = "from Limba t ORDER BY t.nume"),
		@NamedQuery(name = Limba.QUERY_BY_NAME, query = "from Limba WHERE upper(nume)=upper(:nume)") })
public class Limba extends DaoITRO {
	private static final long serialVersionUID = 1L;
	public static final String QUERY_ALL = "ro.uvt.fmi.persistenta.limba.query.all";
	public static final String QUERY_BY_NAME = "ro.uvt.fmi.persistenta.limba.query.by.name";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nume", unique = true)
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.limba.nume.mandatory}")
	@Length(min = 3, message = "{dao.limba.nume.length}")
	private String nume;

	@Override
	public String toString() {
		return "Limba [id=" + id + ", nume=" + nume + "]";
	}

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

}

