package com.pearson.ed.lp.aggregator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Aggregator;

import com.pearson.rws.licensedproduct.doc.v2.GetLicensedProductResponseElement;

public class LicensedProductsResponseAggregator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LicensedProductsResponseAggregator.class);
	
	@Aggregator
	public GetLicensedProductResponseElement aggregateResponse(List<Object> responseMessages) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Received response messages to aggregate, message count: %d", 
					responseMessages.size()));
			LOGGER.debug(responseMessages.toString());
		}
		// TODO implementation
		return new GetLicensedProductResponseElement();
	}
	
}
