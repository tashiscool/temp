package com.pearson.ed.lplc.services.converter.api;

import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.ws.schema.CreateLicensePool;
import com.pearson.ed.lplc.ws.schema.CreateLicensePoolRequest;
import com.pearson.ed.lplc.ws.schema.LicensePool;

/**
 * A converter class which handles the interchange between LicensePools and
 * LicensePoolMappings.
 * 
 * @author RUMBA
 */

public interface LicensePoolConverter {

	/**
	 * Convert to LicensePoolDTO.
	 * @param licensepool
	 * @return LicensePoolDTO.
	 */
	public LicensePoolDTO convertLicensePoolToLicensePoolDTO(LicensePool licensepool);

	/**
	 * Converts a LicensePool to a LicensePoolMappings.
	 * 
	 * @param user
	 *            the LicensePool object.
	 * @param userMapping
	 *            the LicensePoolMapping object
	 * 
	 * @return the LicensePoolMapping object.
	 */
	public LicensePoolMapping convertLicensePoolToLicensePoolMapping(
			LicensePoolDTO licensepool, LicensePoolMapping licensepoolMapping);

	/**
	 * Converts a LicensePoolMapping to a User.
	 * 
	 * @param licensepoolMapping
	 *            the LicensePoolMapping object.
	 * 
	 * @return the LicensePool object.
	 */
	public LicensePoolDTO convertLicensePoolFromLicensePoolMapping(
			LicensePoolMapping userMapping);
	
	/**
	 * Converts CreateLicensePool Schema object to a License Pool DTO.
	 * 
	 * @param createLicensePoolSchemaObj
	 *            the Create LicensePool Schema Object.
	 * 
	 * @return the LicensePool object.
	 */

	public LicensePoolDTO covertCreateRequestToLicensePoolDTO(
			CreateLicensePool createLicensePoolSchemaObj);
}
