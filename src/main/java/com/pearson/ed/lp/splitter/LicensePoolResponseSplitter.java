package com.pearson.ed.lp.splitter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Splitter;

import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

public class LicensePoolResponseSplitter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LicensePoolResponseSplitter.class);

	@Splitter
	public List<Object> split(List<OrganizationLPMapping> licensePools) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received message to split");
		}
		
		List<String> orderLineItemIds = new ArrayList<String>(licensePools.size());
		List<String> productEntityIds = new ArrayList<String>(licensePools.size());
		// TODO implementation
		
		List<Object> splitSet = new ArrayList<Object>();
		splitSet.add(new ProductEntityIdsRequest());
		splitSet.add(new OrderLineItemsRequest());
		
		return splitSet;
	}
	
}
