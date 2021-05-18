/*
Jingwei ZUO, Yixuan ZHANG
M2 DataScale, MicroProjet de J2EE
*********************************
In this class, we've defined a stateful session bean for "Auction manager"
The private methods:
	- private Boolean manip_right(int objetid)
		:Verify that the object belongs to user
	- private Usager findUser(int userid)
		:get informations from Directory manager
	- private Objet findObjet(int objetid)
		:get informations from Directory manager
	- private String getUserpwd()
		:get informations from Directory manager
	- private Auction findAuction(int objetid)
		:get information for DB

The public methods: 
	+ Boolean user_auth(int userid, String pwd);
		:Authentifier le client
	+ Boolean addAuction(int objetid, double startingprice, int maxduration, double increment_bidding);
	+ Boolean startAuction(int objetid);
	+ Boolean closeAuction(int objetid);
	+ Boolean bid(int objetid);
	+ String getAuctionlist(String state);
	+ public Future<String> getObjetinfo(int objetid);
	+ public Future<String> getAuctioninfo(int objetid);
*/

package enterprise.entity_bean_ejb;

//importer l'interface à implémenter
import enterprise.entity_bean_api.Auction_manager;
import enterprise.entity_bean_api.Dir_manager;
//importer les entités à utiliser
import enterprise.entity_bean_entity.Auction;
import enterprise.entity_bean_entity.Objet;
import enterprise.entity_bean_entity.Usager;
import java.lang.StringBuilder;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateful;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;




/**
 * The stateful session bean.
 */
@Stateful
public class Auction_managerBean implements Auction_manager {
  /**
   * the reference to the entity manager, which persistence context is "pu1".
   */
  @PersistenceContext(unitName = "pu1")
  private EntityManager em;
  private String password;
  private boolean userauth = false;
  private Dir_manager dm;
  public int userid;

  /**********************************************
   * Les méthodes privées.
   **********************************************/

  /*
   * La méthode "manip_right" sert à vérifier si l'objet est dans la liste d'utilsateur. Sinon,
   * l'utilisateur n'aura pas de droit (addAuction, startAuction, closeAuction) sur cet objet
   */
  private Boolean manip_right(int objetid) {
    Boolean right = null;
    // Consulter le contexte pour instancier l'objet de Dir_manager
    try {
      InitialContext ic = new InitialContext();
      dm = (Dir_manager) ic.lookup("enterprise.entity_bean_api.Dir_manager");
      right = dm.manip_right(userid, objetid);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return right;
  }

  private Usager findUser(int userid) {
    Usager usager = null;
    // Consulter le contexte pour instancier l'objet de Dir_manager
    try {
      InitialContext ic = new InitialContext();
      dm = (Dir_manager) ic.lookup("enterprise.entity_bean_api.Dir_manager");
      usager = dm.findUser(userid);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return usager;
  }

  private Objet findObjet(int objetid) {
    Objet objet = null;
    // Consulter le contexte pour instancier l'objet de Dir_manager
    try {
      InitialContext ic = new InitialContext();
      dm = (Dir_manager) ic.lookup("enterprise.entity_bean_api.Dir_manager");
      objet = dm.findObjet(objetid);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return objet;
  }

  private String getUserpwd() {
    String pwd = null;
    // Consulter le contexte pour instancier l'objet de Dir_manager
    try {
      InitialContext ic = new InitialContext();
      dm = (Dir_manager) ic.lookup("enterprise.entity_bean_api.Dir_manager");
      pwd = dm.getUserpwd(this.userid);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return pwd;
  }

  private Auction findAuction(int objetid) {
    Query q = em.createQuery("select a from Auction a where a.objet_id = :objetid");
    q.setParameter("objetid", objetid);
    // Si on utiliser getSingleResult(), au cas ou la table est vide, il y aura une erreur
    // return (Auction) q.getSingleResult();
    // En utilisant getResultList, on peut évider les erreurs causées par "table vide"
    @SuppressWarnings("unchecked")
    List<Auction> results = q.getResultList();
    Auction auction = null;
    if (!results.isEmpty()) {
      auction = (Auction) results.get(0);
    }
    return auction;
  }

  /**********************************************
   * Les méthodes publics.
   **********************************************/
  @Override
  // Authentifier le client avec l'id et le mot de passe entrés.
  public Boolean user_auth(int userid, String pwd) {
    this.userid = userid;
    password = pwd;
    if (password.equals(getUserpwd())) {
      userauth = true;
      return true;
    } else {
      userauth = false;
      return false;
    }
  }

  @Override
  public Boolean addAuction(int objetid, double startingprice, int maxduration,
      double incrementbidding) {
    Auction auction = new Auction();
    em.persist(auction);
    // Vérifier si le client a été authentifié et si l'objet est dans la liste de client
    if (userauth && manip_right(objetid)) {
      auction.setobjet_id(objetid);
      auction.setuser_id(userid);
      auction.setstate("not started");
      auction.setstarting_price(startingprice);
      auction.setcurrent_price(startingprice);
      auction.setmax_duration(maxduration);
      auction.setinc_bidding(incrementbidding);
      return true;
    }
    return false;
  }

  @Override
  public Boolean startAuction(int objetid) {
    Auction auction = findAuction(objetid);
    em.persist(auction);
    // Vérifier si le client a été authentifié et si l'objet est dans la liste de client
    if (userauth && manip_right(objetid)) {
      // vérifier l'état d'auction, si et seulement si l'état est "not started".
      if (auction.getstate().equals("not started")) {
        auction.setstate("active");
        return true;
      }
    }
    return false;
  }

  @Override
  public Boolean closeAuction(int objetid) {
    Auction auction = findAuction(objetid);
    em.persist(auction);
    // Vérifier si le client a été authentifié et si l'objet est dans la liste de client
    if (userauth && manip_right(objetid)) {
      // vérifier l'état d'auction, si et seulement si l'état est "active", le client peut fermer
      // l'auction
      if (auction.getstate().equals("active")) {
        auction.setstate("closed");
        return true;
      }
    }
    return false;
  }

  @Override
  public Boolean bid(int objetid) {
    if (userauth) {
      System.out.println(new Date().toString() + " - Bidding arrived ");
      // Touver l'auction de cet objet
      Auction auction = findAuction(objetid);
      em.persist(auction);
      // vérifier l'état d'auction, si et seulement si l'état est "active" et le bide ne dépasse pas
      // la
      // duréé maximale
      if (auction.getstate().equals("active")
          && (auction.getduration() < auction.getmax_duration())) {
        // le bid est réussi, ajouter 1 pour la duréé de l'auction, additionner l'increment pour le
        // prix
        auction.setduration(auction.getduration() + 1);
        auction.setid_bidder(userid);
        auction.setcurrent_price(auction.getcurrent_price() + auction.getinc_bidding());
        return true;
      }
    }
    return false;
  }

  @Override
  public String getAuctionlist(String state) {
    String objetid = null;
    // Filtrer le format de l'entrée pour éviter l'injection SQL
    if (state.equals("not started") || state.equals("active") || state.equals("closed")) {
      Query q = em.createQuery("select a.objet_id from Auction a where a.state = :state");
      q.setParameter("state", state);
      // return (Reservation) q.getSingleResult();
      // En utilisant "List" pour assurer que le resultat retouné soit correct
      @SuppressWarnings("unchecked")
      List<Integer> results = q.getResultList();
      ListIterator<Integer> it = results.listIterator();
      StringBuilder stringBuilder = new StringBuilder();
      if (!results.isEmpty()) {
        while (it.hasNext()) {
          // Composer un String pour retourner une liste de auction_id selon l'état de l'auction
          stringBuilder.append(it.next() + "; ");
        }
      }
      objetid = stringBuilder.toString();
    }
    return objetid;
  }

  // les méthodes asynchones pour consulter les informations dans le serveur
  @Override
  @Asynchronous
  public Future<String> getObjetinfo(int objetid) {
    String objetinfo = null;
    try {
      InitialContext ic = new InitialContext();
      dm = (Dir_manager) ic.lookup("enterprise.entity_bean_api.Dir_manager");
      objetinfo = dm.getObjetinfo(objetid);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new AsyncResult<String>(objetinfo);
  }

  @Override
  @Asynchronous
  public Future<String> getAuctioninfo(int objetid) {
    Auction auction = findAuction(objetid);
    return new AsyncResult<String>(auction.toString());
  }
}
