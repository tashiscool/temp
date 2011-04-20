package com.pearson.ed.lplc.exception;

public class NewSubscriptionsDeniedException extends LicensePoolUnavailableException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 4610237399225739504L;

	/**
	 * The default constructor for NewSubscriptionsDeniedException. The message is null and the cause is unspecified.
	 */
	public NewSubscriptionsDeniedException() {
		super();
	}

	/**
	 * Constructs a new NewSubscriptionsDeniedException with the passed message and cause.
	 * 
	 * @param message
	 *            the message for the exception.
	 * @param cause
	 *            the cause of the exception.
	 */
	public NewSubscriptionsDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new NewSubscriptionsDeniedException with the passed message.
	 * 
	 * @param message
	 *            the message for the exception.
	 */
	public NewSubscriptionsDeniedException(String message) {
		super(message);
	}

	/**
	 * Constructs a new NewSubscriptionsDeniedException with the passed cause. The message is automatically generated
	 * from the cause.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public NewSubscriptionsDeniedException(Throwable cause) {
		super(cause);
	}

}
