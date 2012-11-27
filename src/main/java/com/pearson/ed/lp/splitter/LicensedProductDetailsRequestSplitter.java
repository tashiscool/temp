package com.pearson.ed.lp.splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Splitter;

import com.pearson.ed.lp.message.LicensePoolResponse;
import com.pearson.ed.lp.message.OrderLineItemRequest;
import com.pearson.ed.lp.message.OrganizationDisplayNameRequest;
import com.pearson.ed.lp.message.ProductEntityIdsRequest;
import com.pearson.ed.lplc.model.LicensePoolMapping;
import com.pearson.ed.lplc.model.OrderLineItemLPMapping;
import com.pearson.ed.lplc.model.OrganizationLPMapping;

/**
 * Splitter implementation that takes a {@link LicensePoolResponse} message payload and separates out data for
 * {@link ProductEntityIdsRequest}, {@link OrganizationDisplayNameRequest} and {@link OrderLineItemRequest} requests. The original
 * {@link LicensePoolResponse} is returned with the split collection for final aggregation downstream.
 * 
 * @author ULLOYNI
 * 
 */
public class LicensedProductDetailsRequestSplitter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LicensedProductDetailsRequestSplitter.class);

	/**
	 * Split a single {@link LicensePoolResponse} message into several other messages
	 * for downstream processing.
	 * 
	 * Products of split:
	 * <ul>
	 * <li>{@link OrderLineItemRequest}</li>
	 * <li>{@link OrganizationDisplayNameRequest}</li>
 	 * <li>{@link ProductEntityIdsRequest}</li>
	 * <li>{@link LicensePoolResponse}, original for passthrough</li>
	 * </ul>
	 * 
	 * @param licensePoolResponse {@link LicensePoolResponse} to split
	 * @return {@link List} containing a {@link OrderLineItemRequest}, {@link OrganizationDisplayNameRequest}, 
	 * {@link ProductEntityIdsRequest}, and the original request as a pass through
	 */
	@Splitter
	public List<Object> split(LicensePoolResponse licensePoolResponse) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received message to split");
		}
		
		if(licensePoolResponse == null) {
			LOGGER.error("Required LicensePoolResponse message not received!");
			throw new NullPointerException("Required LicensePoolResponse message not received!");
		}
		
		int responseCount = licensePoolResponse.getLicensePools().size();

		List<Long> productEntityIds = new ArrayList<Long>(responseCount);

		List<Object> splitSet = new ArrayList<Object>((responseCount*2)+2);
		
		OrganizationDisplayNameRequest organizationDisplayNameRequest = null;
		OrderLineItemRequest orderLineItemRequest = null;
		ProductEntityIdsRequest productEntityIdsRequest = new ProductEntityIdsRequest();
		productEntityIdsRequest.setProductEntityIds(productEntityIds);

		for (OrganizationLPMapping licensePool : licensePoolResponse.getLicensePools()) {
			LicensePoolMapping lpMapping = licensePool.getLicensepoolMapping();
			Set<OrderLineItemLPMapping> orderLineItems = lpMapping.getOrderLineItems();
			if (orderLineItems.isEmpty()) {
				throw new IllegalStateException("no order line items in " + lpMapping.toString());
			}

			String orderLineItemId =
				orderLineItems				
					.iterator()
					.next()
					.getOrderLineItemId();

			orderLineItemRequest = new OrderLineItemRequest();
			// first element is the order line that created the license pool
			orderLineItemRequest.setOrderLineItemId(orderLineItemId);

			organizationDisplayNameRequest = new OrganizationDisplayNameRequest();
			organizationDisplayNameRequest.setOrganizationId(lpMapping.getOrg_id());
			
			// is actually a number in the product DB, and this value is actually the product entity id
			productEntityIds.add(Long.valueOf(lpMapping.getProduct_id()));
			splitSet.add(organizationDisplayNameRequest);
			splitSet.add(orderLineItemRequest);
		}
		if(!productEntityIdsRequest.getProductEntityIds().isEmpty()) {
			splitSet.add(productEntityIdsRequest);
		}
		// add original request as a pass through for the final response aggregator
		splitSet.add(licensePoolResponse);

		return splitSet;
	}

}
