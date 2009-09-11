
package com.pearson.ed.lplc.aspect.service;

import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.pearson.ed.lplc.exception.ComponentValidationException;
import com.pearson.ed.lplc.exception.LPLCBaseException;
import com.pearson.ed.lplc.exception.LPLCFieldsNotValidException;
//import com.pearson.ed.lplc.ws.schema.LicensePool;
import com.pearson.ed.lplc.services.api.LicensePoolService;;

public aspect LicensePoolServiceExceptionHandling {
	private static final Logger logger = Logger.getLogger(LicensePoolServiceExceptionHandling.class);
	// Triggering exception
//	pointcut CRUDOperation(LicensePool domainObject) :
//		(execution(* LicensePoolService+.*LicensePool(..)))
//		&& args(domainObject);	
	
//	after(LicensePoolDetails domainObject) throwing (Throwable ex) : CRUDOperation(domainObject) {
//		StringBuffer aLogMessage = new StringBuffer("");

//		if(ex instanceof ComponentValidationException){
//			ComponentValidationException cvEx = (ComponentValidationException) ex;
//			List<String> validationErrors = cvEx.getValidationErrors();
		
//			aLogMessage.append(ex.toString()); // log thrown exception info
//			if(validationErrors != null){
//				for (Iterator <String>it = validationErrors.iterator(); it.hasNext();) {
//					if(aLogMessage.length() == 0 ){
//						aLogMessage.append(it.next());
//					}
//					else{
//						aLogMessage.append("; ");
//						aLogMessage.append(it.next());
//					}
//				}
//			}
//			logger.log(Level.ERROR, aLogMessage.toString());
//			throw new LPLCFieldsNotValidException(aLogMessage.toString(), ex);
//		}
//		else{
//			aLogMessage.append(ex.toString());
//			logger.log(Level.ERROR, aLogMessage.toString());
//			throw new LPLCBaseException(aLogMessage.toString(), ex);
//		}
//		
//	}
}

