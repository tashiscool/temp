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
	 * Unspecified LicensedProduct error
	 */
	LP_EXC_0001,
	
	/**
	 * Organization not found
	 */
	LP_EXC_0002,
	
	/**
	 * Product not found
	 */
	LP_EXC_0003,
	
	/**
	 * Order line item for Order not found
	 */
	LP_EXC_0004,
	
	/**
	 * Missing Product display information
	 */
	LP_EXC_0005,
	
	/**
	 * Missing Organization display information
	 */
	LP_EXC_0006;

}
