package com.pearson.ed.lplc.services.api;

import java.util.List;

import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.dto.UpdateLicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;
import com.pearson.ed.lplc.ws.schema.LicensePoolDetails;

/**
 * Serves as the interface for user services in the LPLC.
 * 
 * @author UCRUZFI
 * 
 */
public interface LicensePoolService {

	/**
	 * Creates all the components for the given license pool.
	 * 
	 * @param licensepool
	 *            the license pool.
	 */
	String createLicensePool(LicensePoolDTO licensepool);

	/**
	 * Update License Pool.
	 * 
	 * @param updateLicensepool
	 * @return licensepoolId
	 */
	String updateLicensePool(UpdateLicensePoolDTO updateLicensepool);

	/**
	 * 
	 * @param organizationId
	 *            organizationId.
	 * @param qualifyingOrgs
	 *            qualifyingOrgs.
	 * @return List.
	 */
	List<OrganizationLPMapping> getLicensePoolByOrganizationId(String organizationId, String qualifyingOrgs);

	/**
	 * 
	 * Get License pool to subscribe. Fetches the license pool Id that qualifies
	 * to be used for subscription for the given organizationId and ProductId.
	 * 
	 * @param organizationId
	 *            - id of the organization.
	 * @param productId
	 *            - Id of the product
	 * @return instance of qualifying LicensePoolMapping object
	 * 
	 * @throw NewSubscriptionsDeniedException - throws this exception if new
	 *        subscriptions are denied for the license pool or organization
	 * @throw LicensePoolExpiredException - throws this exception if license
	 *        pool's start and end dates are out of bound of the current date
	 * @throw LicensePoolForFutureException - throws this exception if existing
	 *        license pools are configured for future use and not available
	 *        currently
	 * @throw LicensePoolUnavailableException - if no license pools exist for
	 *        the given product and organization
	 */
	LicensePoolMapping getLicensePoolToSubscribeId(String organizationId, String productId);

	/**
	 * Gets license pool details for the given license pool id.
	 * 
	 * @param licensePoolId
	 *            id of the license pool.
	 * @return LicensePoolDetails details of license pool.
	 */
	LicensePoolDetails getLicensePoolDetailsById(String licensePoolId);

	/**
	 * This service will find expired license pools that expired yesterday. It
	 * returns list of license pool id.
	 * 
	 * @return List List of license pool id.
	 */
	List<String> findExpiredLicensePool();

	/**
	 * Denies New Subscriptions for the given license pool id.
	 * 
	 * @param licensePoolId
	 *            id of the license pool.
	 * @param deny
	 *            denies new Subscription of the license pool.
	 * @param createdBy
	 *            the created by.
	 * @return licensePoolId.
	 */
	String denyNewSubscriptions(String licensePoolId, int deny, String createdBy);

	/**
	 * Cancels or Revokes a License Pool.
	 * 
	 * @param licensePoolId
	 * 			id of the license pool.
	 * @param createdBy
	 * 			the created by.
	 * @param cancelSubscription
	 * 			cancels a subscription.
	 * 
	 * @return licensepoolId
	 */
	String cancelLicensePool(String licensePoolId, String createdBy, int cancelSubscription);
}
