package com.pearson.ed.lp.message;

import java.util.Hashtable;
import java.util.Map;

/**
 * Response for OrderLifeCycleClient api which wraps
 * a map of Order Line Item Id strings to Ordered ISBN strings.
 * 
 * @author ULLOYNI
 *
 */
public class OrderLineItemsResponse {

	private Map<String,String> orderedISBNsByOrderLineItemIds = new Hashtable<String,String>();

	/**
	 * Get map of Order Line Item Id strings to Ordered ISBN strings.
	 * @return
	 */
	public Map<String, String> getOrderedISBNsByOrderLineItemIds() {
		return orderedISBNsByOrderLineItemIds;
	}

	/**
	 * Set map of Order Line Item Id strings to Ordered ISBN strings.
	 * @param orderedISBNsByOrderLineItemIds
	 */
	public void setOrderedISBNsByOrderLineItemIds(Map<String, String> orderedISBNsByOrderLineItemIds) {
		this.orderedISBNsByOrderLineItemIds = orderedISBNsByOrderLineItemIds;
	}
}