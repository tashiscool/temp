package com.pearson.ed.lplc.model.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * LPLCBaseEntity is the base class for all model classes in the LPLC.
 * 
 * @author RUMBA
 */

@MappedSuperclass
public class LPLCBaseEntity {
	@Column(nullable = false, name = "DT_ADDED")
	protected Date createdDate;

	@Column(nullable = false, name = "ADDED_BY")
	protected String createdBy;

	@Column(nullable = false, name = "DT_MODIFIED")
	protected Date lastUpdatedDate;

	@Column(nullable = false, name = "MODIFIED_BY")
	protected String lastUpdatedBy;

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd KK:mm:ss.SSS aa");
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append(",");
		sb.append("createdDate:" + ((createdDate != null) ? format.format(createdDate) : "null"));
		sb.append(",");
		sb.append("createdBy:" + createdBy);
		sb.append(",");
		sb.append("lastUpdatedDate:" + ((lastUpdatedDate != null) ? format.format(lastUpdatedDate) : "null"));
		sb.append(",");
		sb.append("lastUpdatedBy:" + lastUpdatedBy);
		return sb.toString();
	}

	/**
	 * Compares two LPLCBaseEntity objects for equality. LPLCBaseEntity objects are equal if all of their persistent
	 * member variables are equal or if they are the same instance of a class.
	 * 
	 * @param obj
	 *            the object to compare.
	 * 
	 * @return true if the LPLCBaseEntity objects are equal.
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof LPLCBaseEntity))
			return false;
		if (this == obj)
			return true;

		LPLCBaseEntity u = (LPLCBaseEntity) obj;

		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(this.createdDate, u.createdDate);
		equalsBuilder.append(this.createdBy, u.createdBy);
		equalsBuilder.append(this.lastUpdatedDate, u.lastUpdatedDate);
		equalsBuilder.append(this.lastUpdatedBy, u.lastUpdatedBy);

		return equalsBuilder.isEquals();
	}

	/**
	 * Generates a hashCode for a LPLCBaseEntity object, based on all of the persistent member variables in order to
	 * maintain the hashCode contract that equal objects must have the same hash code.
	 * 
	 * @return the hash code for the LPLCBaseEntity object.
	 */
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(5, 199);
		hashCodeBuilder.append(createdDate);
		hashCodeBuilder.append(createdBy);
		hashCodeBuilder.append(lastUpdatedDate);
		hashCodeBuilder.append(lastUpdatedBy);
		return hashCodeBuilder.toHashCode();
	}

}
