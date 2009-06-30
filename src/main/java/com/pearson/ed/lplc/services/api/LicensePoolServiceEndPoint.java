package com.pearson.ed.lplc.services.api;

import com.pearson.ed.lplc.ws.schema.CreateLicensePool;
import com.pearson.ed.lplc.ws.schema.LicensepoolsByOrganizationId;
import com.pearson.ed.lplc.ws.schema.UpdateLicensePool;

/**
 * Serves as the interface for license pool service endpoints in the LPLC.
 * 
 */

public interface LicensePoolServiceEndPoint extends EntityEndpoint {
	/**
	 * Creates all the components for the given user. Optional components like
	 * email and phone will only be created if they are present.
	 * 
	 * @param licenseId
	 *            the license pool to be created.
	 */
	String createLicensePool(CreateLicensePool licensepool);

	/**
	 * Update LicensePool
	 * 
	 * @param licensepool
	 * @return licenseId
	 */
	String updateLicensePool(UpdateLicensePool licensepool);
	
	/**
	 * Get LicensePool data based on organization
	 * 
	 * @param request organizationID.
	 * @return GetLicensePoolByOrganizationIdResponse GetLicensePoolByOrganizationIdResponse.
	 */
	public LicensepoolsByOrganizationId getLicensePoolByOrganizationId(String organizationId, String qualifyingOrgs);

	/**
	 * Generates a transaction ID.
	 * 
	 * @return the transaction ID.
	 */
	
	
	public String generateTransactionId();

	/**
	 * Sets the transaction ID.
	 * 
	 * @param transactionId
	 *            the transaction ID.
	 */
	public void setTransactionId(String transactionId);
		

}
