package org.raj.londonboy.bosh.dao;

import org.jivesoftware.smack.BOSHConnection;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.raj.londonboy.bosh.connection.BoshSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PubSubBoshDao implements BoshDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PubSubBoshDao.class);
	
	private BoshSource boshSource;
	
	private String loginUserName;
	
	private String loginPassword;
	
	private String pubsubNode;
	
	public PubSubBoshDao(BoshSource boshSource, String loginUserName, String loginPassword, String pubsubNode){
		this.boshSource = boshSource;
		this.loginUserName = loginUserName;
		this.loginPassword = loginPassword;
		this.pubsubNode = pubsubNode;
	}
    

	@Override
	public void publishMessage(String rootElementName, String xmlMessage){
		
		Connection boshConnection = boshSource.getConnection();
		try {
			boshConnection.connect();
			boshConnection.login(this.loginUserName, this.loginPassword, "pubsub");
		} catch (XMPPException e1) {
			 LOGGER.error("EXCEPTION while login the user: ==== " + e1.getMessage());
			 ((BOSHConnection)boshConnection).disconnect();
		}
		
		// See if you are authenticated

		LOGGER.info("STATUS: ==== " + boshConnection.isAuthenticated());
	    
		PubSubManager manager = new PubSubManager(boshConnection,"pubsub." + boshConnection.getHost());
		
		ConfigureForm form = new ConfigureForm(FormType.submit);
        form.setPersistentItems(true);
        
        LeafNode myNode = null;
        boolean pubsubNodeExists = false;
        try{
        	 myNode = (LeafNode) manager.getNode(pubsubNode);  
             if(myNode!=null)
            	 pubsubNodeExists = true;
          }catch(Exception e){
        	  LOGGER.error("EXCEPTION while getting/deleting the node: ==== " + e.getMessage());
        	  ((BOSHConnection)boshConnection).disconnect();
          }

        try {
        	if(!pubsubNodeExists)
        		myNode = (LeafNode) manager.createNode(pubsubNode, form);
		} catch (XMPPException e) {
			LOGGER.error("EXCEPTION while creating the node: ==== " + e.getMessage());
			((BOSHConnection)boshConnection).disconnect();
		}

        SimplePayload payload = new SimplePayload(rootElementName, null,xmlMessage);
		PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>("ERROR:" + System.currentTimeMillis()/1000, payload);
		try {
			myNode.send(item);
		} catch (XMPPException e) {
			LOGGER.error("EXCEPTION while sending the package: ==== " + e.getMessage());
			((BOSHConnection)boshConnection).disconnect();
		}
		
		try {
			((BOSHConnection)boshConnection).disconnect();
		} catch (Exception e) {
			// Ignore SOCKS error, it has to be fixed in XMPP Server.
		}
	}
}
