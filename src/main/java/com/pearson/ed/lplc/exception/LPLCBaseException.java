/**
 * 
 */
package com.pearson.ed.lplc.exception;

import com.pearson.ed.common.exception.RUMBABaseException;

/**
 * Base exception class for all LPLC exceptions. All exceptions for the LPLC will be descended from this one.
 * 
 * @author UCRUZFI
 * 
 */
public class LPLCBaseException extends RUMBABaseException {

	/**
     * 
     */
	private static final long serialVersionUID = -4508972185845086883L;

	/**
	 * The default constructor for LPLCBaseException. The message is null and the cause is unspecified.
	 */
	public LPLCBaseException() {
		super();

	}

	/**
	 * Constructs a new LPLCBaseException with the passed message and cause.
	 * 
	 * @param message
	 *            the message for the exception.
	 * @param cause
	 *            the cause of the exception.
	 */
	public LPLCBaseException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * Constructs a new LPLCBaseException with the passed message.
	 * 
	 * @param message
	 *            the message for the exception.
	 */
	public LPLCBaseException(String message) {
		super(message);

	}

	/**
	 * Constructs a new LPLCBaseException with the passed cause. The message is automatically generated from the cause.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public LPLCBaseException(Throwable cause) {
		super(cause);

	}

}
