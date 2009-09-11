package com.pearson.ed.lplc.common;

public class LPLCErrorMessages {
	
	/**
	 * When end date is less than start date.
	 */
	public static final String DATE_ERROR = "Start Date can not be greater than End Date.";
	/**
	 * When organizaiton id is not found to update licensepool consumption.
	 */
	public static final String NO_ORGNAIZATION_FOUND_UPDATE_LICENSEPOOL = "No organization found to update license consumption.";
	/**
	 * When licensepool consumption is updated to negative.
	 */
	public static final String LICENSEPOOL_CONSUMPTION_NEGATIVE = "License consuption can not be updated to a negative value.";
	/**
	 * Error message for date format exception.
	 */	
	public static final String DATE_FORMAT_ERROR = "Could not format date to XMLGregorianCalendar.";
}
