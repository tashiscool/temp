package com.pearson.ed.lp.splitter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Splitter;

import com.pearson.ed.lp.message.LicensedProductDataCollection;
import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;

/**
 * Splitter implementation that takes a {@link LicensedProductDataCollection} message payload
 * and separates out data for {@link ProductEntityIdsRequest} and {@link OrderLineItemsRequest} requests.
 * The original {@link LicensedProductDataCollection} is returned with the split collection for final 
 * aggregation downstream.
 * 
 * @author ULLOYNI
 *
 */
public class ProductOrderDetailsRequestSplitter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderDetailsRequestSplitter.class);

	@Splitter
	public List<Object> split(LicensedProductDataCollection licensePoolsAndOrgDisplayNames) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received message to split");
		}
		
		int licensedProductCount = licensePoolsAndOrgDisplayNames.getLicensePools().getLicensePools().size();
		
		List<String> orderLineItemIds = new ArrayList<String>(licensedProductCount);
		List<String> productEntityIds = new ArrayList<String>(licensedProductCount);
		// TODO implementation
		
		List<Object> splitSet = new ArrayList<Object>();
		splitSet.add(new ProductEntityIdsRequest());
		splitSet.add(new OrderLineItemsRequest());
		// add original request as a pass through for the final response aggregator
		splitSet.add(licensePoolsAndOrgDisplayNames);
		
		return splitSet;
	}
	
}
