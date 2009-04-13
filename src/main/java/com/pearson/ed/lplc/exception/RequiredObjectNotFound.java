/**
 * 
 */
package com.pearson.ed.lplc.exception;

/**
 * This exception is thrown when a required type value is not found. This will
 * be the case for objects that require child objects in order to be considered
 * complete and intact as in the case of a user object which must have a
 * reference to a Person and a user mapping.
 * 
 * @author UCRUZFI
 * 
 */
public class RequiredObjectNotFound extends LPLCBaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 4945261204041892442L;

    /**
     * The default constructor for RequiredObjectNotFound.
     */
    public RequiredObjectNotFound() {
        super();
    }

    /**
     * Constructs a new RequiredObjectNotFound exception with the passed
     * message and cause.
     * 
     * @param message
     * 			the message for the exception.
     * @param cause
     * 			the cause of the exception.
     */
    public RequiredObjectNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new RequiredObjectNotFound exception with the passed
     * message.
     * 
     * @param message
     * 			the message for the exception.
     */
    public RequiredObjectNotFound(String message) {
        super(message);
    }

    /**
     * Constructs a new RequiredObjectNotFound exception with the passed cause.
     * The message is automatically generated from the cause.
     * 
     * @param cause
     * 			the cause of the exception.
     */
    public RequiredObjectNotFound(Throwable cause) {
        super(cause);
    }

}
