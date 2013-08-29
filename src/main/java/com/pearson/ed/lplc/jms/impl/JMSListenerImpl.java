package com.pearson.ed.lplc.jms.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.jms.api.JMSListener;
import com.pearson.ed.lplc.services.api.LicensingCompositeService;

/**
 * Implementation class for JMSListener interface to listen message from organization.
 * 
 * @author vsethsa
 * 
 */
public class JMSListenerImpl implements JMSListener {

	private static final Logger logger = Logger.getLogger(JMSListenerImpl.class);

	@Autowired
	private LicensingCompositeService licensepoolService;

	/**
	 * @return the licensepoolService
	 */
	public LicensingCompositeService getLicensepoolService() {
		return licensepoolService;
	}

	/**
	 * @param licensepoolService
	 *            the licensepoolService to set
	 */
	public void setLicensepoolService(LicensingCompositeService licensepoolService) {
		this.licensepoolService = licensepoolService;
	}

	/**
	 * This method receives message from organization topic and invokes licensepool service.
	 * 
	 * @param message
	 *            contains message received from destination.
	 */
	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			assignLicensePoolsToNewOrganization(textMessage);
		} else {
			throw new IllegalArgumentException("Message must be of type TextMessage");
		}
	}

	/**
	 * Invoking method for LicensePool service.
	 * 
	 * @param textMessage
	 *            message received from destination.
	 * @throws LicensePoolJMSException
	 */
	private void assignLicensePoolsToNewOrganization(TextMessage textMessage) {
		try {
			String eventType = null;
			String message = textMessage.getText();
			if (logger.isDebugEnabled()) {
				logger.debug("Received Message from organization topic: " + message);
			}
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new BufferedInputStream(new ByteArrayInputStream(message
					.getBytes())));
			document.getDocumentElement().normalize();

			NodeList eventTypeNode = document.getElementsByTagName(LPLCConstants.ORG_EVENT_TYPE);
			eventType = eventTypeNode.item(0).getChildNodes().item(0).getNodeValue();
//			if (eventType.equalsIgnoreCase(LPLCConstants.ORG_REL_CREATE)) {
//				NodeList organizationNode = document.getElementsByTagName(LPLCConstants.ORG_ID);
//				organizationId = organizationNode.item(0).getChildNodes().item(0).getNodeValue();
//				NodeList parentOrganizationNode = document.getElementsByTagName(LPLCConstants.PARENT_ORG_ID);
//				parentOrganizationId = parentOrganizationNode.item(0).getChildNodes().item(0).getNodeValue();
			NodeList licensepoolIdNode = document.getElementsByTagName("LicensePoolId");
			String licensepoolId = licensepoolIdNode.item(0).getChildNodes().item(0).getNodeValue();
			 licensepoolService.subscribeUser(licensepoolId, eventType);
//			}
		} catch (Exception exception) {
			if (exception instanceof JMSException) {
				throw new LicensePoolJMSException("Failed to Fetch JMS message", exception);
			} else if (exception instanceof ParserConfigurationException || exception instanceof SAXException
					|| exception instanceof IOException) {
				throw new LicensePoolJMSException("Failed to parse message", exception);
			} else if (exception instanceof NullPointerException) {
				throw new LicensePoolJMSException("Message is not in specific format", exception);
			} else {
				throw new RuntimeException(exception);
			}
		}
	}
	
	class LicensePoolJMSException extends RuntimeException
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public LicensePoolJMSException(String string, Exception exception) {
			super(string, exception);
		}
		
	}

}
