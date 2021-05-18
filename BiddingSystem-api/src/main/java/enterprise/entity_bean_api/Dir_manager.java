/*
Jingwei ZUO, Yixuan ZHANG
M2 DataScale, MicroProjet de J2EE

In this file, we've defined 2 types of interface
	------------Interfaces which need admin right to access.-------------
	------------Interfaces which don't need admin right and can be accessed in other class.-------------
*/

package enterprise.entity_bean_api;

/* importer les entités Usager et Objet */
import enterprise.entity_bean_entity.Usager;
import enterprise.entity_bean_entity.Objet;
import javax.ejb.Remote;


/**
 * The API of the entity bean.
 */

@Remote
public interface Dir_manager {		

  boolean admin_auth(String pwd);
  /**********Avoir besoin d'authentifier le rôle d'admin.***********/
  
  int addUser(String prenom, String nom, String password, String addr, String email, int droits);
  
  void removeUser(int userid);
  
  int addObjet(int userid, String desc, String cat);
  
  void removeObjet(int objetid);
  
  void updateRights(int userid, int rightsvar);
  
  /**********Pas besoin d'authentifier le rôle d'admin.***********/
  int lookupRights(int userid);
  
  Usager findUser(int userid);
  
  Objet findObjet(int objetid);
  
  String getUserpwd(int userid);
  
  String getObjetinfo(int objetid);

  String getObjetlist();
  
  Boolean manip_right(int userid, int objetid);
}
