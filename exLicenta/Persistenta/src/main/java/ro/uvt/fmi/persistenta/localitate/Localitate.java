package ro.uvt.fmi.persistenta.localitate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ro.uvt.fmi.persistenta.judet.Judet;
import ro.uvt.fmi.persistenta.tara.Tara;
import ro.uvt.fmi.persistenta.util.DaoITRO;
import ro.uvt.fmi.persistenta.util.NotNullStringType;

@Entity
@Table(name = "Localitate")

@TypeDefs({ @TypeDef(name = "trimmedStringType", typeClass = NotNullStringType.class) })
@NamedQueries({ @NamedQuery(name = Localitate.QUERY_ALL, query = "from Localitate l ORDER BY l.nume") })

public class Localitate extends DaoITRO {
	public static final String QUERY_ALL = "ro.uvt.fmi.persistenta.localitate.queryAll";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nume", unique = true)
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.localitate.nume.mandatory}")
	@Length(min = 3, message = "{dao.localitate.nume.length}")
	private String nume;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "judetId")
	private Judet judet;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "taraId")
	private Tara tara;

	@Override
	public String toString() {

		return "Localitate [id=" + id + ", userName=" + nume + ", judet=" + judet + ", tara=" + tara + "]";
	}

	public Judet getJudet() {
		return judet;
	}

	public void setJudet(Judet judet) {
		this.judet = judet;
	}

	public Tara getTara() {
		return tara;
	}

	public void setTara(Tara tara) {
		this.tara = tara;
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
