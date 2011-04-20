package com.pearson.ed.lplc.exception;

public class LicensePoolUnavailableException extends LPLCBaseException {

	private static final long serialVersionUID = -4508972185845086883L;

	/**
	 * The default constructor for LicensePoolUnavailableException. The message is null and the cause is unspecified.
	 */
	public LicensePoolUnavailableException() {
		super();
	}

	/**
	 * Constructs a new LicensePoolUnavailableException with the passed message and cause.
	 * 
	 * @param message
	 *            the message for the exception.
	 * @param cause
	 *            the cause of the exception.
	 */
	public LicensePoolUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new LicensePoolUnavailableException with the passed message.
	 * 
	 * @param message
	 *            the message for the exception.
	 */
	public LicensePoolUnavailableException(String message) {
		super(message);
	}

	/**
	 * Constructs a new LicensePoolUnavailableException with the passed cause. The message is automatically generated
	 * from the cause.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public LicensePoolUnavailableException(Throwable cause) {
		super(cause);
	}

}
