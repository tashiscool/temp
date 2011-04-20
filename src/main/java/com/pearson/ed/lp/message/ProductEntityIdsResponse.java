package com.pearson.ed.lp.message;

import java.util.Hashtable;
import java.util.Map;

/**
 * Response for the ProductLifeCycleClient api which wraps a map of Product Entity Id values to ProductData instances
 * which are simple POJOs containing all necessary product data for the GetLicensedProduct service response.
 * 
 * @author ULLOYNI
 * 
 */
public class ProductEntityIdsResponse {

	private Map<Long, ProductData> productDataByEntityIds = new Hashtable<Long, ProductData>();

	/**
	 * Get the map of Product Entity Id values to Product data collections.
	 * 
	 * @return {@link Map} of product entity ids to {@link ProductData}
	 */
	public Map<Long, ProductData> getProductDataByEntityIds() {
		return productDataByEntityIds;
	}

	/**
	 * Set the map of Product Entity Id values to Product data collections.
	 * 
	 * @param productDataByEntityIds {@link Map} of product entity ids to {@link ProductData}
	 */
	public void setProductDataByEntityIds(Map<Long, ProductData> productDataByEntityIds) {
		this.productDataByEntityIds = productDataByEntityIds;
	}

}
