package com.pearson.ed.lplc.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class LicensePoolException extends LPLCBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8970181489659198621L;

	private LicensePoolExceptionElement codeAndDesc;

	public LicensePoolExceptionElement getCodeAndDesc() {
		return codeAndDesc;
	}

	public void setCodeAndDesc(LicensePoolExceptionElement codeAndDesc) {
		this.codeAndDesc = codeAndDesc;
	}

	public LicensePoolException(String message,
			LicensePoolExceptionElement codeAndDesc) {
		this(message, null, codeAndDesc);
	}

	public LicensePoolException(Throwable cause,
			LicensePoolExceptionElement codeAndDesc) {
		this(cause != null ? cause.getMessage() : "", cause, codeAndDesc);
	}

	public LicensePoolException(String message, Throwable cause,
			LicensePoolExceptionElement codeAndDesc) {
		super(message, cause);
		this.codeAndDesc = codeAndDesc;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LicensePool Exception - ");
		if (codeAndDesc != null) {
			sb.append("code:" + codeAndDesc.getCode());
			sb.append(", description:" + codeAndDesc.getDescription());
		}
		if (getMessage() == null && getCause() != null) {
			sb.append(", message:" + getCause().toString());
		} else {
			sb.append(", message:" + getMessage());
		}

		return sb.toString();
	}

}
