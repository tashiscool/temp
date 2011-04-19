/**
 * 
 */
package com.pearson.ed.lp.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Simple pojo for holding product data.
 * 
 * @author ULLOYNI
 *
 */
public class ProductData {
	
	private String productId;
	
	private String displayName;
	
	private String shortDescription;
	
	private String longDescription;
	
	private String cgProgram;
	
	private List<String> gradeLevels = new ArrayList<String>();
	
	public ProductData(){}
	
	public ProductData(String productId, String displayName, 
			String shortDescription, String longDescription, 
			String cgProgram, String[] gradeLevels) {
		this.productId = productId;
		this.displayName = displayName;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.cgProgram = cgProgram;
		if(gradeLevels != null) {
			this.gradeLevels = Arrays.asList(gradeLevels);
		}
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getCgProgram() {
		return cgProgram;
	}
	
	public void setCgProgram(String cgProgram) {
		this.cgProgram = cgProgram;
	}
	
	public List<String> getGradeLevels() {
		return gradeLevels;
	}
	
	public void setGradeLevels(List<String> gradeLevels) {
		this.gradeLevels = gradeLevels;
	}
	
	public void addGradeLevel(String gradeLevel) {
		this.gradeLevels.add(gradeLevel);
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}
