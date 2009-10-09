/**
 * 
 */
package com.pearson.ed.lplc.common;

import java.util.Date;

/**
 * All the constants used for the LPLC service. These may be migrated out to the
 * configuration service at some point, but for now they will live here.
 * 
 * 
 */
public interface LPLCConstants {

	/**
	 * Indicates that the default user who is updating/creation is System.
	 */
	public static final String DEFAULT_USER = "System";

	/**
	 * Default date.
	 */
	public static final Date DEFAULT_DATE = new Date();

	/**
	 * Default name type.
	 */
	public static final String DEFAULT_NAME_TYPE = "P";

	/**
	 * Default email type.
	 */
	public static final String DEFAULT_EMAIL_TYPE = "P";

	/**
	 * For Resetting the password.
	 */
	public static final String RESET_PASSWD_YES = "Y";
	/**
	 * Student Organization specific role.
	 */
	public static final String ORG_ROLE_STUDENT = "S";

	/**
	 * Default organization code.
	 */
	public static final int DEFAULT_ORG_TYPE_CODE = 1;
	/**
	 * About email.
	 */
	public static final String EMAIL_VALIDATED = "Y";
	
	/**
	 * Constant to denote denial of new subscriptions 
	 */
	public static final int DENY_SUBSCRIPTIONS_TRUE = 1;
	
	/**
	 * Constant to denote non-denial of new subscriptions
	 */
	public static final int DENY_SUBSCRIPTIONS_FALSE = 0;

	/**
	 * Service response constants
	 */
	public static final String SERVICE_RESPONSE_CODE_SUCCESS = "SUCCESS";
	/**
	 * Service response constants
	 */
	public static final String SERVICE_RESPONSE_CODE_FAILURE = "FAILURE";
	/**
	 * Service response constants
	 */
	public static final String SERVICE_RESPONSE_CODE_WARNING = "WARNING";
	/**
	 * Service response constants
	 */
	public static final String SERVICE_RESPONSE_CODE_IGNORED = "IGNORED";
	/**
	 * Service response constants
	 */

	public static final String SERVICE_RESPONSE_RETURN_FAILURE = "-1";
	/**
	 * Different modes.
	 */
	public static final String CREATE_MODE = "Create";

	public static final String UPDATE_MODE = "Update";
	
	public static final String 	QUALIFYING_ORGS_ROOT = 	"ROOT_ONLY";

	public static final String LICENSEPOOLTYPE = "Seat based licensing";
	
	/**
	 * Different status codes for license pool.
	 */
	public static final String STATUS_ACTIVE = "A";
	
	public static final String STATUS_PROCESS = "P";
	
	public static final String STATUS_EXPIRED = "E";
	
	public static final String STATUS_CANCELLED = "C";
	
	/**
	 * Represents a licensepool has cancelled.
	 */
	public static final String IS_CANCELLED_YES = "Yes";
	
	/**
	 * Represents a licensepool has not cancelled.
	 */
	public static final String IS_CANCELLED_NO = "No";
}
