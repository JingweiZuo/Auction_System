#!/bin/bash
asadmin stop-database
asadmin stop-domain domain1
mvn clean install
asadmin start-domain domain1 
asadmin start-database
asadmin undeploy entity-bean
asadmin deploy BiddingSystem-bean/target/entity-bean.jar
cd biddingsystem-client
java -classpath $CLASSPATH:../biddingsystem-bean/target/entity-bean.jar:target/biddingsystem-client-4.0-SNAPSHOT.jar enterprise/entity_bean_client/Admin_client 
java -classpath $CLASSPATH:../biddingsystem-bean/target/entity-bean.jar:target/biddingsystem-client-4.0-SNAPSHOT.jar enterprise/entity_bean_client/Auction_client 
asadmin undeploy entity-bean
asadmin stop-database
asadmin stop-domain domain1
