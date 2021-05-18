/*
Jingwei ZUO, Yixuan ZHANG
M2 DataScale, MicroProjet de J2EE
*********************************
In this class, we've set an adminstrator for the test, the password is "admin" by default.
3 users have been added manually into our program.
For each user, we add 2 objects into their lists.

Methods have been tested:
  - Add/remove user
  - Add/remove objects for users
  - Check object list
  - Check user rights
  - Update user rights
*/

package enterprise.entity_bean_client;

import enterprise.entity_bean_api.Dir_manager;
import java.io.Serializable;
import java.util.Scanner;
import javax.naming.InitialContext;


public class Admin_client implements Serializable {
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    Dir_manager dm;
    try {
      InitialContext ic = new InitialContext();
      dm = (Dir_manager) ic.lookup("enterprise.entity_bean_api.Dir_manager");
      System.out.println("Please enter your admin password(password is admin): ");
      String adminpwd = sc.next();
      //Vérifier le mot de passe d'administrateur
      while (! dm.admin_auth(adminpwd)) {
        System.out.println("Password not correct, please retry!");
        adminpwd = sc.next();
      }
      System.out.println("Password correct, you've got the admin right!");
      System.out.println("\n**********************Add users to user list************************");
      //add user "Jingwei ZUO" and user "Yixuan ZHANG
      String prenom1 = "Jingwei ";
      String nom1 = "ZUO";
      String user1pwd = "123456";
      String user1addr = "2 BD LESSEPS";
      String user1mail = "hustsimon@gmail.com";
      int user1right = 5;
      System.out.println(prenom1 + nom1 + " has been add to the user list, user_id is:" 
          + dm.addUser(prenom1, nom1, user1pwd, user1addr, user1mail, user1right));
      String prenom2 = "Yixuan ";
      String nom2 = "ZHANG";
      String user2pwd = "123456";
      String user2addr = "3 BD LESSEPS";
      String user2mail = "yixuan.zhang@gmail.com";
      int user2right = 5;
      System.out.println(prenom2 + nom2 + " has been add to the user list, user_id is:" 
          + dm.addUser(prenom2, nom2, user2pwd, user2addr, user2mail, user2right));
      String prenom3 = "Qiuhao ";
      String nom3 = "QIAN";
      String user3pwd = "123456";
      String user3addr = "4 BD LESSEPS";
      String user3mail = "qiuhao.qian@gmail.com";
      int user3right = 5;
      System.out.println(prenom3 + nom3 + " has been add to the user list, user_id is:" 
          + dm.addUser(prenom3, nom3, user3pwd, user3addr, user3mail, user3right));
      //add objects for user1 and user2
      System.out.println("\n**********************" 
          + "Add objects to object list************************");
      System.out.println("New objet has been add, objet_id is: " 
          + dm.addObjet(1, "Stylo", "matériel bureautique"));
      System.out.println("New objet has been add, objet_id is: " 
          + dm.addObjet(1, "Notebook", "matériel bureautique"));
      System.out.println("New objet has been add, objet_id is: " 
          + dm.addObjet(2, "Parapluie", "produit quodidien"));
      System.out.println("New objet has been add, objet_id is: " 
          + dm.addObjet(2, "PC", "produit électronique"));
      System.out.println("New objet has been add, objet_id is: " 
          + dm.addObjet(3, "Voiture", "véhicule"));
      System.out.println("New objet has been add, objet_id is: " 
          + dm.addObjet(3, "Maison", "immobilier"));
      //Check for user rights
      System.out.println("\n**********************Check for user rights************************");
      System.out.println("Rights of user1 is: " + dm.lookupRights(1));
      System.out.println("Rights of user2 is: " + dm.lookupRights(2));
      System.out.println("Rights of user3 is: " + dm.lookupRights(3));
      System.out.println("\n**********************Update user's rights************************");
      //add 4 more rights to user2.
      System.out.println("Add 4 more objects rights to user2\n");
      dm.updateRights(2, 4);
      System.out.println("Rights of user1 is: " + dm.lookupRights(1));
      System.out.println("Rights of user2 is: " + dm.lookupRights(2));
      System.out.println("Rights of user3 is: " + dm.lookupRights(3));
      System.out.println("\n**********************Remove object************************");
      //remove object4 of user2
      System.out.println("Object list is(objet_id): " + dm.getObjetlist());
      System.out.println("Remove object4 of user2\n");
      dm.removeObjet(4);
      System.out.println("Object list is(objet_id): " + dm.getObjetlist());
      System.out.println("\n**********************Check for user rights************************");
      //Check for user rights
      System.out.println("Rights of user1 is: " + dm.lookupRights(1));
      System.out.println("Rights of user2 is: " + dm.lookupRights(2));
      System.out.println("Rights of user3 is: " + dm.lookupRights(3));
      System.out.println("\n**********************Remove user************************");
      //remove user3
      System.out.println("Object list is(objet_id): " + dm.getObjetlist());
      System.out.println("Remove user3 \n");
      dm.removeUser(3);
      System.out.println("Object list is(objet_id): " + dm.getObjetlist());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
