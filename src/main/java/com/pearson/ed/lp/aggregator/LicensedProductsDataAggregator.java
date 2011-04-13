package com.pearson.ed.lp.aggregator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.LicensedProductDataCollection;
import com.pearson.ed.lp.message.OrganizationDisplayNamesResponse;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

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
		// TODO implementation
		
		LicensedProductDataCollection collectedData = new LicensedProductDataCollection();
		collectedData.setLicensePools(new LicensePoolResponse(new ArrayList<OrganizationLPMapping>(0)));
		collectedData.setOrganizationDisplayNames(new OrganizationDisplayNamesResponse());
		
		return collectedData;
	}
	
}
