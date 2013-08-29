package com.pearson.ed.lplc.services.api;

import org.springframework.integration.annotation.Gateway;

/**
 * Serves as the interface for user services in the LPLC.
 * 
 * @author UCRUZFI
 * 
 */
public interface LicensingCompositeService {

	@Gateway
	void subscribeUser(String licensepoolId, String eventType);
}
