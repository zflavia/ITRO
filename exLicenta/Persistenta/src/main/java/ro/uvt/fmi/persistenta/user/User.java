package ro.uvt.fmi.persistenta.user;

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
@Table(name = "Usr")

@TypeDefs({ @TypeDef(name = "trimmedStringType", typeClass = NotNullStringType.class) })

@NamedQueries({ @NamedQuery(name = User.QUERY_BY_USER_NANE, query = "from User u where u.userName = :username"), 
	@NamedQuery(name = User.QUERY_BY_ACTIVE_USERS, query = "from User u where u.status = :status"),
	@NamedQuery(name = User.QUERY_BY_ACTIVE_AND_SCIENTIST_USERS, query = "from User u where u.status = :status And type = :type")})

public class User extends DaoITRO {
	public static final String QUERY_BY_USER_NANE = "queryByUserName";
	public static final String QUERY_BY_ACTIVE_USERS="user_queryByActiveUsers";
	public static final String QUERY_BY_ACTIVE_AND_SCIENTIST_USERS="user_queryByActiveAndScientistUsers";
	
	private static final long serialVersionUID = 1L;

	
	public static enum USER_STATUS{ACTIVE("activ"), INACTIVE("inactiv");
		String label;
		USER_STATUS(String l ){this.label=l;}
		public String getLabel(){return this.label;}}
	
	public static enum USER_TYPE{USER("utilizator"), SCIENTIST("culegator");
		String label;
		USER_TYPE(String l ){this.label=l;}
		public String getLabel(){return this.label;}}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "userName", unique = true)
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.user.usernameBlank}")
	@Length(min = 5, message = "{dao.user.longime.username}")
	private String userName;
	
	
	@Column(name = "lastName")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.user.usernameBlank}")
	@Length(min = 2, message = "{dao.user.lastName}")
	private String lastName;
	
	@Column(name = "firstName")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.user.usernameBlank}")
	@Length(min = 2, message = "{dao.user.firstName}")
	private String firstName;

	
	@Column(name = "alias", unique = true)
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.user.alias.null}")
	@Length(min = 2, message = "{dao.user.alias.length}")
	private String alias;
	
	@Column(name = "password")
	@Type(type = "trimmedStringType")
	@NotBlank(message = "{dao.user.passwordBlank}")
	@Length(min = 4, message = "{dao.user.password.length}")
	private String password;
	
	@Column(name="status")
	private String status;

	
	@Column(name="type")
	private String type;

	// @OneToMany(cascade = CascadeType.ALL)
	// @JoinTable(name = "tblUserRole", joinColumns = @JoinColumn(name =
	// "userId"), inverseJoinColumns = @JoinColumn(name = "rolename"))
	// private Set<Role> roles;

	public User() {

	}

	public User(String username, String password) {
		this.userName = username;
		this.password = password;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + "]";
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

}
