package com.pearson.ed.lplc.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pearson.ed.lplc.common.LPLCConstants;
import com.pearson.ed.lplc.dto.LicensePoolDTO;

public class LicensePoolValidator implements Validator {

	private final Class<?>[] validatedTypes = new Class<?>[] { LicensePoolDTO.class };
	private List<String> validationErrors = new ArrayList<String>();
	private LicensePoolDTO licensepool;

	/**
	 * Returns an array of valid types that LicensePoolValidator can validate. LicensePoolValidator is current designed
	 * to validate LicensePool and any of its subclasses.
	 * 
	 * @return an array of classes the validator supports.
	 */
	public Class<?>[] getValidatedTypes() {
		return validatedTypes;
	}

	/**
	 * Constructs a list of error that occurred during validation.
	 * 
	 * @return a list of errors.
	 */
	public List<String> getValidationErrors() {
		return validationErrors;
	}

	/**
	 * Performs validation on the domain object. The method checks to see that the object is an instance of a class
	 * supported by the validator (i.e. User, and then checks that the ID and password are not empty.
	 * 
	 * @return true if the object passes validation.
	 */
	public boolean validate(Object domainObject) {
		boolean result = true;
		if (!(domainObject instanceof LicensePoolDTO)) {
			throw new IllegalArgumentException("Invalid parameters.");
		}
		licensepool = (LicensePoolDTO) domainObject;

		String mode = licensepool.getMode();

		if (LPLCConstants.CREATE_MODE.equals(mode)) {
			if (licensepool.getStartDate() == null) {
				result = false;
				validationErrors.add("LicensePool Start Date must not be empty");
			}
		}

		return result;
	}

}
