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

	/**
	 * Default constructor.
	 */
	public ProductData() {
	}

	/**
	 * Parameterized constructor.
	 * 
	 * @param productId product id string
	 * @param displayName product display name
	 * @param shortDescription product short description
	 * @param longDescription product long description
	 * @param cgProgram product CG program
	 * @param gradeLevels array of product grade levels
	 */
	public ProductData(String productId, String displayName, String shortDescription, String longDescription,
			String cgProgram, String[] gradeLevels) {
		this.productId = productId;
		this.displayName = displayName;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.cgProgram = cgProgram;
		if (gradeLevels != null) {
			this.gradeLevels = Arrays.asList(gradeLevels);
		}
	}

	/**
	 * Get the display name.
	 * 
	 * @return display name string
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Set the display name.
	 * 
	 * @param displayName display name string
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Get the CG program.
	 * 
	 * @return CG program string
	 */
	public String getCgProgram() {
		return cgProgram;
	}

	/**
	 * Set the CG program.
	 * 
	 * @param cgProgram CG program string
	 */
	public void setCgProgram(String cgProgram) {
		this.cgProgram = cgProgram;
	}

	/**
	 * Get the grade levels as a {@link List}.
	 * 
	 * @return {@link List} of grade level strings
	 */
	public List<String> getGradeLevels() {
		return gradeLevels;
	}

	/**
	 * Set the grade levels {@link List}.
	 * 
	 * @param gradeLevels {@link List} of grade level strings
	 */
	public void setGradeLevels(List<String> gradeLevels) {
		this.gradeLevels = gradeLevels;
	}

	/**
	 * Convenience method to add a grade level string to the inner dataset.
	 * 
	 * @param gradeLevel grade level string to add
	 */
	public void addGradeLevel(String gradeLevel) {
		this.gradeLevels.add(gradeLevel);
	}

	/**
	 * Get the short description.
	 * 
	 * @return short description string
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * Set the short description.
	 * 
	 * @param shortDescription short description string
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * Get the long description.
	 * 
	 * @return long description string
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * Set the long description.
	 * 
	 * @param longDescription long description string
	 */
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	/**
	 * Get the product id.
	 * 
	 * @return product id string
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * Set the product id.
	 * 
	 * @param productId product id string
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
