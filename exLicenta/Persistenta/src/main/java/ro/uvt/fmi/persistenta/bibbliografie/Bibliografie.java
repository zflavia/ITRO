package ro.uvt.fmi.persistenta.bibbliografie;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ro.uvt.fmi.persistenta.autor.Autor;
import ro.uvt.fmi.persistenta.util.DaoITRO;
import ro.uvt.fmi.persistenta.util.NotNullStringType;

@Entity
@Table(name = "Bibliografie")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipBibliografie", discriminatorType = DiscriminatorType.STRING)
@TypeDefs({ @TypeDef(name = "trimmedStringType", typeClass = NotNullStringType.class) })
@DiscriminatorOptions(force = true)
public class Bibliografie extends DaoITRO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "BibliografieAutor", joinColumns = @JoinColumn(name = "bibliografieId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "autorId", referencedColumnName = "id"))
	private Set<Autor> autori;

	@Column(name = "titlu", nullable = false)
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.bibliografie.titlu.mandatory}")
	@Length(min = 3, message = "{dao.bibliografie.titlu.length}")
	private String titlu;

	@Column(name = "an")
	private Integer an;

	@Column(name = "detalii")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.bibliografie.detalii.mandatory}")
	@Length(min = 3, message = "{dao.bibliografie.detalii.length}")
	private String detalii;

	@Column(name = "link")
	@Type(type = "trimmedStringType")
	@Length(min = 3, message = "{dao.bibliografie.url.length}")
	private String url;

	@Column(name = "traducatorId", nullable = false)
	private Long traducatorId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Autor> getAutori() {
		return autori;
	}

	public void setAutori(Set<Autor> autori) {
		this.autori = autori;
	}

	public String getTitlu() {
		return titlu;
	}

	public void setTitlu(String titlu) {
		this.titlu = titlu;
	}

	public Integer getAn() {
		return an;
	}

	public void setAn(Integer an) {
		this.an = an;
	}

	public String getDetalii() {
		return detalii;
	}

	public void setDetalii(String detalii) {
		this.detalii = detalii;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getTraducatorId() {
		return traducatorId;
	}

	public void setTraducatorId(Long traducatorId) {
		this.traducatorId = traducatorId;
	}

	public String toString() {
		return "Bibliografie [ id: " + id + " titlu: " + titlu + " an: " + an + " autorii:" + autori + "]";
	}
}
