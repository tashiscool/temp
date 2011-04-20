package com.pearson.ed.lp.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Request for OrderLifeCycleClient api that wraps a collection of Order Line Item Id strings.
 * 
 * @author ULLOYNI
 * 
 */
public class OrderLineItemsRequest {

	private List<String> orderLineItemIds = new ArrayList<String>();

	/**
	 * Get list of Order Line Item Id strings.
	 * 
	 * @return list of order line item id strings
	 */
	public List<String> getOrderLineItemIds() {
		return orderLineItemIds;
	}

	/**
	 * Set list of Order Line Item Id strings.
	 * 
	 * @param orderLineItemIds list of order line item id strings
	 */
	public void setOrderLineItemIds(List<String> orderLineItemIds) {
		this.orderLineItemIds = orderLineItemIds;
	}

}
