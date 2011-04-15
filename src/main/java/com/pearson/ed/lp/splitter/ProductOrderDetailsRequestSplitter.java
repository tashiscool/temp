package com.pearson.ed.lp.splitter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Splitter;

import com.pearson.ed.lp.message.LicensedProductDataCollection;
import com.pearson.ed.lp.message.OrderLineItemsRequest;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

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
		List<Long> productEntityIds = new ArrayList<Long>(licensedProductCount);
		OrderLineItemsRequest orderLineItemIdsRequest = new OrderLineItemsRequest();
		orderLineItemIdsRequest.setOrderLineItemIds(orderLineItemIds);
		ProductEntityIdsRequest productEntityIdsRequest = new ProductEntityIdsRequest();
		productEntityIdsRequest.setProductEntityIds(productEntityIds);
		
		for(OrganizationLPMapping licensePool : licensePoolsAndOrgDisplayNames.getLicensePools().getLicensePools()) {
			// first element is the order line that created the license pool
			orderLineItemIds.add(licensePool.getLicensepoolMapping()
					.getOrderLineItems().iterator().next().getOrderLineItemId());
			// is actually a number in the product DB, and this value is actually the product entity id
			productEntityIds.add(Long.valueOf(licensePool.getLicensepoolMapping().getProduct_id()));
		}
		
		List<Object> splitSet = new ArrayList<Object>();
		splitSet.add(orderLineItemIdsRequest);
		splitSet.add(productEntityIdsRequest);
		// add original request as a pass through for the final response aggregator
		splitSet.add(licensePoolsAndOrgDisplayNames);
		
		return splitSet;
	}
	
}
