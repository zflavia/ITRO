package ro.uvt.fmi.persistenta.traducator;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ro.uvt.fmi.persistenta.bibbliografie.Bibliografie;
import ro.uvt.fmi.persistenta.bibbliografie.BibliografieB;
import ro.uvt.fmi.persistenta.bibbliografie.BibliografieBPAT;
import ro.uvt.fmi.persistenta.localitate.Localitate;
import ro.uvt.fmi.persistenta.pseudonim.Pseudonim;
import ro.uvt.fmi.persistenta.referinte.VizibilitateBDD;
import ro.uvt.fmi.persistenta.traduceri.Dictionar;
import ro.uvt.fmi.persistenta.traduceri.EditiiModerne;
import ro.uvt.fmi.persistenta.traduceri.EditiiVechi;
import ro.uvt.fmi.persistenta.traduceri.Manual;
import ro.uvt.fmi.persistenta.traduceri.Manuscris;
import ro.uvt.fmi.persistenta.traduceri.TraduceriFaraIndicatii;
import ro.uvt.fmi.persistenta.user.User;
import ro.uvt.fmi.persistenta.util.DaoITRO;
import ro.uvt.fmi.persistenta.util.NotNullStringType;

@Entity
@Table(name = "Traducator")

@TypeDefs({ @TypeDef(name = "trimmedStringType", typeClass = NotNullStringType.class) })
@NamedQueries({ @NamedQuery(name = Traducator.QUERY_ALL, query = "from Traducator t)"), })
// @NamedQuery(name = Traducator.QUERY_ALL_NAMES, query = "t.id, t.nume,
// t.prenume from Traducator t)")
public class Traducator extends DaoITRO {

	public static final String QUERY_ALL = "query.tarducator.all";
	public static final String QUERY_ALL_NAMES = "query.tarducator.all.just.name.id.lastname";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nume")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.traducator.nume.mandatory}")
	@Length(min = 3, message = "{dao.traducator.nume.length}")
	private String nume;

	@Column(name = "prenume")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.traducator.prenume.mandatory}")
	@Length(min = 2, message = "{dao.traducator.prenume.length}")
	private String prenume;

	@Column(name = "aspecteBibliografice")
	@Type(type = "trimmedStringType")
	private String aspecteBibliografice;

	@Column(name = "atributiiContributiiEditorialeSociale")
	@Type(type = "trimmedStringType")
	private String atributiiContributiiEditoriale;

	@Column(name = "activitateTraducator")
	@Type(type = "trimmedStringType")
	private String activitateTraducator;

	@Column(name = "dataNastere")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.traducator.dataNastere.mandatory}")
	@Length(min = 2, message = "{dao.traducator.dataNastere.length}")
	private String dataNastere;

	@Column(name = "dataDeces")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.traducator.dataDeces.mandatory}")
	@Length(min = 2, message = "{dao.traducator.dataDeces.length}")
	private String dataDeces;
	
	@Column(name = "foto")
	@Type(type = "trimmedStringType")
	private String foto;

	@OneToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "localitateNastereId")
	Localitate localitateNastere;

	@OneToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "localitateDecesId")
	Localitate localitateDeces;

	// @Column(name = "prelucratDe")
	@OneToOne // (fetch = FetchType.LAZY)
	// @PrimaryKeyJoinColumn
	@JoinColumn(name = "prelucratDe")
	User prelucratDe;

	//
	@OneToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "introdusDe")
	User preluatDe;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "traducatorId")
	Set<Manuscris> traduceriManuscrise;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = EditiiVechi.class)
	@JoinColumn(name = "traducatorId")
	Set<EditiiVechi> editiiVechi;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = EditiiModerne.class)
	@JoinColumn(name = "traducatorId")
	Set<EditiiModerne> editiiModerne;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = TraduceriFaraIndicatii.class)
	@JoinColumn(name = "traducatorId")
	Set<TraduceriFaraIndicatii> traduceriFaraIndicatii;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = Dictionar.class)
	@JoinColumn(name = "traducatorId")
	Set<Dictionar> dictionare;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = Manual.class)
	@JoinColumn(name = "traducatorId")
	Set<Manual> manuale;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = BibliografieB.class)
	@JoinColumn(name = "traducatorId")
	Set<BibliografieB> bibliografie;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = BibliografieBPAT.class)
	@JoinColumn(name = "traducatorId")
	Set<BibliografieBPAT> bibliografieBPAT;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = VizibilitateBDD.class)
	@JoinColumn(name = "traducatorId")
	//@ElementCollection(fetch = FetchType.EAGER)
	Set<VizibilitateBDD> vizibilitateBDD;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = Pseudonim.class)
	@JoinColumn(name="traducatorId")
	Set<Pseudonim> pseudonime;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataNastere() {
		return dataNastere;
	}

	public void setDataNastere(String dataNastere) {
		this.dataNastere = dataNastere;
	}

	public String getDataDeces() {
		return dataDeces;
	}

	public void setDataDeces(String dataDeces) {
		this.dataDeces = dataDeces;
	}

	public Localitate getLocalitateNastere() {
		return localitateNastere;
	}

	public void setLocalitateNastere(Localitate localitateNastere) {
		this.localitateNastere = localitateNastere;
	}

	public Localitate getLocalitateDeces() {
		return localitateDeces;
	}

	public void setLocalitateDeces(Localitate localitateDeces) {
		this.localitateDeces = localitateDeces;
	}

	public User getPreluatDe() {
		return preluatDe;
	}

	public void setPreluatDe(User preluatDe) {
		this.preluatDe = preluatDe;
	}

	public String getAspecteBibliografice() {
		return aspecteBibliografice;
	}

	public void setAspecteBibliografice(String aspecteBibliografice) {
		this.aspecteBibliografice = aspecteBibliografice;
	}

	public String getAtributiiContributiiEditoriale() {
		return atributiiContributiiEditoriale;
	}

	public void setAtributiiContributiiEditoriale(String atributiiContributiiEditoriale) {
		this.atributiiContributiiEditoriale = atributiiContributiiEditoriale;
	}

	public String getActivitateTraducator() {
		return activitateTraducator;
	}

	public void setActivitateTraducator(String activitateTraducator) {
		this.activitateTraducator = activitateTraducator;
	}

	public Set<Manuscris> getTraduceriManuscrise() {
		return traduceriManuscrise;
	}

	public void setTraduceriManuscrise(Set<Manuscris> traduceriManuscrise) {
		this.traduceriManuscrise = traduceriManuscrise;
	}

	@Override
	public String toString() {
		return "Traducator [id=" + id + ", nume=" + nume + ", prenume=" + prenume + ", aspecteBibliografice="
				+ aspecteBibliografice + ", atributiiContributiiEditoriale=" + atributiiContributiiEditoriale
				+ ", activitateTraducator=" + activitateTraducator + ", dataNastere=" + dataNastere + ", dataDeces="
				+ dataDeces + ", localitateNastere=" + localitateNastere + ", localitateDeces=" + localitateDeces
				+ ", prelucratDe=" + prelucratDe + ", preluatDe=" + preluatDe + ", traduceriManuscrise="
				/*+ traduceriManuscrise*/ + "]";
	}

	public User getPrelucratDe() {
		return prelucratDe;
	}

	public void setPrelucratDe(User prelucratDe) {
		this.prelucratDe = prelucratDe;
	}

	public Set<EditiiVechi> getEditiiVechi() {
		return editiiVechi;
	}

	public void setEditiiVechi(Set<EditiiVechi> editiiVechi) {
		this.editiiVechi = editiiVechi;
	}

	public Set<EditiiModerne> getEditiiModerne() {
		return editiiModerne;
	}

	public void setEditiiModerne(Set<EditiiModerne> editiiModerne) {
		this.editiiModerne = editiiModerne;
	}

	public Set<TraduceriFaraIndicatii> getTraduceriFaraIndicatii() {
		return traduceriFaraIndicatii;
	}

	public void setTraduceriFaraIndicatii(Set<TraduceriFaraIndicatii> traduceriFaraIndicatii) {
		this.traduceriFaraIndicatii = traduceriFaraIndicatii;
	}

	public Set<Dictionar> getDictionare() {
		return dictionare;
	}

	public void setDictionare(Set<Dictionar> dictionare) {
		this.dictionare = dictionare;
	}

	public Set<Manual> getManuale() {
		return manuale;
	}

	public void setManuale(Set<Manual> manuale) {
		this.manuale = manuale;
	}



	public Set<VizibilitateBDD> getVizibilitateBDD() {
		return vizibilitateBDD;
	}

	public void setVizibilitateBDD(Set<VizibilitateBDD> vizibilitateBDD) {
		this.vizibilitateBDD = vizibilitateBDD;
	}

	public Set<BibliografieB> getBibliografie() {
		return bibliografie;
	}

	public void setBibliografie(Set<BibliografieB> bibliografie) {
		this.bibliografie = bibliografie;
	}

	public Set<BibliografieBPAT> getBibliografieBPAT() {
		return bibliografieBPAT;
	}

	public void setBibliografieBPAT(Set<BibliografieBPAT> bibliografieBPAT) {
		this.bibliografieBPAT = bibliografieBPAT;
	}

	public Set<Pseudonim> getPseudonime() {
		return pseudonime;
	}

	public void setPseudonime(Set<Pseudonim> pseudonime) {
		this.pseudonime = pseudonime;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}


}
