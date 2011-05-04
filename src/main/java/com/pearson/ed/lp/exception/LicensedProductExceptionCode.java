package com.pearson.ed.lp.exception;

/**
 * Exception codes for GetLicensedProduct V2 service.
 * 
 * @author ULLOYNI
 *
 */
public enum LicensedProductExceptionCode {
	
	/**
	 * Unspecified LicensedProduct error
	 */
	LP0001,
	
	/**
	 * Required data not found
	 */
	LP0002,
	
	/**
	 * Product not found
	 */
	PLC0006,
	
	/**
	 * Organization not found
	 */
	OLC0006,
	
	/**
	 * Order line item for Order not found
	 */
	ORLC006;

}
