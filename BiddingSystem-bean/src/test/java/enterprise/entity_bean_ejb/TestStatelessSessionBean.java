// CHECKSTYLE:OFF
package enterprise.entity_bean_ejb;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

//import enterprise.entity_bean_api.StatelessSession;
/*import enterprise.entity_bean_entity.Customer;*/

public class TestStatelessSessionBean {

	private static EJBContainer ec;
	private static Context ctx;

	@BeforeClass
	public static void setUp() throws Exception {
/*	    Map<String, Object> properties = new HashMap<String, Object>();
	    properties.put(EJBContainer.MODULES, new File("target/classes"));
	    ec = EJBContainer.createEJBContainer(properties);
	    ctx = ec.getContext();*/
	}

	@Test
	public void test() throws NamingException {
/*		StatelessSession sb = (StatelessSession) ctx.lookup("enterprise.entity_bean_api.StatelessSession");
		System.out.println("Inserting Customer and Orders... " + sb.testInsert());
		// Test query and navigation
		System.out.println("Verifying that all are inserted... " + sb.verifyInsert());
		// Get a detached instance
		Customer c = sb.findCustomer("Bat Man");
		// Remove entity
		System.out.println("Removing entity... " + sb.testDelete(c));
		// Query the results
		System.out.println("Verifying that all are removed... " + sb.verifyDelete());*/
	}

	@AfterClass
	public static void tearDown() throws Exception {
		if (ec != null) {
			ec.close();
		}
	}

}
