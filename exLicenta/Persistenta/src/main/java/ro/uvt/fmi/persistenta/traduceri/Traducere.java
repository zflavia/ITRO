package ro.uvt.fmi.persistenta.traduceri;

import java.util.List;
import java.util.Set;

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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ro.uvt.fmi.persistenta.autor.Autor;
import ro.uvt.fmi.persistenta.traducator.Traducator;
import ro.uvt.fmi.persistenta.util.DaoITRO;
import ro.uvt.fmi.persistenta.util.NotNullStringType;

@Entity
@Table(name = "ListaTraduceri")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipTraducere", discriminatorType = DiscriminatorType.STRING)
@TypeDefs({ @TypeDef(name = "trimmedStringType", typeClass = NotNullStringType.class) })
@DiscriminatorOptions(force = true)
public class Traducere extends DaoITRO{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
//	@OneToOne
//	@JoinColumn(name="traducatorId")
//	private Traducator traducator; 
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(
	        name = "AutorListaTraduceri",
	        joinColumns = @JoinColumn(
	                name = "listaTraduceriId",
	                referencedColumnName = "id"
	        ),
	        inverseJoinColumns = @JoinColumn(
	                name = "autorId",
	                referencedColumnName = "id"
	        )
	)
	private Set<Autor> autori;
	
	@Column(name = "titluTradus", nullable = false)
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.traducere.titlu.mandatory}")
	@Length(min = 3, message = "{dao.traducere.titlu.length}")
	private String titlu;
	
	@Column(name = "anTraducere")
	private Integer an;
	
	@Column(name = "alteDetalii")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.traducere.detalii.mandatory}")
	@Length(min = 3, message = "{dao.traducere.detalii.length}")
	private String detalii;
	
	//@TODO ASTA NU E OK TREBUIE GADIT UNDE E SCOASA
	@Column(name = "limbaTrad")
	@Type(type = "trimmedStringType")
	private String limbaTrad;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


//	public Traducator getTraducator() {
//		return traducator;
//	}
//
//
//	public void setTraducator(Traducator traducator) {
//		this.traducator = traducator;
//	}


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


	public void setAn(Integer anTraducere) {
		this.an = anTraducere;
	}


	public String getDetalii() {
		return detalii;
	}


	public void setDetalii(String detalii) {
		this.detalii = detalii;
	}


	@Override
	public String toString() {
		return "Traducere [id=" + id +  ", autori=" /*+ autori*/ + ", titlu=" + titlu
				+ ", anTraducere=" + an + ", detalii=" + detalii + "]";
	}


	public String getLimbaTrad() {
		return limbaTrad;
	}


	public void setLimbaTrad(String limbaTrad) {
		this.limbaTrad = limbaTrad;
	}



}
