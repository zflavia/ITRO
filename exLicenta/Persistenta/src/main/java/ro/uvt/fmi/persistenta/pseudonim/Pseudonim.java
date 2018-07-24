package ro.uvt.fmi.persistenta.pseudonim;

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

import ro.uvt.fmi.persistenta.autor.Autor;
import ro.uvt.fmi.persistenta.util.DaoITRO;
import ro.uvt.fmi.persistenta.util.NotNullStringType;

@Entity
@Table(name = "AliasTraducator")
@TypeDefs({ @TypeDef(name = "trimmedStringType", typeClass = NotNullStringType.class) })
@NamedQueries({ @NamedQuery(name = Pseudonim.QUERY_ALL, query = "from Pseudonim a ORDER BY a.alias")
})
public class Pseudonim extends DaoITRO{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	//@Expose
	private Long id;
	public static final String QUERY_ALL = "ro.uvt.fmi.persistenta.pseudonim.query.all";

	@Column(name = "alias", nullable = false)
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.pseudonim.alias.mandatory}")
	@Length(min = 3, message = "{dao.pseudonim.alias.length}")
	private String alias;
	
	
	@Column(name = "description", nullable = false)
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.pseudonim.alias.mandatory}")
	@Length(min = 3, message = "{dao.pseudonim.alias.length}")
	private String detalii;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getDetalii() {
		return detalii;
	}


	public void setDetalii(String detalii) {
		this.detalii = detalii;
	}

}
