package com.pearson.ed.lplc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "LicensePool_OrderLineItem")
public class OrderLineItemLPMapping extends LPLCBaseEntity implements
		Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 550158394974669789L;

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@Column(name = "ORDERLINEITEM_LP_ID")
	private String orderLineItemLPId;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "LICENSEPOOL_ID")
	private LicensePoolMapping licensepoolMapping;
	
	@Column(nullable = false, name = "ORDERLINEITEM_ID", length = 128)
	private String orderLineItemId;

	/**
	 * @return the orderLineItemLPId
	 */
	public String getOrderLineItemLPId() {
		return orderLineItemLPId;
	}

	/**
	 * @param orderLineItemLPId the orderLineItemLPId to set
	 */
	public void setOrderLineItemLPId(String orderLineItemLPId) {
		this.orderLineItemLPId = orderLineItemLPId;
	}

	/**
	 * @return the licensepoolMapping
	 */
	public LicensePoolMapping getLicensepoolMapping() {
		return licensepoolMapping;
	}

	/**
	 * @param licensepoolMapping the licensepoolMapping to set
	 */
	public void setLicensepoolMapping(LicensePoolMapping licensepoolMapping) {
		this.licensepoolMapping = licensepoolMapping;
	}

	/**
	 * @return the orderLineItemId
	 */
	public String getOrderLineItemId() {
		return orderLineItemId;
	}

	/**
	 * @param orderLineItemId the orderLineItemId to set
	 */
	public void setOrderLineItemId(String orderLineItemId) {
		this.orderLineItemId = orderLineItemId;
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
		hashCodeBuilder.append(orderLineItemId);
		hashCodeBuilder.append(licensepoolMapping);
		return hashCodeBuilder.toHashCode();
	}

	/**
	 * Compares two OrderLineItemLPMapping objects for equality. OrderLineItemLPMapping
	 * objects are equal if all of their persistent member variables are equal
	 * or if they are the same instance of a class.
	 * 
	 * @param obj
	 *            the object to compare.
	 * 
	 * @return true if the OrderLineItemLPMapping objects are equal.
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof OrderLineItemLPMapping))
			return false;
		if (this == obj)
			return true;

		OrderLineItemLPMapping u = (OrderLineItemLPMapping) obj;

		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(this.orderLineItemId, u.orderLineItemId);
		equalsBuilder.append(this.licensepoolMapping, u.licensepoolMapping);
		return equalsBuilder.isEquals() && super.equals(obj);
	}

}
