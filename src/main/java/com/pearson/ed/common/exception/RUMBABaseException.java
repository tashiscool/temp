/**
 * 
 */
package com.pearson.ed.common.exception;

/**
 * Base class for all RUMBA runtime exceptions.
 *
 */
public class RUMBABaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3875455021574787121L;

	/**
     * The default constructor for RUMBABaseException. The message is null and
     * the cause is unspecified.
     */
    public RUMBABaseException() {
    }

    /**
     * Constructs a new RUMBABaseException with the passed message.
     * 
     * @param message
     * 			the message for the exception.
     */
    public RUMBABaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new RUMBABaseException with the passed cause. The message
     * is automatically generated from the cause.
     * 
     * @param cause
     * 			the cause of the exception.
     */
    public RUMBABaseException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new RUMBABaseException with the passed message and cause.
     * 
     * @param message
     * 			the message for the exception.
     * @param cause
     * 			the cause of the exception.
     */
    public RUMBABaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
