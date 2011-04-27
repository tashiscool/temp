package com.pearson.ed.lp.exception;

import javax.xml.bind.JAXBElement;
import javax.xml.transform.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.rws.common.doc.v2.ParameterListType;
import com.pearson.rws.licensedproduct.doc.v2.LicensedProductExceptionType;
import com.pearson.rws.licensedproduct.doc.v2.ObjectFactory;

/**
 * Custom exception resolver for LicensedProduct version 2 services to generate Soap fault detail element in Soap fault
 * as it is not supported by Spring out of the box. Constructs the JAXB Exception element from exception and marshals
 * into Soap fault raw xml
 * 
 * @author nlloyd
 */
public class LicensedProductV2SoapFaultExceptionResolver extends SoapFaultAnnotationExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicensedProductV2SoapFaultExceptionResolver.class);

	@Autowired(required = true)
	private Jaxb2Marshaller marshaller;

	@Autowired(required = true)
	private LicensedProductExceptionFactory exceptionFactory;
	
	public void setMarshaller(Jaxb2Marshaller marshaller) {
		this.marshaller = marshaller;
	}
	
	public void setExceptionFactory(LicensedProductExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.ws.soap.server.endpoint.AbstractSoapFaultDefinitionExceptionResolver#customizeFault(java.
	 * lang.Object, java.lang.Exception, org.springframework.ws.soap.SoapFault)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void customizeFault(final Object endpoint, final Exception ex, final SoapFault fault) {

		super.customizeFault(endpoint, ex, fault);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(ex.toString());
		}
		
		Result result = fault.addFaultDetail().getResult();

		JAXBElement element = null;
		if (ex instanceof AbstractRumbaException) {
			
			LicensedProductException licensedProdException = exceptionFactory.getLicensedProductException(ex.getMessage(), ex);

			LicensedProductExceptionType licensedProdJAXBException = new LicensedProductExceptionType();

			licensedProdJAXBException.setCode(licensedProdException.getCodeAndDesc().getCode());
			licensedProdJAXBException.setDescription(licensedProdException.getCodeAndDesc().getDescription());
			licensedProdJAXBException.setMessage(licensedProdException.getCause().getMessage());

			ParameterListType params = new ParameterListType();

			Object[] objectValues = licensedProdException.getValues();
			if ((objectValues != null) && (objectValues.length > 0)) {
				for (Object objectValue : objectValues) {
					if (objectValue != null) {
						params.getParameter().add(objectValue.toString());
					}
				}
			}

			licensedProdJAXBException.setParameters(params);

			element = new ObjectFactory().createLicensedProductExceptionElement(licensedProdJAXBException);
		}

		// marshal custom exception into SoapFaultDetail
		try {
			this.marshaller.marshal(element, result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
