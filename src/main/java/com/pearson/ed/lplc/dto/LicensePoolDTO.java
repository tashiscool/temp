/**
 * 
 */
package com.pearson.ed.lplc.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The LicensePool class represents users in the LPLC.
 * 
 * 
 */
public class LicensePoolDTO implements Serializable {

	
	private String licensepoolId;
	
	private String type;
	private Date startDate;
	private Date endDate;
	private int quantity;
	private int usedLicenses;
	private int denyManualSubscription;
	private List<String> productIds;
	private String organizationId;
	private int organizationLevel;
	private boolean managedChildOrganization;
	private boolean managedParentOrganization;
	/**
	 * @return the managedChildOrganization
	 */
	public boolean isManagedChildOrganization() {
		return managedChildOrganization;
	}

	/**
	 * @param managedChildOrganization the managedChildOrganization to set
	 */
	public void setManagedChildOrganization(boolean managedChildOrganization) {
		this.managedChildOrganization = managedChildOrganization;
	}

	/**
	 * @return the managedParentOrganization
	 */
	public boolean isManagedParentOrganization() {
		return managedParentOrganization;
	}

	/**
	 * @param managedParentOrganization the managedParentOrganization to set
	 */
	public void setManagedParentOrganization(boolean managedParentOrganization) {
		this.managedParentOrganization = managedParentOrganization;
	}

	/**
	 * @return the productIds
	 */
	public List<String> getProductIds() {
		return productIds;
	}

	/**
	 * @param productIds the productIds to set
	 */
	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	/**
	 * @return the organizationLevel
	 */
	public int getOrganizationLevel() {
		return organizationLevel;
	}

	/**
	 * @param organizationLevel the organizationLevel to set
	 */
	public void setOrganizationLevel(int organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

	private Date lastUpdatedDate;
	private Date createdDate;
	private String lastUpdatedBy;
	private String createdBy;
	private String sourceSystem;
	private String mode;
	/**
	 * @return the licensepoolId
	 */
	public String getLicensepoolId() {
		return licensepoolId;
	}

	/**
	 * @param licensepoolId the licensepoolId to set
	 */
	public void setLicensepoolId(String licensepoolId) {
		this.licensepoolId = licensepoolId;
	}
	/**
	 * @return the sourceSystem
	 */
	public String getSourceSystem() {
		return sourceSystem;
	}

	/**
	 * @param sourceSystem the sourceSystem to set
	 */
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the usedLicenses
	 */
	public int getUsedLicenses() {
		return usedLicenses;
	}

	/**
	 * @param usedLicenses the usedLicenses to set
	 */
	public void setUsedLicenses(int usedLicenses) {
		this.usedLicenses = usedLicenses;
	}

	/**
	 * @return the subscriptionStatus
	 */
	public int getDenyManualSubscription() {
		return denyManualSubscription;
	}

	/**
	 * @param subscriptionStatus the subscriptionStatus to set
	 */
	public void setDenyManualSubscription(int denyManualSubscription) {
		this.denyManualSubscription = denyManualSubscription;
	}

	/**
	 * @return the productId
	 */
	public List<String> getProductId() {
		return productIds;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(List<String> productIds) {
		this.productIds = productIds;
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
	 * @return the lastUpdatedDate
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastUpdatedBy
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * @param lastUpdatedBy the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	private static final long serialVersionUID = -6791950440540657878L;
	
	
	
	
	
	/**
	 * Compares two LicensePool objects for equality. LicensePools are equal if they're the
	 * same instance or if they have the same values for their User members
	 * which are used to generate the hash code, therefore also having matching
	 * hash codes.
	 * 
	 * @return true if the objects are equal.
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof LicensePoolDTO)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		LicensePoolDTO u = (LicensePoolDTO) obj;
		
		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(this.type, u.type);
		equalsBuilder.append(this.startDate, u.startDate);
		equalsBuilder.append(this.endDate, u.endDate);
		equalsBuilder.append(this.quantity, u.quantity);
		equalsBuilder.append(this.usedLicenses, u.usedLicenses);
		
		equalsBuilder.append(this.denyManualSubscription, u.denyManualSubscription);
		equalsBuilder.append(this.productIds.size(), u.productIds.size());
		if (this.productIds.size() == u.productIds.size()){
			Iterator<String> iterator1 = productIds.iterator();
			Iterator<String> iterator2 = u.productIds.iterator();
			while(iterator1.hasNext()){
				equalsBuilder.append(iterator1.next(), iterator2.next());
			}
		}
		equalsBuilder.append(this.organizationId, u.organizationId);
		equalsBuilder.append(this.lastUpdatedDate, u.lastUpdatedDate);
		equalsBuilder.append(this.lastUpdatedBy, u.lastUpdatedBy);
		equalsBuilder.append(this.createdDate, u.createdDate);
		equalsBuilder.append(this.createdBy, u.createdBy);
		return equalsBuilder.isEquals();
	}

	/**
	 * Returns the hash code for a User. The hash code is generated from all
	 * persistent member variables.
	 * 
	 * @return the LicensePool's hash code.
	 */
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(11, 37);
		
		hashCodeBuilder.append(type);
		hashCodeBuilder.append(startDate);
		hashCodeBuilder.append(endDate);
		hashCodeBuilder.append(quantity);
		hashCodeBuilder.append(usedLicenses);
		hashCodeBuilder.append(denyManualSubscription);
		hashCodeBuilder.append(productIds);
		hashCodeBuilder.append(organizationId);
		hashCodeBuilder.append(lastUpdatedDate);
		hashCodeBuilder.append(lastUpdatedBy);
		hashCodeBuilder.append(createdDate);
		hashCodeBuilder.append(createdBy);
		return hashCodeBuilder.toHashCode();
	}

	/**
	 * Constructs a String that contains all of the information stored in a User
	 * object.
	 * 
	 * @return a String representation of the information store in a User.
	 */
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MMM-dd KK:mm:ss.SSS aa");
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append(",");
		sb.append("Type:" + type);
		sb.append(",");
		sb.append("StartDate:" + startDate);
		sb.append(",");
		sb.append("EndDate:" + endDate);
		sb.append(",");
		sb.append("Quantity:" + quantity);
		sb.append(",");
		sb.append("UsedLicenses:" + usedLicenses);
		sb.append(",");
		sb.append("SubscriptionStatus:" + denyManualSubscription);
		sb.append(",");
		Iterator<String> iterator1 = productIds.iterator();
		while (iterator1.hasNext())
			sb.append("ProductIds:" + iterator1.next() + " ,");		
		sb.append("OrganizationId:" + organizationId);
		sb.append(",");
		sb.append("LastUpdatedDate:" + ((lastUpdatedDate != null) ?
				format.format(lastUpdatedDate) : "null"));
		sb.append(",");
		sb.append("CreatedDate:" + ((createdDate != null) ?
				format.format(createdDate) : "null"));
		sb.append(",");
		sb.append("LastUpdatedBy:" + lastUpdatedBy);
		sb.append(",");
		sb.append("CreatedBy:" + createdBy);
		sb.append(",");
		return sb.toString();
	}
}
