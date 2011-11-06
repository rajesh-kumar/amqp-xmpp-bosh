package org.raj.londonboy.amqp.xmpp.bosh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private Main() { }

    public static void main(final String... args) {


        final AbstractApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:META-INF/spring/integration/spring-amqp-context.xml");

        context.registerShutdownHook();

        LOGGER.info("\n========================================================="
        		  + "\n                                                          "
        		  + "\n    This is the AMQP/XMPP/BOSH Sample -                   "
        		  + "\n                                                          "        		  
                  + "\n    Please enter some text and press return. The entered  "
                  + "\n    Message will be sent to the configured RabbitMQ Queue,"
                  + "\n    then sent to BOSH/XMPP server to notify connected     "
        		  + "\n    users.											     "                  
        		  + "\n                                                          "                  
                  + "\n=========================================================" );

    }
}
