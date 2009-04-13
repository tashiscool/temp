package com.pearson.ed.lplc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.pearson.ed.lplc.model.common.LPLCBaseEntity;

/**
 * Representing a license pool mapping in the system.
 * 
 * @author RUMBA
 */

@Entity
@org.hibernate.annotations.GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
@Table(name = "LicensePool")
public class LicensePoolMapping extends LPLCBaseEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 550158394974669789L;

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@Column(name = "LICENSEPOOL_ID")
	private String licensepoolId;

	@Column(nullable = false, name = "type", length = 128)
	private String type;

	@Column(nullable = false, name = "status", length = 1)
	private String status;

	@Column(nullable = false, name = "denynewsubscription", length = 1)
	private int denyManualSubscription;

	@Column(nullable = false, name = "start_date")
	private Date start_date;

	@Column(nullable = true, name = "end_date")
	private Date end_date;

	@Column(nullable = true, name = "quantity", length = 16)
	private int quantity;

	@Column(nullable = true, name = "used_quantity", length = 16)
	private int used_quantity;

	@Column(nullable = true, name = "organization_id", length = 128)
	private String org_id;

	@Column(nullable = true, name = "ORGANIZATION_LEVEL", length = 128)
	private int org_level;
	
	/**
	 * @return the org_level
	 */
	public int getOrg_level() {
		return org_level;
	}

	/**
	 * @param org_level the org_level to set
	 */
	public void setOrg_level(int org_level) {
		this.org_level = org_level;
	}

	@Column(nullable = true, name = "sourcesystem", length = 128)
	private String source_system;

	/**
	 * @return the denyManualSubscription
	 */
	public int getDenyManualSubscription() {
		return denyManualSubscription;
	}

	/**
	 * @param denyManualSubscription
	 *            the denyManualSubscription to set
	 */
	public void setDenyManualSubscription(int denyManualSubscription) {
		this.denyManualSubscription = denyManualSubscription;
	}

	/**
	 * @return the source_system
	 */
	public String getSource_system() {
		return source_system;
	}

	/**
	 * @param source_system
	 *            the source_system to set
	 */
	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}
 
	
	@org.hibernate.annotations.CollectionOfElements(targetElement = java.lang.String.class)
	@JoinTable(name="LicensePool_Product", joinColumns = @JoinColumn(name="licensepool_id"))
	@Column(name="product_id", nullable=false)
	private List<String> products = new ArrayList<String>();

	/**
	 * @return the licensepoolId
	 */
	public String getLicensepoolId() {
		return licensepoolId;
	}

	/**
	 * @return the products
	 */
	public List<String> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<String> products) {
		this.products = products;
	}

	/**
	 * @param licensepoolId
	 *            the licensepoolId to set
	 */
	public void setLicensepoolId(String licensepoolId) {
		this.licensepoolId = licensepoolId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the start_date
	 */
	public Date getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date
	 *            the start_date to set
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	/**
	 * @return the end_date
	 */
	public Date getEnd_date() {
		return end_date;
	}

	/**
	 * @param end_date
	 *            the end_date to set
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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
	 * @return the used_quantity
	 */
	public int getUsed_quantity() {
		return used_quantity;
	}

	/**
	 * @param used_quantity
	 *            the used_quantity to set
	 */
	public void setUsed_quantity(int used_quantity) {
		this.used_quantity = used_quantity;
	}

	/**
	 * @return the org_id
	 */
	public String getOrg_id() {
		return org_id;
	}

	/**
	 * @param org_id
	 *            the org_id to set
	 */
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	
	/**
	 * Generates a hashCode for a LicensePoolMapping object, based on all of the
	 * persistent member variables in order to maintain the hashCode contract
	 * that equal objects must have the same hash code.
	 * 
	 * @return the hash code for the LicensePoolMapping object.
	 */
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(499, 911);
		hashCodeBuilder.append(licensepoolId);
		hashCodeBuilder.append(type);
		hashCodeBuilder.append(status);
		hashCodeBuilder.append(start_date);
		hashCodeBuilder.append(end_date);
		hashCodeBuilder.append(quantity);
		hashCodeBuilder.append(used_quantity);
		hashCodeBuilder.append(org_id);
		return hashCodeBuilder.toHashCode();
	}

	/**
	 * Compares two LicensePoolMapping objects for equality. licensepoolMapping
	 * objects are equal if all of their persistent member variables are equal
	 * or if they are the same instance of a class.
	 * 
	 * @param obj
	 *            the object to compare.
	 * 
	 * @return true if the LicensePoolMapping objects are equal.
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof LicensePoolMapping))
			return false;
		if (this == obj)
			return true;

		LicensePoolMapping u = (LicensePoolMapping) obj;

		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(this.licensepoolId, u.licensepoolId);
		equalsBuilder.append(this.type, u.type);
		equalsBuilder.append(this.status, u.status);
		equalsBuilder.append(this.start_date, u.start_date);
		equalsBuilder.append(this.end_date, u.end_date);
		equalsBuilder.append(this.quantity, u.quantity);
		equalsBuilder.append(this.used_quantity, u.used_quantity);
		equalsBuilder.append(this.org_id, u.org_id);
		return equalsBuilder.isEquals() && super.equals(obj);
	}

}
