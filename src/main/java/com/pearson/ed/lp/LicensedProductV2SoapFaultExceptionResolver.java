package com.pearson.ed.lp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;

/**
 * Custom exception resolver for LicensedProduct version 2 services to generate Soap fault detail element in Soap fault
 * as it is not supported by Spring out of the box. Constructs the JAXB Exception element from exception and marshals
 * into Soap fault raw xml
 * 
 * @author nlloyd
 */
public class LicensedProductV2SoapFaultExceptionResolver extends SoapFaultAnnotationExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicensedProductV2SoapFaultExceptionResolver.class);

	private Jaxb2Marshaller marshaller;

	public void setMarshaller(Jaxb2Marshaller marshaller) {
		this.marshaller = marshaller;
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

		// Result result = fault.addFaultDetail().getResult();
		//
		// JAXBElement element = null;
		// if (ex instanceof ProductV2Exception) {
		// ProductV2Exception prodException = (ProductV2Exception) ex;
		//
		// ProductExceptionType prdJAXBException = new ProductExceptionType();
		//
		// prdJAXBException.setCode(prodException.getCodeAndDesc().getCode());
		// prdJAXBException.setDescription(prodException.getCodeAndDesc().getDescription());
		// prdJAXBException.setMessage(prodException.getCause().getMessage());
		//
		// ParameterListType params = new ParameterListType();
		//
		// Object[] objectValues = prodException.getValues();
		// if ((objectValues != null) && (objectValues.length > 0)) {
		// for (Object objectValue : objectValues) {
		// if (objectValue != null) {
		// params.getParameter().add(objectValue.toString());
		// }
		// }
		// }
		//
		// prdJAXBException.setParameters(params);
		//
		// element = new ObjectFactory().createProductExceptionElement(prdJAXBException);
		// }
		//
		//
		// // marshal custom exception into SoapFaultDetail
		// try {
		// this.marshaller.marshal(element, result);
		// } catch (Exception e) {
		// throw new RuntimeException(e);
		// }
	}
}
