package com.pearson.ed.lplc.jms.api;

import javax.jms.JMSException;

import org.springframework.jms.core.JmsTemplate;

public interface JMSWriter {
	public JmsTemplate getJmsTemplate();

	public void setJmsTemplate(JmsTemplate template);

	public void writeToQueue(String msg) throws JMSException, Exception;
}
