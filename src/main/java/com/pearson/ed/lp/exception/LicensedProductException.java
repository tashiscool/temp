/**
 * 
 */
package com.pearson.ed.lp.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

import com.pearson.ed.commons.service.exception.AbstractCodedRumbaException;
import com.pearson.ed.commons.service.exception.CodedRumbaExceptionElement;

/**
 * Soap Fault exception class for the GetLicensedProduct V2 service.
 * Encapsulates description code and message for callers of this service.
 * 
 * @author ULLOYNI
 *
 */
@SoapFault(faultCode = FaultCode.CLIENT)
public class LicensedProductException extends AbstractCodedRumbaException {

	private static final long serialVersionUID = -4365475616100243442L;

	public LicensedProductException(CodedRumbaExceptionElement codeAndDesc) {
		super(codeAndDesc);
	}
	
	public LicensedProductException(String message, Object[] values, CodedRumbaExceptionElement codeAndDesc) {
		super(message, values, codeAndDesc);
	}
	
	public LicensedProductException(Throwable cause, CodedRumbaExceptionElement codeAndDesc) {
		super(cause, codeAndDesc);
	}
	
	public LicensedProductException(String message, Object[] values,  Throwable cause, CodedRumbaExceptionElement codeAndDesc) {
		super(message, values, cause, codeAndDesc);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("GetLicensedProduct Exception - ");
		if (getCodeAndDesc() != null) {
			sb.append("code:").append(getCodeAndDesc().getCode());
			sb.append(", description:").append(getCodeAndDesc().getDescription());
		}
		if (getMessage() == null && getCause() != null) {
			sb.append(", message:").append(getCause().toString());
		}
		else {
			sb.append(", message:").append(getMessage());
		}
		
		return sb.toString();
	}

}
