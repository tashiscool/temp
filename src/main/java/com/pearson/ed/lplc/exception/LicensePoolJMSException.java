package com.pearson.ed.lplc.exception;

public class LicensePoolJMSException extends LPLCBaseException {

	/**
	 * Serial version  UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The default constructor for LicensePoolJMSException. The message is null and
	 * the cause is unspecified.
	 */
	public LicensePoolJMSException() {
		super();	
	}

	/**
	 * Constructs a new LicensePoolJMSException with the passed message and cause.
	 * 
	 * @param message
	 * 			the message for the exception.
	 * @param cause
	 * 			the cause of the exception.
	 */
	public LicensePoolJMSException(String message, Throwable cause) {
		super(message, cause);	
	}

	/**
	 * Constructs a new LicensePoolJMSException with the passed message.
	 * 
	 * @param message
	 * 			the message for the exception.
	 */
	public LicensePoolJMSException(String message) {
		super(message);	
	}

	/**
	 * Constructs a new LicensePoolJMSException with the passed cause. The message is
	 * automatically generated from the cause.
	 * 
	 * @param cause
	 * 			the cause of the exception.
	 */
	public LicensePoolJMSException(Throwable cause) {
		super(cause);	
	}

}
