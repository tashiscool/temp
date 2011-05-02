/**
 * 
 */
package com.pearson.ed.lp.exception;

/**
 * Exception message codes for GetLicensedProduct V2 service.
 * 
 * @author ULLOYNI
 *
 */
public enum LicensedProductExceptionMessageCode {
	
	/**
	 * Default error.
	 */
	LP_EXC_0001,
	
	/**
	 * Unspecified LicensedProduct error
	 */
	LP_EXC_0002,
	
	/**
	 * Organization not found
	 */
	LP_EXC_0003,
	
	/**
	 * Licensed Products for Organization not found
	 */
	LP_EXC_0004,
	
	/**
	 * Product not found
	 */
	LP_EXC_0005,
	
	/**
	 * Order line item for Order not found
	 */
	LP_EXC_0006,
	
	/**
	 * Missing Product display information
	 */
	LP_EXC_0007;

}
