package org.raj.londonboy.amqp.xmpp.bosh;

import org.jivesoftware.smack.BOSHConfiguration;
import org.jivesoftware.smack.BOSHConnection;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.SimplePayload;

public class PublishMessage {

    /**
     * Method to test Bosh connection with ejabberd
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception {
       
        BOSHConfiguration boshConfiguration = new BOSHConfiguration(false, "localhost", 5280, "/http-bind", "localhost");
        SASLAuthentication.supportSASLMechanism("EXTERNAL", 0);
        Connection boshConnection = new BOSHConnection(boshConfiguration);
        boshConnection.connect();
       
        boshConnection.login("rajesh", "test1234", "pubsub");
       
        // See if you are authenticated
        System.out.println(boshConnection.isAuthenticated());
        
        PubSubManager manager = new PubSubManager(boshConnection,"pubsub." + boshConnection.getHost());
       
        ConfigureForm form = new ConfigureForm(FormType.submit);
        //form.setPersistentItems(false);
        //form.setDeliverPayloads(false);
        //form.setAccessModel(AccessModel.open);
       
       
        LeafNode myNode = null;
        try{
             myNode = (LeafNode) manager.getNode("pubsub.demo.v1"); 
            //exists, so delete
            manager.deleteNode("pubsub.demo.v1");
          }catch(Exception e){
            //'getNode' threw an exception.
            //so we know that the node did not exist
          }

        myNode = (LeafNode) manager.createNode("pubsub.demo.v1", form);
        System.out.println("node create successfully");
        for(int i=0; i<=5; i++)
        {
            SimplePayload payload = new SimplePayload("entry", null,
                    "<entry>&lt;data type='msg_text'&gt;test me" + i +"&lt;/data&gt;</entry>");
            PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>("ERROR:" + System.currentTimeMillis()/1000, payload);
            myNode.send(item);
            Thread.sleep(5000);
        }
        System.out.println("Item sent successfully");
     
        /*Node eventNode = manager.getNode("pubsub.demo.v1");
        eventNode.addItemEventListener(new MyEventHandler());
        eventNode.subscribe("rajesh@localhost");*/
        ((BOSHConnection)boshConnection).disconnect();
    }
}