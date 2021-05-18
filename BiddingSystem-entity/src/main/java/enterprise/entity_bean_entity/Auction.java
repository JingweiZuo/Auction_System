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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * The entity.
 */

@Entity
@Table(name = "Auction")
public class Auction implements Serializable {
  private static final long serialVersionUID = 2L;
  private int auction_id;
  private int user_id;
  private int objet_id;
  private String state;
  private int duration;
  private int max_duration;
  private double starting_price;
  private double current_price;
  private double inc_bidding;
  private int id_bidder;

  /**
   * gets the auction_id.
   * 
   * @return the auction_id.
   */
  @Id
  //Auction_ID est généré par un generator, mettre la valeur initiale à 1
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "auctionGenerator")  
  @SequenceGenerator(name = "auctionGenerator", sequenceName = "Auction_sequence", 
      initialValue = 1, allocationSize = 1)

  @Column(name = "Auction_ID")
  public int getauction_id() {
    return auction_id;
  }
  /**
   * sets the auction_id.
   * 
   * @param id
   *            the new auction_id.
   */
  
  public void setauction_id(final int id) {
    this.auction_id = id;
  }

  /**
   * gets the user_id.
   * 
   * @return the user_id.
   */
  @Column(name = "user_id")
  public int getuser_id() {
    return user_id;
  }
  /**
   * sets the user_id.
   * 
   * @param id
   *            the new user_id.
   */
  
  public void setuser_id(final int id) {
    this.user_id = id;
  }

  /**
   * gets the objet_id.
   * 
   * @return the objet_id.
   */
  @Column(name = "objet_id")
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
   * gets the state.
   * 
   * @return the state.
   */
  @Column(name = "state")
  public String getstate() {
    return state;
  }
  /**
   * sets the state.
   * 
   * @param state
   *            the new state.
   */
  
  public void setstate(final String state) {
    this.state = state;
  }

  /**
   * gets the duration.
   * 
   * @return the duration.
   */
  @Column(name = "duration")
  public int getduration() {
    return duration;
  }
  /**
   * sets the duration.
   * 
   * @param duration
   *            the new duration.
   */
  
  public void setduration(final int duration) {
    this.duration = duration;
  }

  /**
   * gets the max_duration.
   * 
   * @return the max_duration.
   */
  @Column(name = "max_duration")
  public int getmax_duration() {
    return max_duration;
  }
  /**
   * sets the max_duration.
   * 
   * @param max_duration
   *            the new max_duration.
   */
  
  public void setmax_duration(final int max_duration) {
    this.max_duration = max_duration;
  }

  /**
   * gets the starting_price.
   * 
   * @return the starting_price.
   */
  @Column(name = "starting_price")
  public double getstarting_price() {
    return starting_price;
  }
  /**
   * sets the starting_price.
   * 
   * @param starting_price
   *            the new max_dustarting_priceration.
   */
  
  public void setstarting_price(final double starting_price) {
    this.starting_price = starting_price;
  }

  /**
   * gets the current_price.
   * 
   * @return the current_price.
   */
  @Column(name = "current_price")
  public double getcurrent_price() {
    return current_price;
  }
  /**
   * sets the current_price.
   * 
   * @param current_price
   *            the new current_price.
   */
  
  public void setcurrent_price(final double current_price) {
    this.current_price = current_price;
  }

  /**
   * gets the inc_bidding.
   * 
   * @return the inc_bidding.
   */
  @Column(name = "inc_bidding")
  public double getinc_bidding() {
    return inc_bidding;
  }
  /**
   * sets the inc_bidding.
   * 
   * @param inc_bidding
   *            the new inc_bidding.
   */
  
  public void setinc_bidding(final double inc_bidding) {
    this.inc_bidding = inc_bidding;
  }

  /**
   * gets the id_bidder.
   * 
   * @return the id_bidder.
   */
  @Column(name = "id_bidder")
  public int getid_bidder() {
    return id_bidder;
  }
  /**
   * sets the id_bidder.
   * 
   * @param id_bidder
   *            the new id_bidder.
   */
  
  public void setid_bidder(final int id_bidder) {
    this.id_bidder = id_bidder;
  }
  
  //Retourner les informations sur l'auction
  @Override
  public String toString() {
    return " objet_id is: " + objet_id + "\n state is: " + state + "\n duration is: " 
      + duration + "\n max_duration is: " + max_duration 
          + "\n starting_price is: " + starting_price + "\n current_price is: " 
            + current_price + "\n increment for bidding is: " 
              + inc_bidding + "\n the id of last bidder is: " + id_bidder;
  }
}