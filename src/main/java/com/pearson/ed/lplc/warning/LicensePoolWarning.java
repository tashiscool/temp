package com.pearson.ed.lplc.warning;

import java.io.Serializable;

public class LicensePoolWarning implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8047103790629319586L;

	private LicensePoolWarningElement codeAndDesc;
	private String message;
	
	public LicensePoolWarningElement getCodeAndDesc() {
		return codeAndDesc;
	}
	
	public void setCodeAndDesc(LicensePoolWarningElement codeAndDesc) {
		this.codeAndDesc = codeAndDesc;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LicensePoolWarning(String message) {
		this(message, null);
	}
	
	public LicensePoolWarning(LicensePoolWarningElement codeAndDesc) {
		this(null, codeAndDesc);
	}
	
	public LicensePoolWarning(String message, LicensePoolWarningElement codeAndDesc) {
		this.message = message;
		this.codeAndDesc = codeAndDesc;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LicensePool Warning - ");
		if (codeAndDesc != null) {
			sb.append("code:" + codeAndDesc.getCode());
			sb.append(", description:" + codeAndDesc.getDescription());
		}
		if (message != null) {
			sb.append(", message: " + message);
		}
		return sb.toString();
	}
	
}
