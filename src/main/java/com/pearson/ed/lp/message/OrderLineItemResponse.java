package com.pearson.ed.lp.message;

/**
 * Response for OrderLifeCycleClient api that gets Ordered ISBN.
 * 
 * @author ULLOYNI
 * 
 */
public class OrderLineItemResponse {

	private String orderedISBN = null;
	private String orderLineItemId = null;

	/**
	 * @return the orderedISBN
	 */
	public String getOrderedISBN() {
		return orderedISBN;
	}

	/**
	 * @param orderedISBN the orderedISBN to set
	 */
	public void setOrderedISBN(String orderedISBN) {
		this.orderedISBN = orderedISBN;
	}

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
