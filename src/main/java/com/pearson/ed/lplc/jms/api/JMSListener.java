package com.pearson.ed.lplc.jms.api;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Message Listener interface to listen messages from destination.
 * 
 * @author vsethsa
 * 
 */
public interface JMSListener extends MessageListener {

	/**
	 * Overridden onMessage method to listen passcode topic.
	 * 
	 * @param message
	 *            message received from destination.
	 */
	public void onMessage(Message message);

}
