package com.pearson.ed.lp;

import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.xml.transform.StringSource;

public class TestHelperUtils {

	/**
	 * Convenience function to wrap use of the marshaller instance to create from JAXB objects 
	 * an output {@link Source} object.
	 * 
	 * @param marshaller a {@link Jaxb2Marshaller} instance
	 * @param jaxbObject
	 * @return
	 */
	public static Source marshal(Jaxb2Marshaller marshaller, Object jaxbObject) {
		final StringWriter out = new StringWriter();
		marshaller.marshal(jaxbObject, new StreamResult(out));
		
		return new StringSource(out.toString());
	}
}
