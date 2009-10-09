package com.pearson.ed.lplc.services.converter.api;

import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.dto.UpdateLicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.ws.schema.CreateLicensePool;
import com.pearson.ed.lplc.ws.schema.LicensePoolDetails;
import com.pearson.ed.lplc.ws.schema.LicensePoolToSubscribe;
import com.pearson.ed.lplc.ws.schema.LicensepoolsByOrganizationId;
import com.pearson.ed.lplc.ws.schema.UpdateLicensePool;

/**
 * A converter class which handles the interchange between LicensePools and
 * LicensePoolMappings.
 *
 * @author RUMBA
 */

public interface LicensePoolConverter {


	/**
	 * Converts a LicensePool to a LicensePoolMappings.
	 *
	 * @param licensepool
	 *            the LicensePool object.
	 * @param licensepoolMapping
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

	/**
	 * Converts updateLicensepoolSchema object to update licensepooldto.
	 *
	 * @param licensepool
	 * @return update licensepool DTO.
	 */
	public UpdateLicensePoolDTO convertupdateRequestToUpdateLicensePoolDTO(
			UpdateLicensePool licensepool);
	
	/**
	 * Build LicensepoolMapping from UpdateLicensepoolDTO.
	 * @param updateLicensepool
	 * @param licensepool
	 */

	public void buildLicensepoolMappingFromUpdateLicensepoolDTO(
			UpdateLicensePoolDTO updateLicensepool,
			LicensePoolMapping licensepool);
	
	/**
	 *
	 * @param licenses list of OrganizationLPMapping.
	 * @return LicensepoolsByOrganizationId.
	 * @throws DatatypeConfigurationException
	 */

	public LicensepoolsByOrganizationId convertForGetFromLPMappingToSchema(
			List<OrganizationLPMapping> licenses);
	/**
	 * Convert method to convert LPMapping object to Schema object.
	 * @param organizationLPMapping organizationLPMapping.
	 * @return LicensePoolToSubscribe.
	 */

	public LicensePoolToSubscribe convertForGetLicensepoolToSubscribe(LicensePoolMapping organizationLPMapping);

	/**
	 * Converts LicensePoolMapping object to LicensePoolDetails object.
	 *
	 * @param licensePool
	 *            LicensePoolMapping object.
	 * @return LicensePoolDetails.
	 */
	LicensePoolDetails convertLicensePoolMappingToLicensePoolDetails(LicensePoolMapping licensePool);

}
