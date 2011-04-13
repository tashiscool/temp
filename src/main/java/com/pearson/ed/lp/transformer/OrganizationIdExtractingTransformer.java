package com.pearson.ed.lp.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Transformer;

import com.pearson.ed.lp.message.LicensePoolByOrganizationIdRequest;

/**
 * Simple transformer to extract the organization id string from a passed
 * LicensePoolByOrganizationIdRequest object.
 * 
 * @author ULLOYNI
 *
 */
public class OrganizationIdExtractingTransformer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationIdExtractingTransformer.class);

	@Transformer
	public String extractOrganizationId(LicensePoolByOrganizationIdRequest request) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received request to transform");
		}
		return request.getOrganizationId();
	}
	
}
