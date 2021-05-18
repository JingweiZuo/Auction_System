package enterprise.entity_bean_entity;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * The entity.
 */

@Entity
@Table(name = "Usager")
public class Usager implements Serializable {
  private static final long serialVersionUID = 1L;

  // primary key
  private int user_id;
  private String password;
  private String prenom;
  private String nom;
  private String addr;
  private String email;
  private int droits;
  private Collection<Objet> Objets = new ArrayList<Objet>();

  
  /**
   * gets the user_id.
   * 
   * @return the user_id.
   */
  @Id
  //user_id est généré par un generator, mettre la valeur initiale à 1
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "usagerGenerator")  
  @SequenceGenerator(name = "usagerGenerator", sequenceName = "Usager_sequence", 
      initialValue = 1, allocationSize = 1)

  @Column(name = "user_id")
  public int getuser_id() {
    return user_id;
  }
  /**
   * sets the identifier.
   * 
   * @param id
   *            the new identifier.
   */
  
  public void setuser_id(final int id) {
    this.user_id = id;
  }

  /**
   * gets the password.
   * 
   * @return the password.
   */

  public String getpassword() {
    return password;
  }
  /**
   * sets the password.
   * 
   * @param password
   *            the password.
   */
  
  public void setpassword(final String password) {
    this.password = password;
  }

  /**
   * gets the firstname.
   * 
   * @return the firstname.
   */

  public String getprenom() {
    return prenom;
  }
  /**
   * sets the firstname.
   * 
   * @param firstname
   *            the firstname.
   */
  
  public void setprenom(final String firstname) {
    this.prenom = firstname;
  }

  /**
   * gets the nom.
   * 
   * @return the nom.
   */
  public String getnom() {
    return nom;
  }

  /**
   * sets the nom.
   * 
   * @param name
   *            the nom.
   */
  public void setnom(final String name) {
    this.nom = name;
  }

  /**
   * gets the address.
   * 
   * @return the address.
   */
  public String getaddr() {
    return addr;
  }
  /**
   * sets the address.
   * 
   * @param address
   *            the address.
   */
  
  public void setaddr(final String address) {
    this.addr = address;
  }

  /**
   * gets the email.
   * 
   * @return the email.
   */
  public String getemail() {
    return email;
  }
  /**
   * sets the email.
   * 
   * @param mail
   *            the email.
   */
  
  public void setemail(final String mail) {
    this.email = mail;
  }

  /**
   * sets the rights of the user.
   * 
   * @param rights
   *            the new rights.
   */
  public void setdroits(final int rights) {
    this.droits = rights;
  }
  /**
   * gets the rights of the user.
   * 
   * @return the rights.
   */
  
  public int getdroits() {
    return droits;
  }

  /**
   * gets the Objects of user.
   * 
   * @return the Objets.
   */
  @OneToMany(cascade = ALL, mappedBy = "Usager")
  public Collection<Objet> getObjets() {
    return Objets;
  }
  /**
   * sets the collection of orders.
   * 
   * @param newValue
   *            the new collection.
   */
  
  public void setObjets(final Collection<Objet> newValue) {
    this.Objets = newValue;
  }

}
