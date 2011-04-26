/**
 * 
 */
package com.pearson.ed.lp.exception;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;

/**
 * Exception thrown when the order line for a licensed product can not be found.
 * 
 * @author ULLOYNI
 *
 */
public class OrderLineNotFoundException extends AbstractRumbaException {

	private static final long serialVersionUID = 5582793082003167449L;

	/**
	 * Default constructor.
	 */
	public OrderLineNotFoundException() {
	}

	/**
	 * @param message
	 */
	public OrderLineNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param values
	 */
	public OrderLineNotFoundException(String message, Object[] values) {
		super(message, values);
	}

	/**
	 * @param cause
	 */
	public OrderLineNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param values
	 * @param cause
	 */
	public OrderLineNotFoundException(String message, Object[] values, Throwable cause) {
		super(message, values, cause);
	}

}
