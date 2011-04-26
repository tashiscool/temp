/**
 * 
 */
package com.pearson.ed.lp.exception;

import static com.pearson.ed.lp.ws.LicensedProductWebServiceConstants.SOAP_FAULT_LANGUAGE;

import java.util.Properties;

import org.apache.axiom.soap.SOAPFaultText;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.axiom.AxiomSoapMessage;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;
import com.pearson.ed.commons.service.exception.CodedRumbaExceptionElement;

/**
 * Exception factory for GetLicensedProduct V2 service.
 * 
 * @author ULLOYNI
 *
 */
public class LicensedProductExceptionFactory {

	private String defaultKey;
	private Properties codeDescProperties;
	private Properties exceptionMessageProperties;

	/**
	 * @return the defaultKey
	 */
	public final String getDefaultKey() {
		return defaultKey;
	}

	/**
	 * @param defaultKey
	 *            the defaultKey to set
	 */
	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}

	/**
	 * @return the codeDescProperties
	 */
	public Properties getCodeDescProperties() {
		return codeDescProperties;
	}

	/**
	 * @param codeDescProperties
	 *            the codeDescProperties to set
	 */
	public void setCodeDescProperties(Properties codeDescProperties) {
		this.codeDescProperties = codeDescProperties;
	}

	/**
	 * @return the exceptionMessageProperties
	 */
	public Properties getExceptionMessageProperties() {
		return exceptionMessageProperties;
	}

	/**
	 * @param exceptionMessageProperties
	 *            the exceptionMessageProperties to set
	 */
	public void setExceptionMessageProperties(Properties exceptionMessageProperties) {
		this.exceptionMessageProperties = exceptionMessageProperties;
	}

	/**
	 * Returns LicensedProductException for a given message.
	 * 
	 * @param message Message string
	 * @return LicensedProductException
	 */
	public LicensedProductException getLicensedProductException(String message) {
		return getLicensedProductException(message, null);
	}

	/**
	 * Returns LicensedProductException for a given throwable cause.
	 * 
	 * @param cause Root cause
	 * @return LicensedProductException with cause
	 */
	public LicensedProductException getLicensedProductException(Throwable cause) {
		return getLicensedProductException(null, cause);
	}

	/**
	 * Returns LicensedProductException for a given message and a throwable cause.
	 * 
	 * @param message Message string
	 * @param cause Root cause
	 * @return new LicensedProductException instance.
	 */
	public LicensedProductException getLicensedProductException(String message, Throwable cause) {
		String code;
		String desc;

		if (cause == null) {
			code = defaultKey;
		} else if(cause instanceof InvalidOrganizationException) {
			code = LicensedProductExceptionCode.OLC0006.toString();
		} else if(cause instanceof ProductNotFoundException) {
			code = LicensedProductExceptionCode.PLC0006.toString();
		} else if(cause instanceof OrderLineNotFoundException) {
			code = LicensedProductExceptionCode.ORLC006.toString();
		} else if(cause instanceof RequiredObjectNotFoundException) {
			code = LicensedProductExceptionCode.LP0004.toString();
		} else if(cause instanceof ExternalServiceCallException) {
			code = LicensedProductExceptionCode.LP0002.toString();
		} else {
			code = defaultKey;
		}

		desc = codeDescProperties.getProperty(code);
		if (desc == null) {
			code = defaultKey;
			desc = codeDescProperties.getProperty(code);
		}

		LicensedProductException licensedProductException;
		if (cause instanceof AbstractRumbaException) {
			licensedProductException = new LicensedProductException(message, ((AbstractRumbaException) cause).getValues(),
					cause, new CodedRumbaExceptionElement(code, desc));
		} else {
			licensedProductException = new LicensedProductException(message, null, cause,
					new CodedRumbaExceptionElement(code, desc));
		}
		return licensedProductException;
	}

	/**
	 * Returns the exception message for the provided property value.
	 * 
	 * @param messageCode Message code string
	 * @return the exception message corresponding to the message code in the
	 *         property file
	 */
	public String findExceptionMessage(String messageCode) {
		return exceptionMessageProperties.getProperty(messageCode);
	}
	
	/**
	 * Method to get fault message from the SoapFaultClientException's WebServiceMessage.
	 * 
	 * @param message
	 * 
	 * @return fault message
	 */
	public static String getFaultMessage(WebServiceMessage message) {
		AxiomSoapMessage soapMessage = (AxiomSoapMessage)message;
		SOAPFaultText soapFaultText = soapMessage.getAxiomMessage().getSOAPEnvelope().getBody().getFault().getReason()
				.getSOAPFaultText(SOAP_FAULT_LANGUAGE);
		return soapFaultText.getText();
	}

}
