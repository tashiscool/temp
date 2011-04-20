package com.pearson.ed.lplc.exception;

/**
 * Represents LicensePool Canceled Exception class.
 * 
 * @author vc999el
 * 
 */
public class LicensePoolCanceledException extends LPLCBaseException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 4254862954293832060L;

	/**
	 * The default constructor for LicensePoolCanceledException. The message is null and the cause is unspecified.
	 */
	public LicensePoolCanceledException() {
		super();
	}

	/**
	 * Constructs a new LicensePoolCanceledException with the passed message and cause.
	 * 
	 * @param message
	 *            the message for the exception.
	 * @param cause
	 *            the cause of the exception.
	 */
	public LicensePoolCanceledException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new LicensePoolCanceledException with the passed message.
	 * 
	 * @param message
	 *            the message for the exception.
	 */
	public LicensePoolCanceledException(String message) {
		super(message);
	}

	/**
	 * Constructs a new LicensePoolCanceledException with the passed cause. The message is automatically generated from
	 * the cause.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public LicensePoolCanceledException(Throwable cause) {
		super(cause);
	}

}
