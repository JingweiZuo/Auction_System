/*
Jingwei ZUO, Yixuan ZHANG
M2 DataScale, MicroProjet de J2EE
*********************************
In this class, we've created 2 users for the test.
User1: Management of auctions, as adding, starting or closing auctions of some objects
User2: the bidder, who can check for the informtions about objects or auctions, 
     and places bids on some objects.

Methods have been tested:
  - Add/remove auctions
  - Start auctions
  - Place bids on objects
  - Check for auction informations
  - Check for object information details
*/
package enterprise.entity_bean_client;

import enterprise.entity_bean_api.Auction_manager;
import java.io.Serializable;
import java.util.Scanner;
import java.util.concurrent.Future;
import javax.naming.*;    
import javax.naming.InitialContext;

public class Auction_client implements Serializable{
  public static void main(String args[]) {
    //we create 2 users(user1, user2) in this test
    Auction_manager user1;
    Auction_manager user2;
    Scanner sc = new Scanner(System.in);
    try {
      InitialContext ic = new InitialContext();
      user1 = (Auction_manager) ic.lookup("enterprise.entity_bean_api.Auction_manager");
      user2 = (Auction_manager) ic.lookup("enterprise.entity_bean_api.Auction_manager");
      
      System.out.println("\n**********************User1 add auctions************************");
      //User1 add auctions for object1 and object2
      //id and password of user2 are written manually in codes
      System.out.println("Please enter your user id(choose id predefined between 1, 2, 3):");
      int userid = 1;
      System.out.println(userid);
      System.out.println("Please enter your password(password is 123456):");
      String userpwd = "123456";
      System.out.println(userpwd);
      while (!user1.user_auth(userid, userpwd)) {
        System.out.println("Password not correct, please retry!");
        userpwd = sc.next();
      }
      System.out.println("addAuction for object1 is:" + user1.addAuction(1, 50, 5, 1.5));
      System.out.println("addAuction for object2 is:" + user1.addAuction(2, 50, 5, 1.5));  
      System.out.println("\n**********************User1 start auctions************************");
      //User1 start action for object2 of user1
      System.out.println("Start auction for object2 ");
      user1.startAuction(2);

      System.out.println("\n**********************" 
          + "User2 check for auction informations************************");
      //User2 check for auction informations
      //id and password of user2 are written manually in codes
      System.out.println("Please enter your user id(choose id predefined between 1, 2, 3)");
      userid = 2;
      System.out.println(userid);
      System.out.println("Please enter your password(password is 123456) :)");
      userpwd = "123456";
      System.out.println(userpwd);
      while (!user2.user_auth(userid, userpwd)) {
        System.out.println("Password not correct, please retry!");
        userpwd = sc.next();
      }
      System.out.println("Auction not started(objet_id): " + user2.getAuctionlist("not started"));
      System.out.println("Auction active(objet_id): " + user2.getAuctionlist("active"));
      System.out.println("Auction closed(objet_id): " + user2.getAuctionlist("closed"));
      System.out.println("\n**********************" 
          + "User2 check for object informations************************");
      //User2 check for object informations
      System.out.println("User2 check object1 and object2 informations: \n");
      Future<String> future1 = user2.getObjetinfo(1);
      Future<String> future2 = user2.getObjetinfo(2);
      System.out.println("Object 1 information: \n" + future1.get());
      System.out.println("Object 2 information: \n" + future2.get());
      System.out.println("\n**********************User2 start bidding!************************");
      //User2 start bidding
      System.out.println("\nStart bidding for object2 ! \n");
      System.out.println("Bidding 1 of user is: " + user2.bid(2));
      System.out.println("Bidding 2 of user is: " + user2.bid(2));
      System.out.println("Bidding 3 of user is: " + user2.bid(2));
      System.out.println("Bidding 4 of user is: " + user2.bid(2));
      System.out.println("Bidding 5 of user is: " + user2.bid(2));
      System.out.println("Bidding 6 of user is: " + user2.bid(2));
      System.out.println("\n**********************" 
          + "User2 check for auction results************************");
      //User2 check for auction results
      Future<String> future3 = user2.getAuctioninfo(2);
      System.out.println("\nInformation of the auction for object2: \n" + future3.get());
      System.out.println("\n**********************User1 close auction************************");
      //User1 close auction
      System.out.println("\nClose the auction for object2 is: " + user1.closeAuction(2));
      System.out.println("\n**********************" 
          + "User2 check for auction informations************************");
      //User2 check for auction informations
      System.out.println("Auction not started(objet_id): " + user2.getAuctionlist("not started"));
      System.out.println("Auction active(objet_id): " + user2.getAuctionlist("active"));
      System.out.println("Auction closed(objet_id): " + user2.getAuctionlist("closed"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
