package ro.uvt.fmi.persistenta.autor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ro.uvt.fmi.persistenta.util.DaoITRO;
import ro.uvt.fmi.persistenta.util.NotNullStringType;


@Entity
@Table(name = "Autor")

@TypeDefs({ @TypeDef(name = "trimmedStringType", typeClass = NotNullStringType.class) })
@NamedQueries({ @NamedQuery(name = Autor.QUERY_ALL, query = "from Autor a ORDER BY a.nume, a.prenume")
	 })
public class Autor extends DaoITRO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String QUERY_ALL = "ro.uvt.fmi.persistenta.autor.query.all";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nume")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.autor.nume.mandatory}")
	@Length(min = 2, message = "{dao.autor.nume.length}")
	private String nume;
	
	@Column(name = "prenume")
	@Type(type = "trimmedStringType")
	private String prenume;
	
	@Column(name = "titlu")
	@Type(type = "trimmedStringType")
	private String titlu;
	
	@Column(name = "detalii")
	@Type(type = "trimmedStringType")
	private String detalii;
	

	@Override
	public String toString() {
		return "Autor [id=" + id + ", nume=" + nume + ", prenume=" + prenume+ "]";
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

	public String getPrenume() {
		return prenume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public String getTitlu() {
		return titlu;
	}

	public void setTitlu(String titlu) {
		this.titlu = titlu;
	}

	public String getDetalii() {
		return detalii;
	}

	public void setDetalii(String detalii) {
		this.detalii = detalii;
	}

	
}


