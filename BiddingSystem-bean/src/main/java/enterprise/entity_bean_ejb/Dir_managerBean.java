/*
Jingwei ZUO, Yixuan ZHANG
M2 DataScale, MicroProjet de J2EE
*********************************
In this class, we've defined a stateful session bean for "Auction manager"

The public methods: 
	------------Methods which need admin right-------------
	+ boolean adminauth(String pwd);
		:Authentifier the administrator
	+ int addUser(String prenom, String nom, String password, String addr, String email, int droits);
	+ void removeUser(int userid);
	+ int addObjet(int userid, String desc, String cat);
	+ void removeObjet(int objetid);
	+ void updateRights(int userid, int rightsvar);
	------------Methods which don't need admin right and can be called int other class-------------
	+ int lookupRights(int userid);
	+ Usager findUser(int userid);
	+ Objet findObjet(int objetid);
	+ String getUserpwd(int userid);
	+ String getObjetinfo(int objetid);
	+ String getObjetlist();
	+ Boolean manip_right(int userid, int objetid);
*/

package enterprise.entity_bean_ejb;

import enterprise.entity_bean_api.Dir_manager;
import enterprise.entity_bean_entity.Objet;
import enterprise.entity_bean_entity.Usager;

import java.lang.StringBuilder;
import java.util.Collection;
import java.util.Iterator;

import java.util.List;
import java.util.ListIterator;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;

/**
 * The stateful session bean.
 */

@Stateful
public class Dir_managerBean implements Dir_manager {

  /**
   * the reference to the entity manager, which persistence context is "pu1".
   */
  @PersistenceContext(unitName = "pu1")
  private EntityManager em;
  private String adminpwd = "admin";
  private boolean adminauth = false;

  @Override
  public boolean admin_auth(String pwd) {
    if (adminpwd.equals(pwd)) {
      adminauth = true;
      return true;
    } else {
      adminauth = false;
      return false;
    }
  }

  @Override
  public int addUser(String prenom, String nom, String password, String addr, String email,
      int droits) {
    if (adminauth) {
      Usager usager = new Usager();
      em.persist(usager);
      usager.setprenom(prenom);
      usager.setnom(nom);
      // Ajouter le mot de passe pour l'utilisateur
      usager.setpassword(password);
      usager.setaddr(addr);
      usager.setemail(email);
      usager.setdroits(droits);
      return usager.getuser_id(); 
    } else {
      return 0;
    }
  }

  @Override
  public void removeUser(int userid) {
    if (adminauth) {
      // Supprimer les users et leur objets
      Usager usager = findUser(userid);
      if (usager != null) {
        em.persist(usager);
        // Chercher et supprimer tous les objets de ce usager
        Iterator<Objet> it = usager.getObjets().iterator();
        while (it.hasNext()) {
          Objet objet = (Objet) it.next();
          em.persist(objet);
          removeObjet(objet.getobjet_id());
        }
        // supprimer l'usager selon userid
        // Query q = em.createQuery("delete u from Usager u where u.userid = :userid");
        // q.setParameter("userid", userid);
        em.remove(usager);
      }
    }
  }

  @Override
  public int addObjet(int userid, String desc, String cat) {
    if (adminauth) {
      Objet objet = new Objet();
      em.persist(objet);
      Usager usager = findUser(userid);
      usager.getObjets().add(objet);
      objet.setUsager(usager);
      objet.setdesc(desc);
      objet.setcat(cat);
      this.updateRights(userid, -1);
      return objet.getobjet_id();
    } else {
      return 0;
    }
  }

  @Override
  public void removeObjet(int objetid) {
    if (adminauth) {
      Objet objet = findObjet(objetid);
      if (objet != null) {
        em.persist(objet);
        Usager usager = objet.getUsager();
        usager.getObjets().remove(objet);
        // increment 1 for user's rights
        usager.setdroits(usager.getdroits() + 1);
        // delete elements in objet table
        // Delete all records.
        em.remove(objet);
      }
    }
  }

  @Override
  public void updateRights(int userid, int rightsvar) {
    if (adminauth) {
      Usager usager = findUser(userid);
      em.persist(usager);
      usager.setdroits(usager.getdroits() + rightsvar);
    }
  }

  @Override
  public int lookupRights(int userid) {
    Usager usager = findUser(userid);
    if (usager == null) {
      return 0;
    } else {
      em.persist(usager);
      return usager.getdroits();
    }
  }

  @Override
  // use getResultList to ensure the correctness
  public Usager findUser(final int userid) {
    Query q = em.createQuery("select s from Usager s where s.user_id = :userid");
    q.setParameter("userid", userid);
    // return (Usager) q.getSingleResult();
    @SuppressWarnings("unchecked")
    List<Usager> results = q.getResultList();
    Usager usager = null;
    if (!results.isEmpty()) {
      usager = (Usager) results.get(0);
    }
    return usager;
  }

  @Override
  // use getResultList to ensure the correctness
  public Objet findObjet(final int objetid) {
    Query q = em.createQuery("select o from Objet o where o.objet_id = :objetid");
    q.setParameter("objetid", objetid);
    // return (Reservation) q.getSingleResult();
    // En utilisant "List" pour assurer que le resultat retouné soit correct
    @SuppressWarnings("unchecked")
    List<Objet> results = q.getResultList();
    Objet objet = null;
    if (!results.isEmpty()) {
      objet = (Objet) results.get(0);
    }
    return objet;
  }

  @Override
  public String getUserpwd(int userid) {
    Usager usager = findUser(userid);
    // S'il existe un usager dans la base de données
    if (usager != null) {
      em.persist(usager);
      return usager.getpassword();
    } else {
      return null;
    }
  }

  @Override
  public String getObjetinfo(int objetid) {
    Objet objet = findObjet(objetid);
    // S'il existe un objet dans la base de données
    if (objet != null) {
      em.persist(objet);
      return objet.toString();
    } else {
      return null;
    }
  }

  @Override
  public String getObjetlist() {
    String objetid = null;
    Query q = em.createQuery("select o.objet_id from Objet o");
    // return (Reservation) q.getSingleResult();
    // En utilisant "List" pour assurer que le resultat retouné soit correct
    @SuppressWarnings("unchecked")
    List<Integer> results = q.getResultList();
    ListIterator<Integer> it = results.listIterator();
    StringBuilder stringBuilder = new StringBuilder();
    if (!results.isEmpty()) {
      while (it.hasNext()) {
        stringBuilder.append(it.next() + "; ");
      }
    }
    objetid = stringBuilder.toString();
    return objetid;
  }

  @Override
  public Boolean manip_right(int userid, int objetid) {
    Usager usager = findUser(userid);
    em.persist(usager);
    Collection<Objet> objets = usager.getObjets();
    Objet objet = findObjet(objetid);
    return objets.contains(objet);
  }
}
