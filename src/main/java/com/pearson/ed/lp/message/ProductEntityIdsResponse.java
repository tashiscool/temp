package com.pearson.ed.lp.message;

import java.util.Hashtable;
import java.util.Map;

/**
 * Response for the ProductLifeCycleClient api which wraps a map
 * of Product Entity Id values to ProductData instances which
 * are simple POJOs containing all necessary product data for the
 * GetLicensedProduct service response.
 * 
 * @author ULLOYNI
 *
 */
public class ProductEntityIdsResponse {
	
	private Map<Long,ProductData> productDataByEntityIds = new Hashtable<Long,ProductData>();

	/**
	 * Get the map of Product Entity Id values to Product data collections.
	 * @return
	 */
	public Map<Long, ProductData> getProductDataByEntityIds() {
		return productDataByEntityIds;
	}

	/**
	 * Set the map of Product Entity Id values to Product data collections.
	 * @param productDataByEntityIds
	 */
	public void setProductDataByEntityIds(Map<Long, ProductData> productDataByEntityIds) {
		this.productDataByEntityIds = productDataByEntityIds;
	}
	
	/**
	 * Simple pojo for holding product data.
	 * 
	 * @author ULLOYNI
	 *
	 */
	public class ProductData {
		
		private String displayName;
		
		private String cgAttribute;
		
		private String gradeLevel;
		
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public String getCgAttribute() {
			return cgAttribute;
		}
		public void setCgAttribute(String cgAttribute) {
			this.cgAttribute = cgAttribute;
		}
		public String getGradeLevel() {
			return gradeLevel;
		}
		public void setGradeLevel(String gradeLevel) {
			this.gradeLevel = gradeLevel;
		}
	}

}
