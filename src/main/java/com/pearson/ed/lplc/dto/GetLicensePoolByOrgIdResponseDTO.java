package com.pearson.ed.lplc.dto;

import java.io.Serializable;
import java.util.Date;

public class GetLicensePoolByOrgIdResponseDTO implements Serializable{
	
	private static final long serialVersionUID = -6791950440540657878L;
	
	private String organizationId;
	private String productId;
	private String licenseId;
	private Date startDate;
	private Date endDate;
	private int quantity;
	private int used_quantity;
	private int denyNewSubscription;
	private String rootOrganizationId;
	
	/**
	 * @return the rootOrganizationId
	 */
	public String getRootOrganizationId() {
		return rootOrganizationId;
	}
	/**
	 * @param rootOrganizationId the rootOrganizationId to set
	 */
	public void setRootOrganizationId(String rootOrganizationId) {
		this.rootOrganizationId = rootOrganizationId;
	}
	/**
	 * @return the denyNewSubscription
	 */
	public int getDenyNewSubscription() {
		return denyNewSubscription;
	}
	/**
	 * @param denyNewSubscription the denyNewSubscription to set
	 */
	public void setDenyNewSubscription(int denyNewSubscription) {
		this.denyNewSubscription = denyNewSubscription;
	}
	/**
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the licenseId
	 */
	public String getLicenseId() {
		return licenseId;
	}
	/**
	 * @param licenseId the licenseId to set
	 */
	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the used_quantity
	 */
	public int getUsed_quantity() {
		return used_quantity;
	}
	/**
	 * @param used_quantity the used_quantity to set
	 */
	public void setUsed_quantity(int used_quantity) {
		this.used_quantity = used_quantity;
	}
	
	

}
