package com.pearson.ed.lp.message;

/**
 * Request for OrderLifeCycleClient api.
 * 
 * @author ULLOYNI
 * 
 */
public class OrderLineItemRequest {

	private String orderLineItemId = null;

	/**
	 * @return the orderLineItemId
	 */
	public String getOrderLineItemId() {
		return orderLineItemId;
	}

	/**
	 * @param orderLineItemId the orderLineItemId to set
	 */
	public void setOrderLineItemId(String orderLineItemId) {
		this.orderLineItemId = orderLineItemId;
	}

}
