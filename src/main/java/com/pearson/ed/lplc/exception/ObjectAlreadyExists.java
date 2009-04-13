package com.pearson.ed.lplc.exception;

public class ObjectAlreadyExists extends LPLCBaseException{
	 /**
     * 
     */
    private static final long serialVersionUID = 4945261204041892449L;

    /**
     * The default constructor for ObjectAlreadyExists.
     */
    public ObjectAlreadyExists() {
        super();
    }

    /**
     * Constructs a new ObjectAlreadyExists exception with the passed
     * message and cause.
     * 
     * @param message
     * 			the message for the exception.
     * @param cause
     * 			the cause of the exception.
     */
    public ObjectAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ObjectAlreadyExists exception with the passed
     * message.
     * 
     * @param message
     * 			the message for the exception.
     */
    public ObjectAlreadyExists(String message) {
        super(message);
    }

    /**
     * Constructs a new ObjectAlreadyExists exception with the passed cause.
     * The message is automatically generated from the cause.
     * 
     * @param cause
     * 			the cause of the exception.
     */
    public ObjectAlreadyExists(Throwable cause) {
        super(cause);
    }
}
