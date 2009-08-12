package com.pearson.ed.lplc.exception;

public class LicensePoolForFutureException extends LPLCBaseException {

	private static final long serialVersionUID = -4508972185845086883L;
	
	/**
	 * The default constructor for LicensePoolForFutureException. The message is null and
	 * the cause is unspecified.
	 */
	public LicensePoolForFutureException() {
		super();
	}

	/**
	 * Constructs a new LicensePoolForFutureException with the passed message and cause.
	 * 
	 * @param message
	 * 			the message for the exception.
	 * @param cause
	 * 			the cause of the exception.
	 */
	public LicensePoolForFutureException(String message, Throwable cause) {
		super(message, cause);	
	}

	/**
	 * Constructs a new LicensePoolForFutureException with the passed message.
	 * 
	 * @param message
	 * 			the message for the exception.
	 */
	public LicensePoolForFutureException(String message) {
		super(message);	
	}

	/**
	 * Constructs a new LicensePoolForFutureException with the passed cause. The message is
	 * automatically generated from the cause.
	 * 
	 * @param cause
	 * 			the cause of the exception.
	 */
	public LicensePoolForFutureException(Throwable cause) {
		super(cause);	
	}


}
