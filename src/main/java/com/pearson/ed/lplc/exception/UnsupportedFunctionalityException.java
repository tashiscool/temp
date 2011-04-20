package com.pearson.ed.lplc.exception;

/**
 * This exception is thrown by a method when it encounters conditions that are not supported by the current
 * implementation. The exception is designed for situations where the functionality may be supported in future releases,
 * but this is not a required condition.
 * 
 * @author UWESTAN
 */

public class UnsupportedFunctionalityException extends LPLCBaseException {

	private static final long serialVersionUID = 426570441575498143L;

	public UnsupportedFunctionalityException() {
		super();
	}

	public UnsupportedFunctionalityException(String message) {
		super(message);
	}

	public UnsupportedFunctionalityException(Throwable cause) {
		super(cause);
	}

	public UnsupportedFunctionalityException(String message, Throwable cause) {
		super(message, cause);
	}
}
