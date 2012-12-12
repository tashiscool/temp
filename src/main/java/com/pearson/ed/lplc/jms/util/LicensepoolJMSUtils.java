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
import com.pearson.rws.licensepool.doc._2009._04._01.EventTypeType;
import com.pearson.rws.licensepool.doc._2009._04._01.LicensePoolEvent;
import com.pearson.rws.licensepool.doc._2009._04._01.ObjectFactory;

public class LicensepoolJMSUtils {

	private static final Logger logger = Logger.getLogger(LicensepoolJMSUtils.class);
	private JMSWriter writer = null;
	private Marshaller marshaller;

	public LicensepoolJMSUtils() {
		try {
			JAXBContext jaxbcontext = JAXBContext.newInstance(LicensePoolEvent.class);
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

	public void publish(LicensePoolDTO licensepool, EventTypeType type) throws JMSException, Exception {

		try {
			LicensePoolEvent event = new LicensePoolEvent();
			event.setEventType(type);
			event.setLicensePoolId(licensepool.getLicensepoolId());
			JAXBElement<LicensePoolEvent> createLicensepoolMessage = new ObjectFactory()
					.createLicensepoolMessage(event);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			marshaller.marshal(createLicensepoolMessage,  baos);
			writer.writeToQueue(baos.toString());
		} catch (JAXBException e) {
			logger.log(
					Level.ERROR,
					"Exception while sending licensepool message with ID :" + licensepool.getLicensepoolId() + "  "
							+ e.getStackTrace());
		}

	}

}
