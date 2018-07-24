package ro.uvt.fmi.persistenta.judet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ro.uvt.fmi.persistenta.tara.Tara;
import ro.uvt.fmi.persistenta.util.DaoITRO;
import ro.uvt.fmi.persistenta.util.NotNullStringType;

@Entity
@Table(name = "Judet")

@TypeDefs({ @TypeDef(name = "trimmedStringType", typeClass = NotNullStringType.class) })
@NamedQueries({ @NamedQuery(name = Judet.QUERY_ALL, query = "from Judet j ORDER BY j.nume"),
	@NamedQuery(name = Judet.QUERY_BY_TARA, query = "from Judet j WHERE j.tara.id = :taraId ORDER BY j.nume"),
	@NamedQuery(name = Judet.QUERY_BY_NAME_AND_COUNTRY, query = "from Judet j where upper(j.nume) = upper(:nume) AND j.tara.id = :countryId"),
	@NamedQuery(name = Judet.QUERY_BY_ID_AND_NAME_AND_COUNTRY, query = "from Judet j where upper(j.nume) = upper(:nume) AND j.tara.id = :countryId AND j.id != :id")	
})
public class Judet extends DaoITRO{
	public static final String QUERY_ALL = "ro.uvt.fmi.persistenta.judet.query.all";
	public static final String QUERY_BY_TARA = "ro.uvt.fmi.persistenta.judet.query.by.tara";
	public static final String QUERY_BY_NAME_AND_COUNTRY = "ro.uvt.fmi.persistenta.judet.query.by.name.and.country";
	public static final String QUERY_BY_ID_AND_NAME_AND_COUNTRY = "ro.uvt.fmi.persistenta.judet.query.by.id.and.name.and.country";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nume", unique = true)
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.judet.nume.mandatory}")
	@Length(min = 3, message = "{dao.judet.nume.length}")
	private String nume;
	
	@OneToOne
	@JoinColumn(name="taraId", nullable = false)
	@NotNull(message = "{dao.judet.tara.mandatory}")
	private Tara tara;

	@Override
	public String toString() {
		return "Judet [id=" + id + ", userName=" + nume + "]";
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

	public Tara getTara() {
		return tara;
	}

	public void setTara(Tara tara) {
		this.tara = tara;
	}

	
}

