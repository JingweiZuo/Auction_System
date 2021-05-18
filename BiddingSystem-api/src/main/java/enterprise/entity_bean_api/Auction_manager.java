/*
Jingwei ZUO, Yixuan ZHANG
M2 DataScale, MicroProjet de J2EE

In this file, we've defined two asynchronous interfaces: 
    Future<String> getObjetinfo(int objet_id);
    Future<String> getAuctioninfo(int objet_id);
*/

package enterprise.entity_bean_api;

import java.util.concurrent.Future;
import javax.ejb.Asynchronous;
import javax.ejb.Remote;
/**
 * The API of the entity bean.
 */

@Remote
public interface Auction_manager {

  Boolean user_auth(int userid, String pwd);
  
  Boolean addAuction(int objetid, double startingprice, int maxduration, double incrementbidding);
  
  Boolean startAuction(int objetid);
  
  Boolean closeAuction(int objetid);
  
  Boolean bid(int objetid);
  
  String getAuctionlist(String state);
  
  @Asynchronous
  Future<String> getObjetinfo(int objetid);
  
  @Asynchronous
  
  Future<String> getAuctioninfo(int objetid);
}
