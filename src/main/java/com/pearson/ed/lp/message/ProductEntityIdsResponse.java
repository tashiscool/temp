package com.pearson.ed.lp.message;

import java.util.Hashtable;
import java.util.Map;

/**
 * Response for the ProductLifeCycleClient api which wraps a map
 * of Product Entity Id values to Product Display Name strings.
 * 
 * @author ULLOYNI
 *
 */
public class ProductEntityIdsResponse {
	
	private Map<Long,String> productDisplayNamesByEntityIds = new Hashtable<Long,String>();

	/**
	 * Get the map of Product Entity Id values to Product Display Name strings.
	 * @return
	 */
	public Map<Long, String> getProductDisplayNamesByEntityIds() {
		return productDisplayNamesByEntityIds;
	}

	/**
	 * Set the map of Product Entity Id values to Product Display Name strings.
	 * @param productDisplayNamesByEntityIds
	 */
	public void setProductDisplayNamesByEntityIds(Map<Long, String> productDisplayNamesByEntityIds) {
		this.productDisplayNamesByEntityIds = productDisplayNamesByEntityIds;
	}

}
