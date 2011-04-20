package com.pearson.ed.lp.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Request for the ProductLifeCycleClient api which wraps a collection of Product Entity Id values.
 * 
 * @author ULLOYNI
 * 
 */
public class ProductEntityIdsRequest {

	private List<Long> productEntityIds = new ArrayList<Long>();

	/**
	 * Get the list of product entity ids.
	 * 
	 * @return {@link List} of product entity ids
	 */
	public List<Long> getProductEntityIds() {
		return productEntityIds;
	}

	/**
	 * Set the list of product entity ids.
	 * 
	 * @param productEntityIds {@link List} of product entity ids
	 */
	public void setProductEntityIds(List<Long> productEntityIds) {
		this.productEntityIds = productEntityIds;
	}

}
