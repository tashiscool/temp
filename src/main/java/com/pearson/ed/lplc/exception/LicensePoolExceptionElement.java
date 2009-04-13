package com.pearson.ed.lplc.exception;

import java.io.Serializable;

public class LicensePoolExceptionElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7596280449742285456L
	;
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
	
	public LicensePoolExceptionElement(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
}
