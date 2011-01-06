package com.pearson.ed.lplc.services.api;

import com.pearson.rws.licensepool.doc._2009._04._01.CreateLicensePool;
import com.pearson.rws.licensepool.doc._2009._04._01.LicensePoolDetails;
import com.pearson.rws.licensepool.doc._2009._04._01.LicensePoolToSubscribe;
import com.pearson.rws.licensepool.doc._2009._04._01.LicensepoolsByOrganizationId;
import com.pearson.rws.licensepool.doc._2009._04._01.UpdateLicensePool;

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
	 * @param request
	 *            organizationID.
	 * @return GetLicensePoolByOrganizationIdResponse
	 *         GetLicensePoolByOrganizationIdResponse.
	 */
	public LicensepoolsByOrganizationId getLicensePoolByOrganizationId(String organizationId, String qualifyingOrgs);

	/**
	 * Get Licensepool to subscribe.
	 * 
	 * @param organizationId
	 *            organizationId.
	 * @param productId
	 * @return
	 */
	public LicensePoolToSubscribe getLicensePoolToSubscribe(String organizationId, String productId);

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

	/**
	 * Gets license pool details for the given license pool id.
	 * 
	 * @param licensePoolId
	 *            id of the license pool.
	 * @return LicensePoolDetails details of license pool.
	 */
	LicensePoolDetails getLicensePoolDetailsById(String licensePoolId);

	/**
	 * Denies New Subscriptions for the given license pool id.
	 * 
	 * @param licensePoolId
	 *            id of the license pool.
	 * @param requestIsDenied
	 *            denies new Subscription of the license pool.
	 * @param createdBy
	 *            the created by.
	 * @return licenseId.
	 */
	String denyNewSubscriptions(String licensePoolId, boolean requestIsDenied, String createdBy);

	/**
	 * Cancels or Revokes a License Pool.
	 * 
	 * @param licensePoolId
	 *            id of the license pool.
	 * @param createdBy
	 *            the created by.
	 * @param isCancel
	 *            cancels a subscription.
	 * 
	 * @return licensepoolId
	 */
	String cancel(String licensePoolId, String createdBy, boolean isCancel);

}
