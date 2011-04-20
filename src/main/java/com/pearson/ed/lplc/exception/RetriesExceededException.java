package com.pearson.ed.lplc.exception;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * This exception is thrown by a method when the maximum number of retries for a task has been exceeded.
 * 
 * @author UWESTAN
 */

public class RetriesExceededException extends LPLCBaseException {

	private static final long serialVersionUID = 1127378645087584898L;
	private static NumberFormat formatter = new DecimalFormat();

	public RetriesExceededException() {
		super();
	}

	public RetriesExceededException(String message) {
		super(message);
	}

	public RetriesExceededException(Throwable cause) {
		super(cause);
	}

	public RetriesExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetriesExceededException(Integer numRetries) {
		super("Maximum number of retries exceeded: " + formatter.format(numRetries.intValue()));
	}
}
