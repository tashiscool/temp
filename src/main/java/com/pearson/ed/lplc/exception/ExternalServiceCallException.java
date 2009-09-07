package com.pearson.ed.lplc.exception;

/**
 * Thrown to indicated that the Call to the ExternalService Failed.
 * 
 * @author vtirura
 *
 */
public class ExternalServiceCallException extends LPLCBaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * The default constructor for ExternalServiceCallException.
	 */
	public ExternalServiceCallException() {
		super();
	}

	/**
	 * Constructs a new ExternalServiceCallException with the passed message and
	 * cause.
	 * 
	 * @param message
	 * 			the message for the exception.
	 * @param cause
	 * 			the cause of the exception.
	 */
	public ExternalServiceCallException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new ExternalServiceCallException with the passed message.
	 * 
	 * @param message
	 * 			the message for the exception.
	 */
	public ExternalServiceCallException(String message) {
		super(message);
	}

	/**
	 * Constructs a new ExternalServiceCallException with the passed cause. The
	 * message is automatically generated from the cause.
	 * 
	 * @param cause
	 * 			the cause of the exception.
	 */
	public ExternalServiceCallException(Throwable cause) {
		super(cause);
	}
}