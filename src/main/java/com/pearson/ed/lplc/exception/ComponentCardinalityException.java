/**
 * 
 */
package com.pearson.ed.lplc.exception;

/**
 * Thrown to indicated that a component has an incorrect or unexpected cardinality.
 * 
 * @author UCRUZFI
 */
public class ComponentCardinalityException extends LPLCBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 123702958060208486L;

	/**
	 * The default constructor for ComponentCardinalityException.
	 */
	public ComponentCardinalityException() {
		super();
	}

	/**
	 * Constructs a new ComponentCardinalityException with the passed message and cause.
	 * 
	 * @param message
	 *            the message for the exception.
	 * @param cause
	 *            the cause of the exception.
	 */
	public ComponentCardinalityException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new ComponentCardinalityException with the passed message.
	 * 
	 * @param message
	 *            the message for the exception.
	 */
	public ComponentCardinalityException(String message) {
		super(message);
	}

	/**
	 * Constructs a new ComponentCardinalityException with the passed cause. The message is automatically generated from
	 * the cause.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public ComponentCardinalityException(Throwable cause) {
		super(cause);
	}

}
