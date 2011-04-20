package com.pearson.ed.lplc.exception;

public class LicensePoolExpiredException extends LicensePoolUnavailableException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 4254862954293832060L;

	/**
	 * The default constructor for LicensePoolExpiredException. The message is null and the cause is unspecified.
	 */
	public LicensePoolExpiredException() {
		super();
	}

	/**
	 * Constructs a new LicensePoolExpiredException with the passed message and cause.
	 * 
	 * @param message
	 *            the message for the exception.
	 * @param cause
	 *            the cause of the exception.
	 */
	public LicensePoolExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new LicensePoolExpiredException with the passed message.
	 * 
	 * @param message
	 *            the message for the exception.
	 */
	public LicensePoolExpiredException(String message) {
		super(message);
	}

	/**
	 * Constructs a new LicensePoolExpiredException with the passed cause. The message is automatically generated from
	 * the cause.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public LicensePoolExpiredException(Throwable cause) {
		super(cause);
	}

}
