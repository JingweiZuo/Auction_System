package enterprise.entity_bean_entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The order of the customers.
 */
@Entity
@Table(name = "ObjetTABLE")
public class Objet {
  private int objet_id;
  private int user_id;
  private String desc;
  private String cat;
  private Usager Usager;

  /**
   * gets the objet_id.
   * 
   * @return the objet_id.
   */
  @Id
  //OBJET_ID est généré par un generator, mettre la valeur initiale à 1
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "objetGenerator")  
  @SequenceGenerator(name = "objetGenerator", sequenceName = "objet_sequence", 
      initialValue = 1, allocationSize = 1)
  @Column(name = "OBJET_ID")
  public int getobjet_id() {
    return objet_id;
  }
  /**
   * sets the objet_id.
   * 
   * @param id
   *            the new objet_id.
   */
  
  public void setobjet_id(final int id) {
    this.objet_id = id;
  }

  /**
   * gets the desc.
   * 
   * @return the desc.
   */
  @Column(name = "Description")
  public String getdesc() {
    return this.desc;
  }
  /**
   * sets the desc.
   * 
   * @param desc
   *            the new desc.
   */
  
  public void setdesc(final String desc) {
    this.desc = desc;
  }

  /**
   * gets the Category.
   * 
   * @return the Category.
   */
  @Column(name = "Category")
  public String getcat() {
    return cat;
  }
  /**
   * sets the Category.
   * 
   * @param cat
   *            the new Category.
   */
  
  public void setcat(final String cat) {
    this.cat = cat;
  }
  
  /**
   * gets the usager.
   * 
   * @return the usager.
   */
  @ManyToOne()
  @JoinColumn(name = "Usager_ID")
  public Usager getUsager() {
    return Usager;
  }
  /**
   * sets the usager.
   * 
   * @param usager
   *            the usager.
   */
  
  public void setUsager(final Usager usager) {
    this.Usager = usager;
  }

  //Retourner les informations sur l'objet
  @Override
  public String toString() {
    return " Description of object: " + desc + "\n Category of objet: " + cat;
  }
}