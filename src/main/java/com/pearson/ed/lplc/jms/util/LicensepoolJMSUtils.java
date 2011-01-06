package com.pearson.ed.lplc.jms.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

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
	
	
	/**
	 * @return the writer
	 */
	public JMSWriter getWriter() {
		return writer;
	}


	/**
	 * @param writer the writer to set
	 */
	public void setWriter(JMSWriter writer) {
		this.writer = writer;
	}
	/**
	 * This method publish message to the "LicensePool" queue.
	 * @param licensepool LicensepoolDTO that has id.
	 * @param type event type (Expiration, Cancellation etc.)
	 * @throws JMSException exception	
	 * @throws Exception exception
	 */

	public void publish(LicensePoolDTO licensepool, EventTypeType type) throws JMSException, Exception{
		
		try {
	        JAXBContext jaxbcontext = JAXBContext.newInstance(LicensePoolEvent.class);
			Marshaller marshaller = jaxbcontext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        LicensePoolEvent event  =new LicensePoolEvent();
	        event.setEventType(type);
	        event.setLicensePoolId(licensepool.getLicensepoolId());
	        JAXBElement<LicensePoolEvent> createLicensepoolMessage = new ObjectFactory().createLicensepoolMessage(event);
 	        marshaller.marshal(createLicensepoolMessage, new FileOutputStream("jaxbOutput.xml"));
 	        BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream("jaxbOutput.xml"))));
 	        StringBuffer buf = new StringBuffer();
 	        String strLine;
 	        while ((strLine = br.readLine()) != null) 
 	        	buf.append(strLine+"\n");
 	        writer.writeToQueue(buf.toString());
		} catch (JAXBException e) {
			logger.log(Level.ERROR,
					"Exception while sending licensepool message with ID :"
							+ licensepool.getLicensepoolId() +"  "+e.getStackTrace());	
		} catch (FileNotFoundException e) {
			logger.log(Level.ERROR,
					"Exception while sending licensepool  message with ID :"
							+ licensepool.getLicensepoolId() +"  "+e.getStackTrace());	
		}
		
	}

}
