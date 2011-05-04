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
 * Splitter implementation that takes a {@link LicensedProductDataCollection} message payload and separates out data for
 * {@link ProductEntityIdsRequest} and {@link OrderLineItemsRequest} requests. The original
 * {@link LicensedProductDataCollection} is returned with the split collection for final aggregation downstream.
 * 
 * @author ULLOYNI
 * 
 */
public class ProductOrderDetailsRequestSplitter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderDetailsRequestSplitter.class);

	/**
	 * Split a single {@link LicensedProductDataCollection} message into several other messages
	 * for downstream processing.
	 * 
	 * Products of split:
	 * <ul>
	 * <li>{@link OrderLineItemsRequest}</li>
	 * <li>{@link ProductEntityIdsRequest}</li>
	 * <li>{@link LicensedProductDataCollection}, original for passthrough</li>
	 * </ul>
	 * 
	 * @param licensePoolsAndOrgDisplayNames {@link LicensedProductDataCollection} to split
	 * @return {@link List} containing a {@link OrderLineItemsRequest}, {@link ProductEntityIdsRequest}, and 
	 * 			the original request as a pass through
	 */
	@Splitter
	public List<Object> split(LicensedProductDataCollection licensePoolsAndOrgDisplayNames) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received message to split");
		}
		
		if(licensePoolsAndOrgDisplayNames.getLicensePools() == null) {
			LOGGER.error("List of license pool data missing!");
			throw new NullPointerException("List of license pool data missing!");
		}

		int licensedProductCount = licensePoolsAndOrgDisplayNames.getLicensePools().getLicensePools().size();

		List<String> orderLineItemIds = new ArrayList<String>(licensedProductCount);
		List<Long> productEntityIds = new ArrayList<Long>(licensedProductCount);
		OrderLineItemsRequest orderLineItemIdsRequest = new OrderLineItemsRequest();
		orderLineItemIdsRequest.setOrderLineItemIds(orderLineItemIds);
		ProductEntityIdsRequest productEntityIdsRequest = new ProductEntityIdsRequest();
		productEntityIdsRequest.setProductEntityIds(productEntityIds);

		for (OrganizationLPMapping licensePool : licensePoolsAndOrgDisplayNames.getLicensePools().getLicensePools()) {
			// first element is the order line that created the license pool
			orderLineItemIds.add(licensePool.getLicensepoolMapping().getOrderLineItems().iterator().next()
					.getOrderLineItemId());
			// is actually a number in the product DB, and this value is actually the product entity id
			productEntityIds.add(Long.valueOf(licensePool.getLicensepoolMapping().getProduct_id()));
		}

		List<Object> splitSet = new ArrayList<Object>();
		splitSet.add(productEntityIdsRequest);
		splitSet.add(orderLineItemIdsRequest);
		// add original request as a pass through for the final response aggregator
		splitSet.add(licensePoolsAndOrgDisplayNames);

		return splitSet;
	}

}
