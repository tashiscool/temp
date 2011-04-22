/**
 * 
 */
package com.pearson.ed.lp.exception;

import com.pearson.ed.commons.service.exception.AbstractRumbaException;


/**
 * Exception thrown when a required object for the return result is not
 * found.
 * 
 * @author ULLOYNI
 *
 */
public class RequiredObjectNotFoundException extends AbstractRumbaException {

	private static final long serialVersionUID = -3556917213294311585L;

	public RequiredObjectNotFoundException() {
    }

    public RequiredObjectNotFoundException(String message) {
    	super(message);
    }

    public RequiredObjectNotFoundException(String message,Object[] values) {
    	super(message);
    	setValues(values);
    }
    
    public RequiredObjectNotFoundException(Throwable cause) {
        super(cause);
    }

    public RequiredObjectNotFoundException(String message, Object[] values,Throwable cause) {
        super(message, values, cause);
    }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("RequiredObjectNotFound Exception - ");
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
