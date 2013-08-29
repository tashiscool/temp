package com.pearson.ed.lplc.jms.util;

import java.io.ByteArrayOutputStream;

import javax.jms.JMSException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.jms.api.JMSWriter;
import com.pearson.rws.codes.doc._2009._09._01.EventTypeType;
import com.pearson.rws.codes.doc._2009._09._01.ObjectFactory;
import com.pearson.rws.codes.doc._2009._09._01.PassCodeEvent;
import com.pearson.rws.codes.doc._2009._09._01.ReasonCodeType;

public class PassCodeJMSUtils {

	private static final Logger logger = Logger.getLogger(PassCodeJMSUtils.class);
	private JMSWriter writer = null;
	private Marshaller marshaller;

	public PassCodeJMSUtils() {
		try {
			JAXBContext jaxbcontext = JAXBContext.newInstance(PassCodeEvent.class);
			marshaller = jaxbcontext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the writer
	 */
	public JMSWriter getWriter() {
		return writer;
	}

	/**
	 * @param writer
	 *            the writer to set
	 */
	public void setWriter(JMSWriter writer) {
		this.writer = writer;
	}

	/**
	 * This method publish message to the "LicensePool" queue.
	 * 
	 * @param licensepool
	 *            LicensepoolDTO that has id.
	 * @param type
	 *            event type (Expiration, Cancellation etc.)
	 * @throws JMSException
	 *             exception
	 * @throws Exception
	 *             exception
	 */

	public void publish(String orgId, EventTypeType type) throws JMSException, Exception {

		try {
			PassCodeEvent event = new PassCodeEvent();
			event.setEventType(type);
			event.setOrganizationId(orgId);
			event.setReasonCode(ReasonCodeType.LP_CREATE);
			JAXBElement<PassCodeEvent> createLicensepoolMessage = new ObjectFactory()
					.createPassCodeMessage(event);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			marshaller.marshal(createLicensepoolMessage,  baos);
			writer.writeToQueue(baos.toString());
		} catch (JAXBException e) {
			logger.log(
					Level.ERROR,
					"Exception while sending licensepool message with org ID :" + orgId + "  "
							+ e.getStackTrace());
		}

	}

}
