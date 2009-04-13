/**
 * 
 */
package com.pearson.ed.lplc.services.api;

import com.pearson.ed.lplc.dto.LicensePoolDTO;

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

}
