/**
 * 
 */
package com.pearson.ed.lp.exception;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;

/**
 * Exception thrown when a licensed product can not be found.
 * Typically thrown when a license pool can not be found for a
 * given organization or an organization hierarchy.
 * 
 * @author ULLOYNI
 *
 */
public class ProductNotFoundException extends AbstractRumbaException {

	private static final long serialVersionUID = 343181013688009203L;
	
	/**
	 * Default constructor.
	 */
	public ProductNotFoundException() {
	}

	/**
	 * @param message
	 */
	public ProductNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param values
	 */
	public ProductNotFoundException(String message, Object[] values) {
		super(message, values);
	}

	/**
	 * @param cause
	 */
	public ProductNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param values
	 * @param cause
	 */
	public ProductNotFoundException(String message, Object[] values, Throwable cause) {
		super(message, values, cause);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProductNotFound Exception - ");
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
