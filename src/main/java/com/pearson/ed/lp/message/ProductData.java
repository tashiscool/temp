/**
 * 
 */
package com.pearson.ed.lp.message;

import java.util.ArrayList;
import java.util.List;


/**
 * Simple pojo for holding product data.
 * 
 * @author ULLOYNI
 *
 */
public class ProductData {
	
	private String displayName;
	
	private String cgAttribute;
	
	private List<String> gradeLevels = new ArrayList<String>();
	
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
	
	public List<String> getGradeLevels() {
		return gradeLevels;
	}
	
	public void setGradeLevels(List<String> gradeLevels) {
		this.gradeLevels = gradeLevels;
	}
	
	public void addGradeLevel(String gradeLevel) {
		this.gradeLevels.add(gradeLevel);
	}
}
