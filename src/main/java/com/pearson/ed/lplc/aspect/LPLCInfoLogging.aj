package com.pearson.ed.lplc.aspect;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.aspectj.lang.Signature;


public aspect LPLCInfoLogging {
	private static final Logger logger = Logger.getLogger(LPLCInfoLogging.class);
	pointcut traceMethods(): execution(* *(..)) && ! within(LPLCInfoLogging);
	
	before() : traceMethods(){
		Signature sig = thisJoinPointStaticPart.getSignature();
				logger.log(Level.INFO, sig.getDeclaringType().getName() + " " + sig.getName() + "() Entering");
	}
	
	after() returning : traceMethods(){
		Signature sig = thisJoinPointStaticPart.getSignature();
		logger.log(Level.INFO, sig.getDeclaringType().getName() + " " + sig.getName() + "() Exiting");
	}
}
