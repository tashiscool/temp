package com.pearson.ed.lplc.warning;

import java.io.Serializable;

public class LicensePoolWarningElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7507097996465050956L;

	private String code;
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LicensePoolWarningElement(String code, String description) {
		this.code = code;
		this.description = description;
	}
}
