/**
 * 
 */
package com.pearson.ed.lp.exception;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;

/**
 * Exception for when data required from a retrieved object is not found.
 * Some data elements that are optional for some objects are required
 * by this service.
 * 
 * @author ULLOYNI
 *
 */
public class InvalidObjectException extends AbstractRumbaException {

	private static final long serialVersionUID = 942820947143260857L;

	public InvalidObjectException() {
    }

    public InvalidObjectException(String message) {
    	super(message);
    }

    public InvalidObjectException(String message,Object[] values) {
    	super(message);
    	setValues(values);
    }
    
    public InvalidObjectException(Throwable cause) {
        super(cause);
    }

    public InvalidObjectException(String message, Object[] values,Throwable cause) {
        super(message, values, cause);
    }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("InvalidObject Exception - ");
		if (getMessage() == null && getCause() != null) {
			sb.append(", message:").append(getCause().toString());
		} else {
			sb.append(", message:").append(getMessage());
		}
		
		if(getValues() != null) {
			sb.append(", values:").append(getValues());
		}
		
		return sb.toString();
	}

}
