package com.pearson.ed.lp.exception;

/**
 * Exception codes for GetLicensedProduct V2 service.
 * 
 * @author ULLOYNI
 *
 */
public enum LicensedProductExceptionCode {
	
	/**
	 * Default error.
	 */
	LP0001,
	
	/**
	 * Unspecified LicensedProduct error
	 */
	LP0002,
	
	/**
	 * Invalid LicensedProduct fields
	 */
	LP0003,
	
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
