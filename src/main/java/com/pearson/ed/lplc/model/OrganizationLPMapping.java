package com.pearson.ed.lplc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "LicensePool_Organization")
public class OrganizationLPMapping extends LPLCBaseEntity implements
		Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5501583949734569789L;

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@Column(name = "ORGANIZATION_LP_ID")
	private String organizationLPId;

	@Column(nullable = false, name = "denynewsubscription", length = 1)
	private int denyManualSubscription;

	@Column(nullable = true, name = "used_quantity", length = 16)
	private int used_quantity;

	@Column(nullable = false, name = "organization_id", length = 32)
	private String organization_id;

	@Column(nullable = false, name = "organization_level", length = 3)
	private int organization_level;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "LICENSEPOOL_ID")
	private LicensePoolMapping licensepoolMapping;
	
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
	 * @return the organizationLPId
	 */
	public String getOrganizationLPId() {
		return organizationLPId;
	}

	/**
	 * @param organizationLPId
	 *            the organizationLPId to set
	 */
	public void setOrganizationLPId(String organizationLPId) {
		this.organizationLPId = organizationLPId;
	}

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
	 * @return the organization_id
	 */
	public String getOrganization_id() {
		return organization_id;
	}

	/**
	 * @param organization_id the organization_id to set
	 */
	public void setOrganization_id(String organization_id) {
		this.organization_id = organization_id;
	}

	/**
	 * @return the organization_level
	 */
	public int getOrganization_level() {
		return organization_level;
	}

	/**
	 * @param organization_level the organization_level to set
	 */
	public void setOrganization_level(int organization_level) {
		this.organization_level = organization_level;
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
		hashCodeBuilder.append(organizationLPId);
		hashCodeBuilder.append(used_quantity);
		hashCodeBuilder.append(organization_id);
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
		if (!(obj instanceof OrganizationLPMapping))
			return false;
		if (this == obj)
			return true;

		OrganizationLPMapping u = (OrganizationLPMapping) obj;

		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(this.organizationLPId, u.organizationLPId);
		equalsBuilder.append(this.used_quantity, u.used_quantity);
		equalsBuilder.append(this.organization_id, u.organization_id);
		return equalsBuilder.isEquals() && super.equals(obj);
	}

}
