package com.pearson.ed.lplc.dao.api;

import java.util.List;

import com.pearson.ed.lplc.model.LicensePoolMapping;

/**
 * LicensePoolMappingDAO is the interface for user mapping data access objects in the
 * LPLC.
 * 
 * @author RUMBA
 */

public interface LicensePoolDAO {

	/**
	 * Creates a license pool mapping.
	 * 
	 * @param lplcMapping the license pool mapping.
	 */
	public void createLicensePool(LicensePoolMapping lplcMapping);
	/**
	 * Find license pool by name.
	 * @param lplcId lplcId.
	 * @return LicensePool mapping.
	 */
	public LicensePoolMapping findByLicensePoolId(String lplcId);
	
	
	/**
	 * Update license pool.
	 * @param licensepool licensepool.
	 */
	public void update(LicensePoolMapping licensepool);
	/**
     * 
     * @param organizationId organizationId.
     * @param productId productId.
     * @return list of licensepoool.
     */
	public List<LicensePoolMapping> findOrganizationMappingToSubscribe(String organizationId,
			String productId);
	
}
