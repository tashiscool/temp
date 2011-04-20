package com.pearson.ed.lplc.dao.api;

import java.util.Date;
import java.util.List;

import com.pearson.ed.lplc.model.LicensePoolMapping;

/**
 * LicensePoolMappingDAO is the interface for user mapping data access objects in the LPLC.
 * 
 * @author RUMBA
 */

public interface LicensePoolDAO {

	/**
	 * Creates a license pool mapping.
	 * 
	 * @param lplcMapping
	 *            the license pool mapping.
	 */
	public void createLicensePool(LicensePoolMapping lplcMapping);

	/**
	 * Find license pool by name.
	 * 
	 * @param lplcId
	 *            lplcId.
	 * @return LicensePool mapping.
	 */
	public LicensePoolMapping findByLicensePoolId(String lplcId);

	/**
	 * Update license pool.
	 * 
	 * @param licensepool
	 *            licensepool.
	 */
	public void update(LicensePoolMapping licensepool);

	/**
	 * 
	 * @param organizationId
	 *            organizationId.
	 * @param productId
	 *            productId.
	 * @param asOfDate
	 *            - To check license pool start date and end date lie within the given date
	 * @param considerDenySubscriptions
	 *            - if true checks if denySubscriptions is not set
	 * 
	 * @return list of licensepoool.
	 * 
	 */
	public List<LicensePoolMapping> findOrganizationMappingToSubscribe(String organizationId, String productId,
			Date asOfDate, boolean considerDenySubscriptions);

	/**
	 * This service will find expired license pools that expired yesterday. It returns list of license pool id.
	 * 
	 * @return List List of license pool id.
	 */

	public List<String> findExpiredLicensePool();

}
