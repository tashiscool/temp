package com.pearson.ed.lplc.jms.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.pearson.ed.lplc.jms.api.JMSWriter;

public class JMSWriterImpl implements JMSWriter {
	private JmsTemplate jmsTemplate = null;

	@Override
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	@Override
	public void setJmsTemplate(JmsTemplate template) {
		this.jmsTemplate = template;
	}

	@Override
	public void writeToQueue(final String msg) throws JMSException, Exception {

		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}
}
