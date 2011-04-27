/**
 * 
 */
package com.pearson.ed.lp.exception;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;

/**
 * Exception thrown when an invalid organization id is given to the service.
 * 
 * @author ULLOYNI
 *
 */
public class InvalidOrganizationException extends AbstractRumbaException {

	private static final long serialVersionUID = -4619294619519918979L;

	/**
	 * Default constructor.
	 */
	public InvalidOrganizationException() {
	}

	/**
	 * @param message
	 */
	public InvalidOrganizationException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param values
	 */
	public InvalidOrganizationException(String message, Object[] values) {
		super(message, values);
	}

	/**
	 * @param cause
	 */
	public InvalidOrganizationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param values
	 * @param cause
	 */
	public InvalidOrganizationException(String message, Object[] values, Throwable cause) {
		super(message, values, cause);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("InvalidOrganization Exception - ");
		if (getMessage() == null && getCause() != null) {
			sb.append(", message:").append(getCause().toString());
		} else {
			sb.append(", message:").append(getMessage());
		}
		
		if(getValues() != null) {
			sb.append(", values:").append(getValues());
		}
		
		return sb.toString();
	}

}
