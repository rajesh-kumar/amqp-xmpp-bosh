package org.raj.londonboy.message.handler;

import org.springframework.integration.Message;
import org.springframework.integration.message.GenericMessage;


public class MessageHandler {
	
	 public Message<String> handleMessage(String data) {
		 Message<String> message = new GenericMessage<String>(data);
		 return message;
	 }

}
