package com.pearson.ed.lplc.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class UpdateLicensePoolDTO {
	private Date startDate;
	private Date endDate;
	private int quantity;
	private String orderLineItem;
	private int usedLicenses;
	private String organizationId;

	/**
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId
	 *            the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the usedLicenses
	 */
	public int getUsedLicenses() {
		return usedLicenses;
	}

	/**
	 * @param usedLicenses
	 *            the usedLicenses to set
	 */
	public void setUsedLicenses(int usedLicenses) {
		this.usedLicenses = usedLicenses;
	}

	/**
	 * @return the orderLineItem
	 */
	public String getOrderLineItem() {
		return orderLineItem;
	}

	/**
	 * @param orderLineItem
	 *            the orderLineItem to set
	 */
	public void setOrderLineItem(String orderLineItem) {
		this.orderLineItem = orderLineItem;
	}

	private String licensepoolId;

	/**
	 * @return the licensepoolId
	 */
	public String getLicensepoolId() {
		return licensepoolId;
	}

	/**
	 * @param licensepoolId
	 *            the licensepoolId to set
	 */
	public void setLicensepoolId(String licensepoolId) {
		this.licensepoolId = licensepoolId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
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
	 * @param endDate
	 *            the endDate to set
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
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Compares two LicensePool objects for equality. LicensePools are equal if they're the same instance or if they
	 * have the same values for their User members which are used to generate the hash code, therefore also having
	 * matching hash codes.
	 * 
	 * @return true if the objects are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UpdateLicensePoolDTO)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UpdateLicensePoolDTO u = (UpdateLicensePoolDTO) obj;

		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(this.startDate, u.startDate);
		equalsBuilder.append(this.endDate, u.endDate);
		equalsBuilder.append(this.quantity, u.quantity);
		return equalsBuilder.isEquals();
	}

	/**
	 * Returns the hash code for a User. The hash code is generated from all persistent member variables.
	 * 
	 * @return the LicensePool's hash code.
	 */
	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(11, 37);
		hashCodeBuilder.append(startDate);
		hashCodeBuilder.append(endDate);
		hashCodeBuilder.append(quantity);
		return hashCodeBuilder.toHashCode();
	}

	/**
	 * Constructs a String that contains all of the information stored in a User object.
	 * 
	 * @return a String representation of the information store in a User.
	 */
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd KK:mm:ss.SSS aa");
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append(",");
		sb.append("StartDate:" + startDate);
		sb.append(",");
		sb.append("EndDate:" + endDate);
		sb.append(",");
		sb.append("Quantity:" + quantity);
		sb.append(",");
		return sb.toString();
	}
}
