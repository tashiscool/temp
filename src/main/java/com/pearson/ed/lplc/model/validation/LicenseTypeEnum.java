package com.pearson.ed.lplc.model.validation;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerated class defines the list of supported Licensing types.
 * 
 */
public class LicenseTypeEnum {

	private static final Map<String, LicenseTypeEnum> LICENSE_TYPE = new HashMap<String, LicenseTypeEnum>();

	/**
	 * Represents constant for student seat based licensing.
	 */
	public static final LicenseTypeEnum studentSeatBasedLicensing = new LicenseTypeEnum("Student seat based licensing");

	private final String type;

	private LicenseTypeEnum(String type) {
		super();
		this.type = type;
		LICENSE_TYPE.put(type, this);
	}

	public static LicenseTypeEnum valueOf(final String type) {
		return (LicenseTypeEnum) LICENSE_TYPE.get(type);
	}

	public String toString() {
		return this.type;
	}

}
