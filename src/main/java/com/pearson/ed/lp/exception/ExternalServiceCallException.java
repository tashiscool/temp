/**
 * 
 */
package com.pearson.ed.lp.exception;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;

/**
 * Exception thrown when a call to an external service fails, for instance
 * when the service is unavailable.
 * 
 * @author ULLOYNI
 *
 */
public class ExternalServiceCallException extends AbstractRumbaException {

	private static final long serialVersionUID = 3981989995874692802L;

	public ExternalServiceCallException() {
    }

    public ExternalServiceCallException(String message) {
    	super(message);
    }

    public ExternalServiceCallException(String message,Object[] values) {
    	super(message);
    	setValues(values);
    }
    
    public ExternalServiceCallException(Throwable cause) {
        super(cause);
    }

    public ExternalServiceCallException(String message, Object[] values,Throwable cause) {
        super(message, values, cause);
    }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ExernalServiceCall Exception - ");
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
