package com.pearson.ed.lplc.exception;

/**
 * Thrown to indicate that Organization Id is not valid.
 * 
 * @author vtirura
 * 
 */
public class OrganizationNotValidException extends LPLCBaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * The default constructor for OrganizationNotValidException.
	 */
	public OrganizationNotValidException() {
		super();
	}

	/**
	 * Constructs a new OrganizationNotValidException with the passed message and cause.
	 * 
	 * @param message
	 *            the message for the exception.
	 * @param cause
	 *            the cause of the exception.
	 */
	public OrganizationNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new OrganizationNotValidException with the passed message.
	 * 
	 * @param message
	 *            the message for the exception.
	 */
	public OrganizationNotValidException(String message) {
		super(message);
	}

	/**
	 * Constructs a new OrganizationNotValidException with the passed cause. The message is automatically generated from
	 * the cause.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public OrganizationNotValidException(Throwable cause) {
		super(cause);
	}
}
