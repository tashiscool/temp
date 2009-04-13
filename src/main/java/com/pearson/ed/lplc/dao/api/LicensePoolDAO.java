package com.pearson.ed.lplc.dao.api;

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
	 * @param licensepoolMapping
	 *            the license pool mapping.
	 */
	public void createLicensePool(LicensePoolMapping lplcMapping);
	/**
	 * Find license pool by name.
	 * @param lplcName
	 * @return LicensePool mapping.
	 */
	public LicensePoolMapping findByLicensePoolId(String lplcId);

}
