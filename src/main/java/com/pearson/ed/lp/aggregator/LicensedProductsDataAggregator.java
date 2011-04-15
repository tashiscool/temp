package com.pearson.ed.lp.aggregator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.LicensedProductDataCollection;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;

/**
 * Aggregator that collects a {@link LicensePoolResponse} object 
 * and one or more {@link OrganizationDisplayNameResponse} objects into a single
 * {@link LicensedProductDataCollection} object for downstream processing.
 * 
 * @author ULLOYNI
 *
 */
public class LicensedProductsDataAggregator {
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(LicensedProductsDataAggregator.class);

	public LicensedProductDataCollection aggregateResponse(List<Object> responses) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Received messages to aggregate, count: %d", responses.size()));
			LOGGER.debug(responses.toString());
		}
		
		LicensePoolResponse licensePoolResponse = null;
		List<OrganizationDisplayNamesResponse> orgDisplayNameResponses = new ArrayList<OrganizationDisplayNamesResponse>();
		
		for(Object response : responses) {
			if(response instanceof LicensePoolResponse) {
				licensePoolResponse = (LicensePoolResponse)response;
			} else if(response instanceof OrganizationDisplayNamesResponse) {
				orgDisplayNameResponses.add((OrganizationDisplayNamesResponse)response);
			}
		}
		
		OrganizationDisplayNamesResponse mergedResponse = merge(orgDisplayNameResponses);
		
		LicensedProductDataCollection collectedData = new LicensedProductDataCollection();
		collectedData.setLicensePools(licensePoolResponse);
		collectedData.setOrganizationDisplayNames(mergedResponse);
		
		return collectedData;
	}
	
	/**
	 * Merges multiple {@link OrganizationDisplayNamesResponse} instances into one.
	 * 
	 * @param responses {@link List} of {@link OrganizationDisplayNamesResponse}
	 * @return a single, merged {@link OrganizationDisplayNamesResponse} instance
	 */
	private OrganizationDisplayNamesResponse merge(List<OrganizationDisplayNamesResponse> responses) {
		Map<String,String> mergedDisplayNames = new Hashtable<String,String>();
		OrganizationDisplayNamesResponse mergedResponse = new OrganizationDisplayNamesResponse();
		mergedResponse.setOrganizationDisplayNamesByIds(mergedDisplayNames);
		
		for(OrganizationDisplayNamesResponse response : responses) {
			mergedDisplayNames.putAll(response.getOrganizationDisplayNamesByIds());
		}
		
		return mergedResponse;
	}
	
}
