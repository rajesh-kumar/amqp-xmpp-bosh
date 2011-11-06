Spring Integration - AMQP/BOSH/XMPP Example
================================

# Overview

This sample demonstrates basic functionality of the **Spring Integration AMQP Adapter**, **Spring Integration XMPP** and SMACK BOSH API, which uses the [Advanced Message Queuing Protocol](http://www.amqp.org/) (AMQP) to publish messages on EJABBERD (any XMPP) Server using XMPP or BOSH. 

Once the application is started, you enter some text on the command prompt and a message containing that entered text is dispatched to the AMQP queue. In return that message will be published to XMPP server using XMPP connection or BOSH connection. You can switch the choice by importing BOSH/XMPP config file in "src\main\resources\META-INF\spring\integration\spring-amqp-context.xml"

like shown below:
	<import resource="springxmpp-context.xml"/>
	<import resource="springbosh-context.xml"/>

> In order to run the example you will need a running  instance of RabbitMQ and Ejabberd. A local installation with just the basic defaults will be sufficient. Please visit: [http://www.rabbitmq.com/install.html] and(http://ndpar.blogspot.com/2010/02/installing-ejabberd-on-ubuntu.html) for detailed installation procedures. For ejabberd, you can use default localhost domain for test purposes.

# How to Run the Sample

If you imported the example into your IDE, you can just run class **org.raj.londonboy.amqp.xmpp.bosh.Main**. For example in [SpringSource Tool Suite](http://www.springsource.com/developer/sts) (STS) do:

* Right-click on Main class --> Run As --> Java Application

Alternatively, you can start the sample from the command line ([Maven](http://maven.apache.org/) required):

* mvn package
* mvn exec:java

# Used Spring Integration components

### Spring Integration Modules (Maven dependencies)

* spring-integration-core
* spring-integration-amqp
* spring-integration-stream
* spring-integration-xmpp

### Spring Integration Adapters

* int-stream:stdin-channel-adapter
* **int-amqp:outbound-channel-adapter**
* **int-amqp:inbound-channel-adapter**
* int-stream:stdout-channel-adapter
* int:poller
* int:channel
* int:interceptors
* int:wire-tap
* logging-channel-adapter
* int-xmpp:xmpp-connection
* int-xmpp:header-enricher 
* int-xmpp:outbound-channel-adapter

# XMPP Demo (Use <import resource="springxmpp-context.xml"/> inside spring-amqp-context.xml)

This demo has been tested with ejabberd (on Ubuntu) and
demonstrates the following aspects of the XMPP support available with Spring Integration:
1. XMPP Inbound Channel Adapter - receive instant messages.
2. XMPP Outboud Channel Adapter - send instant messages.

In order to run this sample you need to provide correct values in xmpp.properties.
Everything there was already preset. The only 3 properties you need to provide values for are:

	- user.login
	- user.password
	- send.to.user
	
You'll also need to test it with your friend or have two ejabberd accounts setup

	To test this demo, first log on to the account identified via 'send.to.user' property
	and make sure that that account is in your buddy list. Then run the demo.
	
	When demo is started you'll see on Ejabberd that your buddy 
	has just signed on. Now you can send a message from JabberID (on ubuntu) and see it appear on the console.

# BOSH Demo (Use <import resource="springbosh-context.xml"/> inside spring-amqp-context.xml)

For testing BOSH, you must download BBCRD open-source demo of strophe.js using BOSH connection:

https://github.com/bbcrd/Strophejs-PubSub-Demo

and then run Main.java which has been tested with ejabberd (on Ubuntu) and demonstrates the following aspects of the BOSH support available with SMACK APIs:

In order to run this sample you need to provide correct values in xmpp.properties.
Everything there was already preset. The only 3 properties you need to provide values for are:

	- bosh.login
	- bosh.password
	- bosh.pubsub.node (It must be same as mentioned in BBCRD shared.js file)
	
When demo is started you'll see messaged appearing on the browser (client.html) just like twitter updates.

Note: BBCRD demo example expects messages in certain format to read, you can change it if you want. But to run this demo, send only xml messages with below format:

<entry>&lt;data type='msg_text'&gt;test me&lt;/data&gt;</entry>

