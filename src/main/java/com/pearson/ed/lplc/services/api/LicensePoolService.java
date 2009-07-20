/**
 * 
 */
package com.pearson.ed.lplc.services.api;

import java.util.List;

import com.pearson.ed.lplc.dto.LicensePoolDTO;
import com.pearson.ed.lplc.dto.UpdateLicensePoolDTO;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

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
	List<OrganizationLPMapping> getLicensePoolByOrganizationId(
			String organizationId, String qualifyingOrgs);
	/**
	 * Get Licensepool For Subscription.
	 * @param organizationId
	 * @param productId
	 * @return OrganizationLPMapping
	 */

	LicensePoolMapping getLicensePoolToSubscribeId(String organizationId, String productId);

}
